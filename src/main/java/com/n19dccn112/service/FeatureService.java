package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.repository.FeatureRepository;
import com.n19dccn112.repository.FeatureTypeRepository;
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
public class FeatureService implements IBaseService<FeatureDTO, Long>, IModelMapper<FeatureDTO, Feature> {
    private final FeatureRepository featureRepository;
    private final FeatureTypeRepository featureTypeRepository;
    private final ModelMapper modelMapper;

    public FeatureService(FeatureRepository featuresRepository, FeatureTypeRepository featureTypeRepository, ModelMapper modelMapper) {
        this.featureRepository = featuresRepository;
        this.featureTypeRepository = featureTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeatureDTO> findAll() {
        return createFromEntities(featureRepository.findAll());
    }

    public List<FeatureDTO> findAll1(Long featureTypeId) {
        return createFromEntities(featureRepository.findAllByFeaturetype_FeatureTypeId(featureTypeId));
    }

    public List<FeatureDTO> findAll(Long productId) {
        return createFromEntities(featureRepository.findAllByProductId(productId));
    }

    @Override
    public FeatureDTO findById(Long featureId) {
        Optional <Feature> feature = featureRepository.findById(featureId);
        feature.orElseThrow(() -> new NotFoundException(FeatureDTO.class, featureId));
        return createFromE(feature.get());
    }

    @Override
    public FeatureDTO update(Long featureId, FeatureDTO featureDTO) {
        Optional <Feature> features = featureRepository.findById(featureId);
        features.orElseThrow(() -> new NotFoundException(FeatureDTO.class, featureId));
        featureRepository.save(updateEntity(features.get(), featureDTO));
        return createFromE(features.get());
    }

    @Override
    public FeatureDTO save(FeatureDTO featureDTO) {
        featureRepository.save(createFromD(featureDTO));
        return featureDTO;
    }

    @Override
    public FeatureDTO delete(Long featureId) {
        Optional <Feature> features = featureRepository.findById(featureId);
        features.orElseThrow(() -> new NotFoundException(FeatureDTO.class, featureId));
        FeatureDTO featureDTO = createFromE(features.get());
        try {
            featureRepository.delete(features.get());
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(FeatureDTO.class, featureId);
        }
        return featureDTO;
    }

    @Override
    public Feature createFromD(FeatureDTO featureDTO) {
        Feature feature = modelMapper.map(featureDTO, Feature.class);
        feature.setFeaturetype(featureTypeRepository.findById(featureDTO.getFeatureTypeId()).get());
        return feature;
    }

    @Override
    public FeatureDTO createFromE(Feature feature) {
        FeatureDTO featureDTO = modelMapper.map(feature, FeatureDTO.class);
        featureDTO.setFeatureTypeId(feature.getFeaturetype().getFeatureTypeId());
        return featureDTO;
    }

    @Override
    public Feature updateEntity(Feature feature, FeatureDTO featureDTO) {
        if (feature != null && featureDTO != null){
            feature.setPoint(featureDTO.getPoint());
            feature.setSpecific(featureDTO.getSpecific());
            feature.setFeaturetype(featureTypeRepository.findById(featureDTO.getFeatureTypeId()).get());
        }
        return feature;
    }
}
