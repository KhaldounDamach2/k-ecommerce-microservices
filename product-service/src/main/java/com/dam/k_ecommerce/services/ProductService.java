package com.dam.k_ecommerce.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dam.k_ecommerce.dto.ClientDto;
import com.dam.k_ecommerce.dto.ProductAttributeDto;
import com.dam.k_ecommerce.dto.ProductDto;
import com.dam.k_ecommerce.dto.ProductVariantAttributeDto;
import com.dam.k_ecommerce.dto.ProductVariantDto;
import com.dam.k_ecommerce.dto.ProductVariantPhotoDto;
import com.dam.k_ecommerce.model.Category;
import com.dam.k_ecommerce.model.Client;
import com.dam.k_ecommerce.model.Product;
import com.dam.k_ecommerce.model.ProductAttribute;
import com.dam.k_ecommerce.model.ProductVariant;
import com.dam.k_ecommerce.model.ProductVariantAttribute;
import com.dam.k_ecommerce.model.ProductVariantPhoto;
import com.dam.k_ecommerce.repository.CategoryRepository;
import com.dam.k_ecommerce.repository.ClientRepository;
import com.dam.k_ecommerce.repository.ProductAttributeRepository;
import com.dam.k_ecommerce.repository.ProductRepository;
import com.dam.k_ecommerce.repository.ProductVariantAttributeRepository;
import com.dam.k_ecommerce.repository.ProductVariantPhotoRepository;
import com.dam.k_ecommerce.repository.ProductVariantRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final ProductAttributeRepository productAttributeRepository;
	private final ProductVariantAttributeRepository productVariantAttributeRepository;
	private final ProductVariantRepository productVariantRepository;
	private final ProductVariantPhotoRepository productVariantPhotoRepository;
	private final ClientRepository clientRepository;
	
	
	@Transactional
	public Product createProduct(ProductDto productDto, ProductVariantDto productVariantDto,
	        ProductVariantAttributeDto productVariantAttributeDto, ProductVariantPhotoDto productVariantPhotoDto,
	        ProductAttributeDto productAttributeDto, ClientDto clientDto) {

// erstellen Category
		Category category = categoryRepository
			    .findByCatName(productDto.getCategoryDto().getCatName())
			    .map(existing -> {
			        existing.setCatDescrip(productDto.getCategoryDto().getCatDescrip());
			        return existing;
			    })
			    .orElseGet(() -> {
			        Category newCategory = new Category();
			        newCategory.setCatName(productDto.getCategoryDto().getCatName());
			        newCategory.setCatDescrip(productDto.getCategoryDto().getCatDescrip());
			        return newCategory;
			    });
		Category savedCategory = categoryRepository.save(category);

		
		
// Creating Client			
			Client client = clientRepository.findByNameAndEmail(clientDto.getClientName(), clientDto.getEmail())
					.orElseGet(() -> {
						Client newClient = new Client();
						newClient.setClientName(clientDto.getClientName());
						newClient.setEmail(clientDto.getEmail());
						return newClient;
					});
			Client savedClient = clientRepository.save(client);
	
			
			
// Creating Product			
			Product product = productRepository.findByProdNameAndClientId(productDto.getProdName(), client.getId())
					.map(existing -> {
				existing.setProdDescrip(productDto.getProdDescrip());
				existing.setCategory(savedCategory);
				existing.setClient(savedClient);
				existing.setUpdatedAt(LocalDateTime.now());
				return existing;
			}).orElseGet(() -> {
				Product newProduct = new Product();
				newProduct.setProdName(productDto.getProdName());
				newProduct.setProdDescrip(productDto.getProdDescrip());
				newProduct.setCategory(savedCategory);
				newProduct.setClient(savedClient);
				newProduct.setCreatedAt(LocalDateTime.now());
				return newProduct;
			});
			Product savedProduct = productRepository.save(product);
			
			

// Creating Product Attribute
			ProductAttribute productAttribute = productAttributeRepository.findByAttrName(productAttributeDto.getAttrName())											
					.orElseGet(() -> {
						ProductAttribute newProductAttribute = new ProductAttribute();
						newProductAttribute.setAttrName(productAttributeDto.getAttrName());						
						return newProductAttribute;
					});
			ProductAttribute savedProductAttribute = productAttributeRepository.save(productAttribute);
			
			
			
// Creating Product Variant
			ProductVariant productVariant = productVariantRepository
					.findBySkuAndProductId(productVariantDto.getSku(), savedProduct.getId()).map(existing -> {
						existing.setPrice(productVariantDto.getPrice());
						existing.setStock(productVariantDto.getStock() + existing.getStock());
						existing .setManufDate(productVariantDto.getManufDate());
						existing .setModel(productVariantDto.getModel());
						existing.setProduct(savedProduct);
						
						return existing;
					}).orElseGet(() -> {
						ProductVariant newProductVariant = new ProductVariant();
						newProductVariant.setSku(productVariantDto.getSku());
						newProductVariant.setPrice(productVariantDto.getPrice());
						newProductVariant.setStock(productVariantDto.getStock());
						newProductVariant.setProduct(savedProduct);
						return newProductVariant;
					});
			ProductVariant savedProductVariant = productVariantRepository.save(productVariant);
			
			
			
// Creating Product Variant Attribute
			ProductVariantAttribute productVariantAttribute = productVariantAttributeRepository
					.findByVariantIdAndAttributeId(productVariant.getId(), productAttribute.getId())
					.map(existing -> {
						existing.setValue(productVariantAttributeDto.getValue());						
						return existing;
					}).orElseGet(() -> {
						ProductVariantAttribute newProductVariantAttribute = new ProductVariantAttribute();					
						newProductVariantAttribute.setValue(productVariantAttributeDto.getValue());
						newProductVariantAttribute.setAttribute(savedProductAttribute);
                        newProductVariantAttribute.setProdVariant(savedProductVariant);
						return newProductVariantAttribute;
					});
			ProductVariantAttribute savedProductVariantAttribute = productVariantAttributeRepository.save(productVariantAttribute);
			
			
			
			// Creating Product Variant Photo
			ProductVariantPhoto productVariantPhoto = productVariantPhotoRepository
					.findByVariantIdAndUrl(productVariant.getId(), productVariantPhotoDto.getUrl())
					.orElseGet(() -> {
						ProductVariantPhoto newProductVariantPhoto = new ProductVariantPhoto();
						newProductVariantPhoto.setUrl(productVariantPhotoDto.getUrl());
						newProductVariantPhoto.setAltText(productVariantPhotoDto.getAltText() != null ? productVariantPhotoDto.getAltText() : "");
						newProductVariantPhoto.setPosition(productVariantPhotoDto.getPosition() != null ? productVariantPhotoDto.getPosition() : 0);
						newProductVariantPhoto.setProdVariant(savedProductVariant);
						return newProductVariantPhoto;
					});
			ProductVariantPhoto savedProductVariantPhoto = productVariantPhotoRepository.save(productVariantPhoto);
					
		
	return savedProduct;
    }


	public int getVariantQuantity(Long variantId) {
		if (variantId == null || variantId <= 0) {
			throw new IllegalArgumentException("variantId must be a positive number");
		}
		ProductVariant variant = productVariantRepository.findById(variantId)
				.orElseThrow(() -> new IllegalArgumentException("Product variant not found with id: " + variantId));
		return variant.getStock();
	}
}

