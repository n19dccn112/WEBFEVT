package com.n19dccn112.service;

import com.n19dccn112.model.dto.RateDTO;
import com.n19dccn112.model.entity.Rate;
import com.n19dccn112.model.key.RateId;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.repository.RateRepository;
import com.n19dccn112.repository.UserRepository;
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
public class RateService implements IModelMapper<RateDTO, Rate> {
    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public RateService(RateRepository ratesRepository, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.rateRepository = ratesRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public RateId rateId(Long userId, Long productId){
        RateId rateId = new RateId();
        rateId.setUser(userRepository.findById(userId).get());
        rateId.setProduct(productRepository.findById(productId).get());
        return rateId;
    }

    public List<RateDTO> findAll() {
        return createFromEntities(rateRepository.findAll());
    }

    public List<RateDTO> findAll(Long productId) {
        return createFromEntities(rateRepository.findAllByRateIdProduct_ProductId(productId));
    }

    public RateDTO findById(Long userId, Long productId) {
        RateId rateId = rateId(userId, productId);
        Optional <Rate> rate = rateRepository.findById(rateId);
        rate.orElseThrow(() -> new NotFoundException(rateId));
        return createFromE(rate.get());
    }

    public RateDTO update(Long userId, Long productId, RateDTO rateDTO) {
        RateId rateId = rateId(userId, productId);
        Optional <Rate> rate = rateRepository.findById(rateId);
        rate.orElseThrow(() -> new NotFoundException(rateId));
        rateRepository.save(updateEntity(rate.get(), rateDTO));
        return createFromE(rate.get());
    }

    public RateDTO save(RateDTO rateDTO) {
        Rate rate = createFromD(rateDTO);
        rateRepository.save(rate);
        return createFromE(rate);
    }

    public RateDTO delete(Long userId, Long productId) {
        RateId rateId = rateId(userId, productId);
        Optional <Rate> rate = rateRepository.findById(rateId);
        rate.orElseThrow(() -> new NotFoundException(rateId));
        RateDTO rateDTO = createFromE(rate.get());
        try {
            rateRepository.delete(rate.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(RateDTO.class, rateId);
        }
        return rateDTO;
    }

    public Rate createFromD(RateDTO rateDTO) {
        Rate rate = modelMapper.map(rateDTO, Rate.class);
        RateId rateId = rateId(rateDTO.getUserId(), rateDTO.getProductId());
        rate.setRateId(rateId);
        return rate;
    }

    public RateDTO createFromE(Rate rate) {
        RateDTO rateDTO = modelMapper.map(rate, RateDTO.class);
        rateDTO.setProductId(rate.getRateId().getProduct().getProductId());
        rateDTO.setUserId(rate.getRateId().getUser().getUserId());
        return rateDTO;
    }

    public Rate updateEntity(Rate rate, RateDTO rateDTO) {
        if (rate != null && rateDTO != null){
            RateId rateId = rateId(rateDTO.getUserId(), rateDTO.getProductId());
            rate.setRateId(rateId);
            rate.setPoint(rateDTO.getPoint());
            rate.setComment(rateDTO.getComment());
        }
        return rate;
    }
}
