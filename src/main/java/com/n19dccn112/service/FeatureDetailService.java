package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.entity.FeatureDetail;
import com.n19dccn112.model.key.FeatureDetailId;
import com.n19dccn112.repository.FeatureDetailRepository;
import com.n19dccn112.repository.FeatureRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
@Service
public class FeatureDetailService implements IModelMapper<FeatureDetailDTO, FeatureDetail> {
    private final FeatureDetailRepository featureDetailRepository;
    private final FeatureRepository featureRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public FeatureDetailService(FeatureDetailRepository featureDetailRepository, FeatureRepository featureRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.featureDetailRepository = featureDetailRepository;
        this.featureRepository = featureRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<FeatureDetailDTO> findAll() {
        return createFromEntities(featureDetailRepository.findAll());
    }
    public FeatureDetailId featureDetailId (Long feature_id, Long product_id){
        FeatureDetailId featureDetailId = new FeatureDetailId();
        featureDetailId.setFeature(featureRepository.findById(feature_id).get());
        featureDetailId.setProduct(productRepository.findById(product_id).get());
        return featureDetailId;
    }

    public FeatureDetailDTO findById (Long feature_id,  Long product_id) {
        FeatureDetailId featureDetailId = featureDetailId(feature_id, product_id);
        Optional <FeatureDetail> featureDetails = featureDetailRepository.findById(featureDetailId);
        featureDetails.orElseThrow(() -> new NotFoundException(featureDetailId));
        return createFromE(featureDetails.get());
    }

    public FeatureDetailDTO update(Long feature_id, Long product_id, FeatureDetailDTO featureDetailDTO) {
        FeatureDetailId featureDetailId = featureDetailId(feature_id, product_id);
        Optional <FeatureDetail> featureDetail = featureDetailRepository.findById(featureDetailId);
        featureDetail.orElseThrow(() -> new NotFoundException(featureDetailId));
        featureDetailRepository.save(updateEntity(featureDetail.get(), featureDetailDTO));
        return createFromE(featureDetail.get());
    }

    public FeatureDetailDTO save(FeatureDetailDTO featureDetailDTO) {
        FeatureDetail featureDetail = createFromD(featureDetailDTO);
        featureDetailRepository.save(featureDetail);
        return createFromE(featureDetail);
    }

    public FeatureDetailDTO delete(Long feature_id, Long product_id) {
        FeatureDetailId featureDetailId = featureDetailId(feature_id, product_id);
        Optional <FeatureDetail> featureDetail = featureDetailRepository.findById(featureDetailId);
        featureDetail.orElseThrow(() -> new NotFoundException(featureDetailId));
        FeatureDetailDTO featureDetailDTO = createFromE(featureDetail.get());
        try {
            featureDetailRepository.delete(featureDetail.get());
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(FeatureDetailDTO.class, featureDetailId);
        }
        return featureDetailDTO;
    }

    @Override
    public FeatureDetail createFromD(FeatureDetailDTO featureDetailDTO) {
        FeatureDetail featureDetail = modelMapper.map(featureDetailDTO, FeatureDetail.class);
        FeatureDetailId featureDetailId = featureDetailId(featureDetailDTO.getFeatureId(), featureDetailDTO.getProductId());
        featureDetail.setFeatureDetailsId(featureDetailId);
        return featureDetail;
    }

    @Override
    public FeatureDetailDTO createFromE(FeatureDetail featureDetail) {
        FeatureDetailDTO featureDetailDTO = modelMapper.map(featureDetail, FeatureDetailDTO.class);
        featureDetailDTO.setFeatureId(featureDetail.getFeatureDetailsId().getFeature().getFeatureId());
        featureDetailDTO.setProductId(featureDetail.getFeatureDetailsId().getProduct().getProductId());
        return featureDetailDTO;
    }

    @Override
    public FeatureDetail updateEntity(FeatureDetail featureDetail, FeatureDetailDTO featureDetailDTO) {
        if (featureDetail != null && featureDetailDTO != null){
            featureDetail.setFeatureDetailsId(featureDetailId(featureDetailDTO.getFeatureId(), featureDetailDTO.getProductId()));
        }
        return featureDetail;
    }
}
