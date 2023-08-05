package com.n19dccn112.service;

import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.model.entity.OrderDetail;
import com.n19dccn112.model.key.OrderDetailId;
import com.n19dccn112.repository.OrderDetailRepository;
import com.n19dccn112.repository.OrderRepository;
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
public class OrderDetailService implements IModelMapper<OrderDetailDTO, OrderDetail> {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public OrderDetailId orderDetailId (Long orderId, Long productId){
        OrderDetailId orderDetailId = new OrderDetailId();
        orderDetailId.setOrder(orderRepository.findById(orderId).get());
        orderDetailId.setProduct(productRepository.findById(productId).get());
        return orderDetailId;
    }

    public List<OrderDetailDTO> findAll() {
        return createFromEntities(orderDetailRepository.findAll());
    }

    public List<OrderDetailDTO> findAll(Long orderId) {
        return createFromEntities(orderDetailRepository.findAllByOrderOrderId(orderId));
    }

    public OrderDetailDTO findById(Long orderId, Long productId) {
        OrderDetailId orderDetailId = orderDetailId(orderId, productId);
        Optional <OrderDetail> orderDetails = orderDetailRepository.findById(orderDetailId);
        orderDetails.orElseThrow(() -> new NotFoundException(orderDetailId));
        return createFromE(orderDetails.get());
    }

    public OrderDetailDTO update(Long orderId, Long productId, OrderDetailDTO orderDetailDTO) {
        OrderDetailId orderDetailId = orderDetailId(orderId, productId);
        Optional <OrderDetail> orderDetails = orderDetailRepository.findById(orderDetailId);
        orderDetails.orElseThrow(() -> new NotFoundException(orderDetailId));
        orderDetailRepository.save(updateEntity(orderDetails.get(), orderDetailDTO));
        return createFromE(orderDetails.get());
    }

    public OrderDetailDTO save(OrderDetailDTO orderDetailsDTO) {
        OrderDetail orderDetail = createFromD(orderDetailsDTO);
        orderDetailRepository.save(orderDetail);
        return orderDetailsDTO;
    }

    public OrderDetailDTO delete(Long orderId, Long productId) {
        OrderDetailId orderDetailId = orderDetailId(orderId, productId);
        Optional <OrderDetail> orderDetails = orderDetailRepository.findById(orderDetailId);
        orderDetails.orElseThrow(() -> new NotFoundException(orderDetailId));
        OrderDetailDTO orderDetailDTO = createFromE(orderDetails.get());
        try {
            orderDetailRepository.delete(orderDetails.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(OrderDetailDTO.class, orderDetailId);
        }
        return orderDetailDTO;
    }

    @Override
    public OrderDetail createFromD(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        OrderDetailId orderDetailId = orderDetailId(orderDetailDTO.getOrderId(), orderDetailDTO.getProductId());
        orderDetail.setOrderDetailId(orderDetailId);
        return orderDetail;
    }

    @Override
    public OrderDetailDTO createFromE(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
        orderDetailDTO.setOrderId(orderDetail.getOrderDetailId().getOrder().getOrderId());
        orderDetailDTO.setProductId(orderDetail.getOrderDetailId().getProduct().getProductId());
        return orderDetailDTO;
    }

    @Override
    public OrderDetail updateEntity(OrderDetail orderDetail, OrderDetailDTO orderDetailDTO) {
        if (orderDetail != null && orderDetailDTO != null){
            OrderDetailId orderDetailId = orderDetailId(orderDetailDTO.getOrderId(), orderDetailDTO.getProductId());
            orderDetail.setOrderDetailId(orderDetailId);
            orderDetail.setAmount(orderDetailDTO.getAmount());
        }
        return orderDetail;
    }
}
