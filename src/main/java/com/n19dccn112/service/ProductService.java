package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.model.entity.*;
import com.n19dccn112.model.key.FeatureDetailId;
import com.n19dccn112.repository.*;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class ProductService implements IBaseService<ProductDTO, Long>, IModelMapper<ProductDTO, Product> {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final FeatureRepository featureRepository;
    private final FeatureDetailRepository featureDetailRepository;
    private final FeatureDetailService featureDetailService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productsRepository, ImageRepository imageRepository, CategoryRepository categoryRepository, FeatureRepository featureRepository, FeatureDetailRepository featureDetailRepository, FeatureDetailService featureDetailService, ImageService imageService, ModelMapper modelMapper) {
        this.productRepository = productsRepository;
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.featureRepository = featureRepository;
        this.featureDetailRepository = featureDetailRepository;
        this.featureDetailService = featureDetailService;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return createFromEntities(products);
    }

    public List<ProductDTO> findAll(Long categoryId, List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByFilter(featureIds, categoryId));
    }

    public List<ProductDTO> findAll(Long categoryId) {
        return createFromEntities(productRepository.findAllByCategory_CategoryId(categoryId));

    }

    public List<ProductDTO> findAll(List<Long> featureIds) {
        return createFromEntities(productRepository.findAllByFeaturesID(featureIds));
    }

    public List<ProductDTO> findAllByListProducts(List<Long> productIds) {
        return createFromEntities(productRepository.findAllByProductID(productIds));
    }

    @Override
    public ProductDTO findById(Long productId) {
        Optional <Product> products = productRepository.findById(productId);
        products.orElseThrow(() -> new NotFoundException(ProductDTO.class, productId));
        return createFromE(products.get());
    }
    @Transactional
    @Override
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        Optional <Product> product = productRepository.findById(productId);
        product.orElseThrow(() -> new NotFoundException(ProductDTO.class, productId));
        productRepository.save(updateEntity(product.get(), productDTO));

        List<Image> images = imageRepository.findAllByProduct_ProductId(productId);
        for (int i=0; i<productDTO.getImageUrl().size(); i++){
            if (i<images.size()) {
                images.get(i).setUrl(productDTO.getImageUrl().get(i));
                imageRepository.save(images.get(i));
            }else{
                Image image = new Image();
                image.setUrl(productDTO.getImageUrl().get(i));
                image.setProduct(product.get());
                imageRepository.save(image);
            }
        }
        List<FeatureDetail> featureDetails = featureDetailRepository.findAllByFeatureDetailsByProductId(productId);
        for (int i=0; i<productDTO.getFeatureIds().size(); i++){
            if (i<featureDetails.size()) {
                FeatureDetailId featureDetailId = featureDetails.get(i).getFeatureDetailsId();
                featureDetailId.setProduct(productRepository.findProductByName(productDTO.getName()).get());
                featureDetails.get(i).setFeatureDetailsId(featureDetailId);
                featureDetailRepository.save(featureDetails.get(i));
            }else {
                FeatureDetailDTO featureDetailDTO = new FeatureDetailDTO();
                featureDetailDTO.setFeatureId(productDTO.getFeatureIds().get(i));
                featureDetailDTO.setProductId(productId);
                featureDetailService.save(featureDetailDTO);
            }
        }

        return createFromE(product.get());
    }
    @Transactional
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product2 = createFromD(productDTO);
        product2.setCreateDate(new Date());
        product2.setCategory(categoryRepository.findById(productDTO.getCategoryId()).get());
        productRepository.save(product2);
        Optional<Product> product = productRepository.findProductByName(productDTO.getName());
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

        for (Long featureId: productDTO.getFeatureIds()) {
            FeatureDetailDTO featureDetailDTO = new FeatureDetailDTO();
            featureDetailDTO.setFeatureId(featureId);
            featureDetailDTO.setProductId(product.get().getProductId());
            featureDetailService.save(featureDetailDTO);
        }
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
        List <Image> images = imageRepository.findAllByProduct_ProductId(productDTO.getProductId());
        product.setImages(images);
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
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        productDTO.setCategoryName(product.getCategory().getCategoryName());
        List<Long> featureIds = new ArrayList<>();
        for (Feature feature: featureRepository.findAllByProductId(product.getProductId())){
            featureIds.add(feature.getFeatureId());
        }
        productDTO.setFeatureIds(featureIds);
        return productDTO;
    }

    @Transactional
    @Override
    public Product updateEntity(Product product, ProductDTO productDTO) {
        if (product != null && productDTO != null){
            product.setDescription(productDTO.getDescription());
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setRemain(productDTO.getRemain());
            product.setUpdateDate(new Date());
            product.setExpirationDate(productDTO.getExpirationDate());
            product.setCategory(categoryRepository.getById(productDTO.getCategoryId()));
        }
        return product;
    }
}
