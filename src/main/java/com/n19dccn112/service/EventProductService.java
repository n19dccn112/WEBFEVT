package com.n19dccn112.service;

import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.entity.Event;
import com.n19dccn112.model.entity.EventProduct;
import com.n19dccn112.model.entity.Product;
import com.n19dccn112.model.key.EventProductId;
import com.n19dccn112.repository.EventProductRepository;
import com.n19dccn112.repository.EventRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class EventProductService implements IModelMapper<EventProductDTO, EventProduct> {
    private final EventProductRepository eventProductRepository;
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;

    public EventProductService(EventProductRepository eventProductRepository, ModelMapper modelMapper, EventRepository eventRepository, ProductRepository productRepository) {
        this.eventProductRepository = eventProductRepository;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
    }

    public List<EventProductDTO> findAll() {
        return createFromEntities(eventProductRepository.findAll());
    }

    public List<EventProductDTO> findAll(Long eventId) {
        return createFromEntities(eventProductRepository.findAllByEventProductId_Event(eventId));
    }

    public EventProductId eventProductId(Event event, Product product){
        EventProductId eventProductId = new EventProductId();
        eventProductId.setEvent(event);
        eventProductId.setProduct(product);
        return eventProductId;
    }

    public EventProductDTO findById(Long eventId, Long productId) {
        EventProductId eventProductId = eventProductId(eventRepository.findById(eventId).get(),productRepository.findById(productId).get());
        Optional <EventProduct> eventProduct = eventProductRepository.findById(eventProductId);
        eventProduct.orElseThrow(() -> new NotFoundException(eventProductId));
        return createFromE(eventProduct.get());
    }

    public EventProductDTO update(Long eventId, Long productId, EventProductDTO eventProductDTO) {
        EventProductId eventProductId = eventProductId(eventRepository.findById(eventId).get(),productRepository.findById(productId).get());
        Optional <EventProduct> eventProduct = eventProductRepository.findById(eventProductId);
        eventProduct.orElseThrow(() -> new NotFoundException(eventProductId));
        eventProductRepository.save(updateEntity(eventProduct.get(), eventProductDTO));
        return createFromE(eventProduct.get());
    }

    public EventProductDTO save(EventProductDTO eventProductDTO) {
        EventProduct eventProduct = createFromD(eventProductDTO);
        eventProduct.setCreateDate(new Date());
        eventProduct.setUpdateDate(new Date());
        eventProductRepository.save(eventProduct);
        return createFromE(eventProduct);
    }

    public EventProductDTO delete(Long eventId, Long productId) {
        EventProductId eventProductId = eventProductId(eventRepository.findById(eventId).get(),productRepository.findById(productId).get());
        Optional <EventProduct> eventProduct = eventProductRepository.findById(eventProductId);
        eventProduct.orElseThrow(() -> new NotFoundException(eventProductId));
        try {
            eventProductRepository.delete(eventProduct.get());
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(EventProductDTO.class, eventProductId);
        }
        return createFromE(eventProduct.get());
    }

    @Override
    public EventProduct createFromD(EventProductDTO eventProductDTO) {
        EventProduct eventProduct = modelMapper.map(eventProductDTO, EventProduct.class);
        eventProduct.setEventProductId(eventProductId(eventRepository.findById(eventProductDTO.getEventId()).get(),productRepository.findById(eventProductDTO.getProductId()).get()));
        return eventProduct;
    }

    @Override
    public EventProductDTO createFromE(EventProduct eventProduct) {
        EventProductDTO eventProductDTO = modelMapper.map(eventProduct, EventProductDTO.class);
        eventProductDTO.setEventId(eventProduct.getEventProductId().getEvent().getEventId());
        eventProductDTO.setProductId(eventProduct.getEventProductId().getProduct().getProductId());
        return eventProductDTO;
    }

    @Override
    public EventProduct updateEntity(EventProduct eventProduct, EventProductDTO eventProductDTO) {
        if (eventProduct != null && eventProductDTO != null){
            eventProduct.setEventProductId(eventProductId(eventRepository.findById(eventProductDTO.getEventId()).get(),productRepository.findById(eventProductDTO.getProductId()).get()));
            eventProduct.setUpdateDate(new Date());
        }
        return eventProduct;
    }
}
