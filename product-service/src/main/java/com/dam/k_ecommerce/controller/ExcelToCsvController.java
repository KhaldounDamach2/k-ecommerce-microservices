package com.dam.k_ecommerce.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dam.k_ecommerce.repository.CategoryRepository;
import com.dam.k_ecommerce.repository.ClientRepository;
import com.dam.k_ecommerce.repository.ProductAttributeRepository;
import com.dam.k_ecommerce.repository.ProductRepository;
import com.dam.k_ecommerce.repository.ProductVariantAttributeRepository;
import com.dam.k_ecommerce.repository.ProductVariantPhotoRepository;
import com.dam.k_ecommerce.repository.ProductVariantRepository;
import com.dam.k_ecommerce.services.ProductImportService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ExcelToCsvController {
	
	private final ProductImportService productImportService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.csv.dir}")
    private String csvDir;

    private static final Pattern EXCEL_EXTENSION = Pattern.compile("\\.(xlsx)$", Pattern.CASE_INSENSITIVE);
    private static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @PostMapping("/import-products")
    public ResponseEntity<String> handleExcelUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Initial valida
            if (file.isEmpty()) {
                log.warn("Empty file received");
                return ResponseEntity.badRequest().body("File is empty");
            }

            // File extension check
            if (!EXCEL_EXTENSION.matcher(file.getOriginalFilename()).find()) {
                log.warn("Invalid file extension: {}", file.getOriginalFilename());
                return ResponseEntity.badRequest().body("Only .xlsx files are supported");
            }

            // Content type validation
            if (!EXCEL_CONTENT_TYPE.equalsIgnoreCase(file.getContentType())) {
                log.warn("Invalid content type: {}", file.getContentType());
                return ResponseEntity.badRequest().body("Invalid file content type");
            }

            // File content validation
            try (InputStream is = file.getInputStream()) {
                if (!isValidExcelFile(is)) {
                    log.warn("Invalid Excel file content");
                    return ResponseEntity.badRequest().body("The file is not a valid Excel document");
                }
            }

            // Ensure directories exist
            createDirectoriesIfNotExist();
            
            // Save uploaded file
            Path savedFilePath = saveUploadedFile(file);
            
            // Convert to CSV
            Path csvFilePath = convertToCsv(savedFilePath);
            
            productImportService.importCsv(csvFilePath);
            
            return ResponseEntity.ok("CSV file saved to: " + csvFilePath.toAbsolutePath());
            
        } catch (NotOfficeXmlFileException e) {
            log.error("Invalid Excel format", e);
            return ResponseEntity.badRequest().body("Please save your file as Excel 2007+ (.xlsx) format");
        } catch (ZipException e) {
            log.error("Corrupted Excel file", e);
            return ResponseEntity.badRequest().body("The Excel file appears to be corrupted");
        } catch (IOException e) {
            log.error("File processing error", e);
            return ResponseEntity.internalServerError().body("File processing error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error", e);
            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());
        }
    }

    private boolean isValidExcelFile(InputStream inputStream) throws IOException {
        // Check Excel file magic number (PK header)
        byte[] header = new byte[4];
        if (inputStream.read(header) != 4) {
            return false;
        }
        return header[0] == 0x50 && header[1] == 0x4B && 
               header[2] == 0x03 && header[3] == 0x04;
    }

    private void createDirectoriesIfNotExist() throws IOException {
        Files.createDirectories(Path.of(uploadDir));
        Files.createDirectories(Path.of(csvDir));
    }

    private Path saveUploadedFile(MultipartFile file) throws IOException {
        Path destination = Path.of(uploadDir, sanitizeFilename(file.getOriginalFilename()));
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        return destination;
    }

    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }


    private Path convertToCsv(Path excelPath) throws IOException {
        String csvFilename = excelPath.getFileName().toString()
            .replaceFirst("(?i)\\.xlsx$", ".csv");
        Path csvPath = Path.of(csvDir, csvFilename);

        try (OPCPackage pkg = OPCPackage.open(excelPath.toFile());
             Workbook workbook = new XSSFWorkbook(pkg);
             BufferedWriter writer = Files.newBufferedWriter(csvPath)) {

            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> rowValues = new ArrayList<>();
                for (Cell cell : row) {
                    String cellValue = formatter.formatCellValue(cell).trim();

                    // Proper CSV escaping
                    if (cellValue.contains("\"")) {
                        cellValue = cellValue.replace("\"", "\"\"");
                    }
                    if (cellValue.contains(",") || cellValue.contains("\"") || cellValue.contains("\n")) {
                        cellValue = "\"" + cellValue + "\"";
                    }

                    rowValues.add(cellValue);
                }
                writer.write(String.join(",", rowValues));
                writer.newLine();
            }
        } catch (InvalidFormatException e) {
            throw new IOException("Invalid Excel file format", e);
        }

        return csvPath;
    }


}