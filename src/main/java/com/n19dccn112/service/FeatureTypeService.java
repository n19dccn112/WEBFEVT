package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureTypeDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.FeatureType;
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
public class FeatureTypeService implements IBaseService<FeatureTypeDTO, Long>, IModelMapper<FeatureTypeDTO, FeatureType> {
    private final FeatureTypeRepository featuretypeRepository;
    private final ModelMapper modelMapper;

    public FeatureTypeService(FeatureTypeRepository featuretypeRepository, ModelMapper modelMapper) {
        this.featuretypeRepository = featuretypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeatureTypeDTO> findAll() {
        return createFromEntities(featuretypeRepository.findAll());
    }

    @Override
    public FeatureTypeDTO findById(Long featuretypeId) {
        Optional <FeatureType> featuretype = featuretypeRepository.findById(featuretypeId);
        featuretype.orElseThrow(() -> new NotFoundException(FeatureTypeDTO.class, featuretypeId));
        return createFromE(featuretype.get());
    }

    @Override
    public FeatureTypeDTO update(Long featuretypeId, FeatureTypeDTO featuretypeDTO) {
        Optional <FeatureType> featuretype = featuretypeRepository.findById(featuretypeId);
        featuretype.orElseThrow(() -> new NotFoundException(FeatureTypeDTO.class, featuretypeId));
        featuretypeRepository.save(updateEntity(featuretype.get(), featuretypeDTO));
        return createFromE(featuretype.get());
    }

    @Override
    public FeatureTypeDTO save(FeatureTypeDTO featuretypeDTO) {
        featuretypeRepository.save(createFromD(featuretypeDTO));
        return featuretypeDTO;
    }

    @Override
    public FeatureTypeDTO delete(Long featureTypeId) {
        Optional <FeatureType> featureType = featuretypeRepository.findById(featureTypeId);
        featureType.orElseThrow(() -> new NotFoundException(FeatureTypeDTO.class, featureTypeId));
        FeatureTypeDTO featureTypeDTO = createFromE(featureType.get());
        try {
            featuretypeRepository.delete(featureType.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, featureTypeId);
        }
        return featureTypeDTO;
    }

    @Override
    public FeatureType createFromD(FeatureTypeDTO featuretypeDTO) {
        FeatureType featuretype = modelMapper.map(featuretypeDTO, FeatureType.class);
        return featuretype;
    }

    @Override
    public FeatureTypeDTO createFromE(FeatureType featuretype) {
        FeatureTypeDTO featuretypeDTO = modelMapper.map(featuretype, FeatureTypeDTO.class);
        return featuretypeDTO;
    }

    @Override
    public FeatureType updateEntity(FeatureType featuretype, FeatureTypeDTO featuretypeDTO) {
        if (featuretype != null && featuretypeDTO != null){
            featuretype.setName(featuretypeDTO.getName());
            featuretype.setUnit(featuretypeDTO.getUnit());
        }
        return featuretype;
    }
}
