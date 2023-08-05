package com.n19dccn112.service;

import com.n19dccn112.model.dto.ImageDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Image;
import com.n19dccn112.model.entity.Product;
import com.n19dccn112.repository.ImageRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService implements IBaseService<ProductDTO, Long>, IModelMapper<ProductDTO, Product> {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productsRepository, ImageRepository imageRepository, ImageService imageService, ModelMapper modelMapper) {
        this.productRepository = productsRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return createFromEntities(products);
    }

    @Override
    public ProductDTO findById(Long productId) {
        Optional <Product> products = productRepository.findById(productId);
        products.orElseThrow(() -> new NotFoundException(ProductDTO.class, productId));
        return createFromE(products.get());
    }

    @Override
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        Optional <Product> product = productRepository.findById(productId);
        product.orElseThrow(() -> new NotFoundException(ProductDTO.class, productId));
        productRepository.save(updateEntity(product.get(), productDTO));

        for (Image image: imageRepository.findAllByProduct_ProductId(productId)){
            imageRepository.delete(image);
        }
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < productDTO.getImageUrl().size(); i++){
            Image image = new Image();
            image.setUrl(productDTO.getImageUrl().get(i));
            image.setProduct(product.get());
            imageRepository.save(image);
            images.add(image);
        }
        product.get().setImages(images);
        productRepository.save(product.get());
        return createFromE(product.get());
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        productRepository.save(createFromD(productDTO));
        Optional<Product> product = productRepository.findProductByName(productDTO.getName(), productDTO.getDescription());
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < productDTO.getImageUrl().size(); i++){
            Image image = new Image();
            image.setUrl(productDTO.getImageUrl().get(i));
            image.setProduct(product.get());
            imageRepository.save(image);
            images.add(image);
        }
        product.get().setImages(images);
        productRepository.save(product.get());
        return createFromE(product.get());
    }

    @Override
    public ProductDTO delete(Long productId) {
        Optional <Product> product = productRepository.findById(productId);
        ProductDTO productDTO = createFromE(product.get());
        product.orElseThrow(() -> new NotFoundException(ProductDTO.class, productId));
        try {
            productRepository.delete(product.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, productId);
        }
        return productDTO;
    }

    @Override
    public Product createFromD(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        return product;
    }

    @Override
    public ProductDTO createFromE(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        if (product.getImages() != null) {
            List<String> urlImage = new ArrayList<>();
            for (Image image : product.getImages()) {
                urlImage.add(image.getUrl());
            }
            productDTO.setImageUrl(urlImage);
        }
        return productDTO;
    }

    @Override
    public Product updateEntity(Product product, ProductDTO productDTO) {
        if (product != null && productDTO != null){
            product.setDescription(productDTO.getDescription());
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setRemain(productDTO.getRemain());
            product.setUpdateDate(new Date());
            product.setExpirationDate(productDTO.getExpirationDate());
        }
        return product;
    }
}
