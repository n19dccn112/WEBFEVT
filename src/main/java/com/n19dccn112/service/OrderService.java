package com.n19dccn112.service;

import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Order;
import com.n19dccn112.model.entity.User;
import com.n19dccn112.model.enumeration.OrderStatus;
import com.n19dccn112.repository.OrderRepository;
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
public class OrderService implements IBaseService<OrderDTO, Long>, IModelMapper<OrderDTO, Order> {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository ordersRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = ordersRepository;
        this.userRepository = userRepository;
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
        orderRepository.save(createFromD(orderDTO));
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
        try {
            Optional<User> user = userRepository.findById(orderDTO.getOrderId());
            user.orElseThrow(() -> new NotFoundException(UserDTO.class, orderDTO.getUserId()));
            order.setUser(user.get());
        }catch (Exception e){
            order.setUser(null);
        }
        return order;
    }

    @Override
    public OrderDTO createFromE(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        try {
            orderDTO.setUserId(order.getUser().getUserId());
        }catch (Exception e){
            orderDTO.setUserId(null);
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
        }
        return order;
    }
}
