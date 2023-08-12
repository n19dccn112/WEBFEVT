package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.model.enumeration.OrderStatus;
import com.n19dccn112.model.key.OrderDetailId;
import com.n19dccn112.service.OrderDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orderDetails")
@Tag(name = "OrderDetail")
public class OrderDetailController {
    @Resource
    @Getter
    private OrderDetailService service;

    @GetMapping("")
    public List<OrderDetailDTO> getAll(@RequestParam(required = false) Long orderId) {
        if (orderId != null) {
            return getService().findAll(orderId);
        }else {
            return getService().findAll();
        }
    }

    @GetMapping("/{orderId}-{productId}")
    public OrderDetailDTO get1(@PathVariable Long orderId, @PathVariable Long productId) {
        return service.findById(orderId, productId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{orderId}-{productId}")
    public OrderDetailDTO update(@PathVariable Long orderId, @PathVariable Long productId, @Valid @RequestBody OrderDetailDTO dto) {
        dto.setProductId(productId);
        dto.setOrderId(orderId);
        return service.update(orderId, productId, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{orderId}-{productId}")
    public OrderDetailDTO delete(@PathVariable Long orderId, @PathVariable Long productId) {
        return service.delete(orderId, productId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public OrderDetailDTO insert(@Valid @RequestBody OrderDetailDTO d) {
        return getService().save(d);
    }
}
