package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/Order")
@Tag(name = "Order")
public class OrderController implements IBaseController<OrderDTO, Long, OrderService> {
    @Resource
    @Getter
    private OrderService service;

    @GetMapping("")
    public List<OrderDTO> getAll(@RequestParam(required = false) Long userId,
                                 @RequestParam(required = false) String status) {
        if (userId != null && status != null) {
            return getService().findAll(userId, status);
        }if (userId != null) {
            return getService().findAll(userId);
        }if (userId == null && status == null) {
            return getService().findAll();
        }
        return null;
    }
}
