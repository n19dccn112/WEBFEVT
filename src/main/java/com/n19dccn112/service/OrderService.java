package com.n19dccn112.service;

import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.model.entity.*;
import com.n19dccn112.model.enumeration.OrderStatus;
import com.n19dccn112.model.key.OrderDetailId;
import com.n19dccn112.repository.OrderDetailRepository;
import com.n19dccn112.repository.OrderRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.repository.UserRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class OrderService implements IBaseService<OrderDTO, Long>, IModelMapper<OrderDTO, Order> {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailService orderDetailService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository ordersRepository, OrderDetailRepository orderDetailRepository, OrderDetailService orderDetailService, UserRepository userRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = ordersRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailService = orderDetailService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderDTO> findAll() {
        return createFromEntities(orderRepository.findAll());
    }

    public List<OrderDTO> findAll(Long userId) {
        return createFromEntities(orderRepository.findAllByUserUserId(userId));
    }

    public List<OrderDTO> findAll(Long userId, String status){
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            return createFromEntities(orderRepository.findAllByUserUserIdAndOrderStatus(userId, orderStatus.name()));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public OrderDTO findById(Long orderId) {
        Optional <Order> orders = orderRepository.findById(orderId);
        orders.orElseThrow(() -> new NotFoundException(Order.class, orderId));
        return createFromE(orders.get());
    }

    @Override
    public OrderDTO update(Long orderId, OrderDTO orderDTO) {
        Optional <Order> orders = orderRepository.findById(orderId);
        orders.orElseThrow(() -> new NotFoundException(Order.class, orderId));
        orderRepository.save(updateEntity(orders.get(), orderDTO));
        return createFromE(orders.get());
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = createFromD(orderDTO);
        order.setStatus(OrderStatus.PREPARE);
        order.setTime(new Date());
        orderRepository.save(order);

        for (Map.Entry<Long, Integer> entry : orderDTO.getDetails().entrySet()){
            OrderDetail orderDetail = new OrderDetail();
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrder(order);
            Optional<Product> product = productRepository.findById(entry.getKey());
            orderDetailId.setProduct(product.get());
            orderDetail.setOrderDetailId(orderDetailId);
            orderDetail.setAmount(entry.getValue());
            orderDetailRepository.save(orderDetail);

            product.orElseThrow(() -> new NotFoundException(ProductDTO.class, entry.getKey()));
            product.get().setRemain(product.get().getRemain()- entry.getValue());
            productRepository.save(product.get());
        }
        return createFromE(orderRepository.findOrderByPhone(orderDTO.getPhone()).get());
    }

    @Override
    public OrderDTO delete(Long orderId) {
        Optional <Order> order = orderRepository.findById(orderId);
        order.orElseThrow(() -> new NotFoundException(Order.class, orderId));
        OrderDTO orderDTO = createFromE(order.get());
        try {
            orderRepository.delete(order.get());
        }
        catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Category.class, orderId);
        }
        return orderDTO;
    }

    @Override
    public Order createFromD(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        user.orElseThrow(() -> new NotFoundException(UserDTO.class, orderDTO.getUserId()));
        order.setUser(user.get());
        order.setStatus(orderDTO.getStatus());
        return order;
    }

    @Override
    public OrderDTO createFromE(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setStatus(order.getStatus());
        Map<String, Integer> map = new HashMap<>();
        for (OrderDetail orderDetail: orderDetailRepository.findAllByOrderOrderId(order.getOrderId())){
            map.put(orderDetail.getOrderDetailId().getProduct().getProductId().toString(), orderDetail.getAmount());
        }
        return orderDTO;
    }

    @Override
    public Order updateEntity(Order order, OrderDTO orderDTO) {
        if ( order != null && orderDTO != null){
            order.setAddress(orderDTO.getAddress());
            order.setPhone(orderDTO.getPhone());
            order.setStatus(orderDTO.getStatus());
            order.setTime(orderDTO.getTime());
            if (orderDTO.getStatus() == OrderStatus.CANCELED)
                for (Map.Entry<Long, Integer> entry : orderDTO.getDetails().entrySet()){
                    OrderDetailDTO orderDetailDTO = orderDetailService.findById(orderDTO.getOrderId(), entry.getKey());
                    orderDetailDTO.setAmount(entry.getValue());
                    orderDetailRepository.save(orderDetailService.createFromD(orderDetailDTO));

                    Optional<Product> product = productRepository.findById(entry.getKey());
                    product.orElseThrow(() -> new NotFoundException(ProductDTO.class, entry.getKey()));
                    product.get().setRemain(product.get().getRemain() + entry.getValue());
                    productRepository.save(product.get());
                }
        }
        return order;
    }
}
