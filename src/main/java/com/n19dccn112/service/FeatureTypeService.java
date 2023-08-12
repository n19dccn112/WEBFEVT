package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureTypeDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.model.entity.FeatureType;
import com.n19dccn112.repository.FeatureRepository;
import com.n19dccn112.repository.FeatureTypeRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FeatureTypeService implements IBaseService<FeatureTypeDTO, Long>, IModelMapper<FeatureTypeDTO, FeatureType> {
    private final FeatureTypeRepository featuretypeRepository;
    private final FeatureRepository featureRepository;
    private final ModelMapper modelMapper;

    public FeatureTypeService(FeatureTypeRepository featuretypeRepository, FeatureRepository featureRepository, ModelMapper modelMapper) {
        this.featuretypeRepository = featuretypeRepository;
        this.featureRepository = featureRepository;
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
    @Transactional
    @Override
    public FeatureTypeDTO update(Long featuretypeId, FeatureTypeDTO featuretypeDTO) {
        FeatureType featuretype = featuretypeRepository.findById(featuretypeId).get();
        featuretype = updateEntity(featuretype, featuretypeDTO);
        try {
            List<Feature> features = featureRepository.findAllByFeaturetype_FeatureTypeId(featuretypeId);
            for (int i=0; i<featuretypeDTO.getSpecific().size(); i++){
                if (i<features.size()) {
                    features.get(i).setSpecific(featuretypeDTO.getSpecific().get(i));
                    featureRepository.save(features.get(i));
                }else{
                    Feature feature = new Feature();
                    feature.setFeaturetype(featuretype);
                    feature.setSpecific(featuretypeDTO.getSpecific().get(i));
                    featureRepository.save(feature);
                }
            }
        }catch (Exception e){
        }
        featuretypeRepository.save(featuretype);
        return createFromE(featuretype);
    }

    @Override
    public FeatureTypeDTO save(FeatureTypeDTO featuretypeDTO) {
        featuretypeRepository.save(createFromD(featuretypeDTO));
        FeatureType featuretype = featuretypeRepository.findByMaxId();
        try {
            for (String specific: featuretypeDTO.getSpecific()) {
                Feature feature = new Feature();
                feature.setFeaturetype(featuretype);
                feature.setSpecific(specific);
                featureRepository.save(feature);
            }
        }catch (Exception e){
        }
        return featuretypeDTO;
    }
    @Transactional
    @Override
    public FeatureTypeDTO delete(Long featureTypeId) {
        Optional <FeatureType> featureType = featuretypeRepository.findById(featureTypeId);
        featureType.orElseThrow(() -> new NotFoundException(FeatureTypeDTO.class, featureTypeId));
        FeatureTypeDTO featureTypeDTO = createFromE(featureType.get());
        try {
            for (Feature feature: featureRepository.findAllByFeaturetype_FeatureTypeId(featureTypeId)){
                featureRepository.delete(feature);
            }
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
        try {
            List<Feature> features = featureRepository.findAllByFeaturetype_FeatureTypeId(featuretype.getFeatureTypeId());
            List<String> specific = new ArrayList<>();
            for (Feature feature: features){
                specific.add(feature.getSpecific());
            }
            featuretypeDTO.setSpecific(specific);
        }catch (Exception e){
        }
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
