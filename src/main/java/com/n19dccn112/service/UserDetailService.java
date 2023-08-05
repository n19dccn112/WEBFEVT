package com.n19dccn112.service;

import com.n19dccn112.model.dto.UserDetailDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.UserDetail;
import com.n19dccn112.repository.UserDetailRepository;
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
public class UserDetailService implements IBaseService<UserDetailDTO, Long>, IModelMapper<UserDetailDTO, UserDetail> {
    private final UserDetailRepository userDetailRepository;
    private final ModelMapper modelMapper;

    public UserDetailService(UserDetailRepository userDetailRepository, ModelMapper modelMapper) {
        this.userDetailRepository = userDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDetailDTO> findAll() {
        return createFromEntities(userDetailRepository.findAll());
    }

    @Override
    public UserDetailDTO findById(Long userDetailId) {
        Optional <UserDetail> userDetail = userDetailRepository.findById(userDetailId);
        userDetail.orElseThrow(() -> new NotFoundException(UserDetailDTO.class, userDetailId));
        return createFromE(userDetail.get());
    }

    @Override
    public UserDetailDTO update(Long userDetailId, UserDetailDTO userDetailDTO) {
        Optional <UserDetail> userDetail = userDetailRepository.findById(userDetailId);
        userDetail.orElseThrow(() -> new NotFoundException(UserDetailDTO.class, userDetailId));
        userDetailRepository.save(updateEntity(userDetail.get(), userDetailDTO));
        return createFromE(userDetail.get());
    }

    @Override
    public UserDetailDTO save(UserDetailDTO userDetailDTO) {
        userDetailRepository.save(createFromD(userDetailDTO));
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO delete(Long userDetailId) {
        Optional <UserDetail> userDetail = userDetailRepository.findById(userDetailId);
        userDetail.orElseThrow(() -> new NotFoundException(UserDetailDTO.class, userDetailId));
        UserDetailDTO userDetailDTO = createFromE(userDetail.get());
        try {
            userDetailRepository.delete(userDetail.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, userDetailId);
        }
        return userDetailDTO;
    }

    @Override
    public UserDetail createFromD(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = modelMapper.map(userDetailDTO, UserDetail.class);
        return userDetail;
    }

    @Override
    public UserDetailDTO createFromE(UserDetail userDetail) {
        UserDetailDTO userDetailDTO = modelMapper.map(userDetail, UserDetailDTO.class);
        return userDetailDTO;
    }

    @Override
    public UserDetail updateEntity(UserDetail userDetail, UserDetailDTO userDetailDTO) {
        if (userDetail != null && userDetailDTO != null){
            userDetail.setAddress(userDetailDTO.getAddress());
            userDetail.setName(userDetailDTO.getName());
            userDetail.setAddressDefault(userDetailDTO.getAddressDefault());
        }
        return userDetail;
    }
}
