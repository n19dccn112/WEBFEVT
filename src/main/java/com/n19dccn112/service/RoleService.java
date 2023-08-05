package com.n19dccn112.service;

import com.n19dccn112.model.dto.RateDTO;
import com.n19dccn112.model.dto.RoleDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Role;
import com.n19dccn112.repository.RoleRepository;
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
public class RoleService implements IBaseService<RoleDTO, Long>, IModelMapper<RoleDTO, Role> {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDTO> findAll() {
        return createFromEntities(roleRepository.findAll());
    }

    @Override
    public RoleDTO findById(Long roleId) {
        Optional <Role> role = roleRepository.findById(roleId);
        role.orElseThrow(() -> new NotFoundException(RateDTO.class, roleId));
        return createFromE(role.get());
    }

    @Override
    public RoleDTO update(Long roleId, RoleDTO roleDTO) {
        Optional <Role> role = roleRepository.findById(roleId);
        role.orElseThrow(() -> new NotFoundException(RateDTO.class, roleId));
        roleRepository.save(updateEntity(role.get(), roleDTO));
        return createFromE(role.get());
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        roleRepository.save(createFromD(roleDTO));
        return roleDTO;
    }

    @Override
    public RoleDTO delete(Long roleId) {
        Optional <Role> role = roleRepository.findById(roleId);
        role.orElseThrow(() -> new NotFoundException(RoleDTO.class, roleId));
        RoleDTO roleDTO = createFromE(role.get());
        try {
            roleRepository.delete(role.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, roleId);
        }
        return roleDTO;
    }

    @Override
    public Role createFromD(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        return role;
    }

    @Override
    public RoleDTO createFromE(Role role) {
        RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
        return roleDTO;
    }

    @Override
    public Role updateEntity(Role role, RoleDTO roleDTO) {
        if (role != null && roleDTO != null){
            role.setName(roleDTO.getName());
        }
        return role;
    }
}
