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
import java.util.List;
import java.util.Optional;
@Service
public class ImageService implements IBaseService<ImageDTO, Long>, IModelMapper<ImageDTO, Image> {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ImageDTO> findAll() {
        return createFromEntities(imageRepository.findAll());
    }

    public List<ImageDTO> findAll(Long productId) {
        return createFromEntities(imageRepository.findAllByProduct_ProductId(productId));
    }

    @Override
    public ImageDTO findById(Long imageId) {
        Optional <Image> image = imageRepository.findById(imageId);
        image.orElseThrow(() -> new NotFoundException(ImageDTO.class, imageId));
        return createFromE(image.get());
    }

    @Override
    public ImageDTO update(Long imageId, ImageDTO imageDTO) {
        Optional <Image> image = imageRepository.findById(imageId);
        image.orElseThrow(() -> new NotFoundException(ImageDTO.class, imageId));
        imageRepository.save(updateEntity(image.get(), imageDTO));
        return createFromE(image.get());
    }

    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        imageRepository.save(createFromD(imageDTO));
        return imageDTO;
    }

    @Override
    public ImageDTO delete(Long imageId) {
        Optional <Image> image = imageRepository.findById(imageId);
        image.orElseThrow(() -> new NotFoundException(ImageDTO.class, imageId));
        ImageDTO imageDTO = createFromE(image.get());
        try {
            imageRepository.delete(image.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, imageId);
        }
        return imageDTO;
    }

    @Override
    public Image createFromD(ImageDTO imageDTO) {
        Image image = modelMapper.map(imageDTO, Image.class);
        image.setProduct(productRepository.findById(imageDTO.getImageId()).get());
        return image;
    }

    @Override
    public ImageDTO createFromE(Image image) {
        ImageDTO imageDTO = modelMapper.map(image, ImageDTO.class);
        imageDTO.setProductId(image.getProduct().getProductId());
        return imageDTO;
    }

    @Override
    public Image updateEntity(Image image, ImageDTO imageDTO) {
        if (image != null && imageDTO != null){
            image.setUrl(imageDTO.getUrl());
            Optional<Product> product = productRepository.findById(image.getImageId());
            product.orElseThrow(() -> new NotFoundException(ProductDTO.class, imageDTO.getImageId()));
            image.setProduct(product.get());
        }
        return image;
    }
}
