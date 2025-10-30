package com.dam.k_ecommerce.services;

import com.dam.k_ecommerce.model.*;
import com.dam.k_ecommerce.repository.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImportService {

	private final CategoryRepository categoryRepository;
	private final ClientRepository clientRepository;
	private final ProductRepository productRepository;
	private final ProductAttributeRepository attributeRepository;
	private final ProductVariantRepository productVariantRepository;
	private final ProductVariantAttributeRepository productVariantAttributeRepository;
	private final ProductVariantPhotoRepository productVariantPhotoRepository;

	@Transactional
	public void importCsv(Path csvPath) throws IOException {

		List<Integer> skippedLines = new ArrayList<>(); // Liste für übersprungene Zeilennummern
		
		log.info("Starting CSV import from {}", csvPath.toAbsolutePath());

		try (BufferedReader reader = Files.newBufferedReader(csvPath); CSVReader csvReader = new CSVReader(reader)) {

			String[] row;
			int line = 0;
			while ((row = csvReader.readNext()) != null) {
				line++;
				if (line == 1)
					continue; // skip header
//CLIENT
				try {
					String clientName = row[0].trim();
					if (clientName.isEmpty()) {
						log.warn("Skipping line {} due to empty client name", line);
						skippedLines.add(line);
						continue;
					}

					String email = row[1].trim();
					if (email.isEmpty() || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
						log.warn("Skipping line {} due to invalid email format", line);
						skippedLines.add(line);
						continue;
					}

					String phone = row[2].trim();
					if (phone.isEmpty() || !phone.matches("^\\+?[0-9]{10,15}$")) {
						log.warn("Skipping line {} due to invalid phone format", line);
						skippedLines.add(line);
						continue;
					}

					// Überprüfen, ob der Client existiert
					Client client = clientRepository.findByNameAndEmail(clientName, email).orElseGet(() -> {
						// Neuer Client, wenn nicht vorhanden
						Client newClient = new Client();
						newClient.setClientName(clientName);
						newClient.setEmail(email);
						newClient.setPhone(phone);
						return newClient;
					});

					// Speichern des Clients
					Client savedClient = clientRepository.save(client);

					
//CATEGORY			
					
					String categoryName = row[3].trim();
					if (categoryName.isEmpty()) {
						log.warn("Skipping line {} due to empty category name", line);
						skippedLines.add(line);
						continue;
					}

					String categoryDesc = row[4].trim();

					Category category = categoryRepository.findByCatName(categoryName).map(existing -> {
						existing.setCatDescrip(categoryDesc);
						return existing;
					}).orElseGet(() -> {
						Category newCategory = new Category();
						newCategory.setCatName(categoryName);
						newCategory.setCatDescrip(categoryDesc);
						return newCategory;
					});
					Category savedCategory = categoryRepository.save(category);

					
// PRODUCT			
					
					String prodName = row[5].trim();
					if (prodName.isEmpty()) {
						log.warn("Skipping line {} due to empty product name", line);
						skippedLines.add(line);
						continue;
					}

					String prodDesc = row[6].trim();

					Product product = productRepository.findByProdNameAndClientId(prodName, savedClient.getId())
							.map(existing -> {
								existing.setProdDescrip(prodDesc);
								existing.setCategory(savedCategory);
								existing.setClient(savedClient);
								existing.setUpdatedAt(LocalDateTime.now());
								return existing;
							}).orElseGet(() -> {
								Product newProduct = new Product();
								newProduct.setProdName(prodName);
								newProduct.setProdDescrip(prodDesc);
								newProduct.setCategory(savedCategory);
								newProduct.setClient(savedClient);
								newProduct.setCreatedAt(LocalDateTime.now());
								return newProduct;
							});
					Product savedProduct = productRepository.save(product);

					
// PRODUCT VARIANT

					// sku
					String sku = row[7].trim();
					if (sku.isEmpty()) {
						log.warn("Skipping line {} due to empty SKU", line);
						skippedLines.add(line);
						continue;
					}
					// price
					String priceStr = row[8].trim();
					BigDecimal price = null;

					try {
						if (priceStr.isEmpty()) {
							log.warn("Skipping line {} due to empty price", line);
							skippedLines.add(line);
							continue;
						}

						// Ersetzen von Komma durch Punkt
						priceStr = priceStr.replace(",", ".");

						price = new BigDecimal(priceStr);

						// Zusätzliche Validierung: Preis muss positiv sein
						if (price.compareTo(BigDecimal.ZERO) <= 0) {
							log.warn("Skipping line {} due to non-positive price: {}", line, price);
							skippedLines.add(line);
							continue;
						}
					} catch (NumberFormatException e) {
						log.warn("Skipping line {} due to invalid price format: {}", line, priceStr);
						skippedLines.add(line);
						continue;
					}
					BigDecimal parsedPrice = price;

					// stock
					String stockStr = row[9].trim();
					int stock = 0;
					try {
						if (stockStr.isEmpty()) {
							log.warn("Skipping line {} due to empty stock", line);
							skippedLines.add(line);
							continue;
						}
						stock = Integer.parseInt(stockStr);
						if (stock <= 0) {
							log.warn("Skipping line {} due to negative stock: {}", line, stock);
							skippedLines.add(line);
							continue;
						}
					} catch (NumberFormatException e) {
						log.warn("Skipping line {} due to invalid stock format: {}", line, stockStr);
						skippedLines.add(line);
						continue;
					}
					int parsedStock = stock;

					// manufDate
					String manufDateStr = row[10].trim(); // Beispiel: Spalte 10
					LocalDate manufDate = null;

					try {
						if (manufDateStr.isEmpty()) {
							log.warn("Skipping line {} due to empty manufacture date", line);
							skippedLines.add(line);
							continue;
						}

						// Liste der unterstützten Formate
						List<String> dateFormats = List.of("yyyy.MM.dd", "yyyy_MM_dd", "yyyy/MM/dd", "yyyy,MM,dd",
								"yyyy:MM:dd", "yyyy-MM-dd" );

						boolean parsed = false;
						for (String format : dateFormats) {
							try {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
								manufDate = LocalDate.parse(manufDateStr, formatter);
								parsed = true;
								break;
							} catch (DateTimeParseException ignored) {
								// Versuche das nächste Format
							}
						}

						if (!parsed) {
							log.warn("Skipping line {} due to invalid manufacture date format: {}", line, manufDateStr);
							skippedLines.add(line);
							continue;
						}

						// Zusätzliche Validierung: Datum darf nicht in der Zukunft liegen
						if (manufDate.isAfter(LocalDate.now())) {
							log.warn("Skipping line {} due to future manufacture date: {}", line, manufDate);
							skippedLines.add(line);
							continue;
						}
					} catch (Exception e) {
						log.warn("Skipping line {} due to unexpected error: {}", line, e.getMessage());
						skippedLines.add(line);
						continue;
					}
					LocalDate parsedManufDate = manufDate;

					String model = row[11].trim();

					ProductVariant productVariant = productVariantRepository
							.findBySkuAndProductId(sku, savedProduct.getId()).map(existing -> {
								existing.setPrice(parsedPrice);
								existing.setStock(parsedStock + existing.getStock());
								existing.setManufDate(parsedManufDate);
								existing.setModel(model);
								existing.setProduct(savedProduct);
								return existing;
							}).orElseGet(() -> {
								ProductVariant newProductVariant = new ProductVariant();
								newProductVariant.setSku(sku);
								newProductVariant.setPrice(parsedPrice);
								newProductVariant.setStock(parsedStock);
								newProductVariant.setManufDate(parsedManufDate);
								newProductVariant.setProduct(savedProduct);
								return newProductVariant;
							});
					ProductVariant savedProductVariant = productVariantRepository.save(productVariant);

					
// PRODUCT ATTRIBUTE AND PRODUCT VARIANT ATTRIBUTE	
					

					// Parse Attribute Names and Values
					String attrNames = row[12].trim();
					String values = row[13].trim();


					// Remove curly braces and trim
					attrNames = attrNames.replaceAll("^\\{|\\}$", "").trim();
					values = values.replaceAll("^\\{|\\}$", "").trim();

					// Splitte die Werte
					String[] attrNameArr = attrNames.split(",");
					String[] attrValueArr = values.split(",");

					// Validierung der Zuordnung
					if (attrNameArr.length != attrValueArr.length) {
					    log.warn("Mismatch between attribute names and values at line {}, skipping", line);
					    skippedLines.add(line);
					    return;
					}

					// Speichere Attribute und Werte
					for (int i = 0; i < attrNameArr.length; i++) {
					    String attrName = attrNameArr[i].trim();
					    String attrValue = attrValueArr[i].trim();

					    if (attrName.isEmpty()) {
					        log.warn("Skipping empty attribute name at line {}, position {}", line, i + 1);
					        continue;
					    }

					    ProductAttribute productAttribute = attributeRepository.findByAttrName(attrName)
					            .orElseGet(() -> {
					                ProductAttribute newProductAttribute = new ProductAttribute();
					                newProductAttribute.setAttrName(attrName);
					                return attributeRepository.save(newProductAttribute);
					            });
					    ProductAttribute savedProductAttribute = attributeRepository.save(productAttribute);

					    ProductVariantAttribute productVariantAttribute = productVariantAttributeRepository
					            .findByVariantIdAndAttributeId(savedProductVariant.getId(), productAttribute.getId())
					            .map(existing -> {
					                existing.setValue(attrValue);
					                return existing;
					            }).orElseGet(() -> {
					                ProductVariantAttribute newProductVariantAttribute = new ProductVariantAttribute();
					                newProductVariantAttribute.setValue(attrValue);
					                newProductVariantAttribute.setAttribute(savedProductAttribute);
					                newProductVariantAttribute.setProdVariant(savedProductVariant);
					                return newProductVariantAttribute;
					            });

					    productVariantAttributeRepository.save(productVariantAttribute);
					}


					
// PRODUCT VARIANT PHOTO	
					
					String urls = row[14].trim();
					String alt = row[15] != null ? row[15].trim() : null;

					if (urls.startsWith("{") && urls.endsWith("}")) {
					    urls = urls.substring(1, urls.length() - 1); // Entferne {}
					}
					if (alt != null && alt.startsWith("{") && alt.endsWith("}")) {
					    alt = alt.substring(1, alt.length() - 1); // Entferne {}
					}

					String[] urlArr = urls.split(",");
					String[] altArr = alt != null ? alt.split(",") : new String[urlArr.length];

					for (int i = 0; i < urlArr.length; i++) {
					    String url = urlArr[i].trim();
					    String altText = (i < altArr.length) ? altArr[i].trim() : null;

					    if (url.isEmpty()) {
					        log.warn("Skipping empty URL at line {}, position {}", line, i + 1);
					        continue;
					    }

					    final int position = i + 1;
						ProductVariantPhoto productVariantPhoto = productVariantPhotoRepository
								.findByVariantIdAndUrl(savedProductVariant.getId(), url).
								orElseGet(() -> {
									ProductVariantPhoto newProductVariantPhoto = new ProductVariantPhoto();
								    newProductVariantPhoto.setUrl(url);
								    newProductVariantPhoto.setAltText(altText != null ? altText : "");
								    newProductVariantPhoto.setPosition(position); // Position basierend auf der Reihenfolge
									return newProductVariantPhoto;
									
								});
						// Position basierend auf der Reihenfolge

						// Speichern des Bildes
						productVariantPhoto.setProdVariant(savedProductVariant);
						productVariantPhotoRepository.save(productVariantPhoto);
					}

				} catch (Exception e) {
					log.warn("Skipping line {} due to error: {}", line, e.getMessage());
					skippedLines.add(line);
					continue;
				}

			}

		} catch (CsvValidationException e) {
			throw new IOException("Invalid CSV structure", e);
		}
		// Protokollieren der Seriennummern der übersprungenen Zeilen
		log.info("Serial numbers of skipped lines: {}", skippedLines);

	}
}
