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
@RequestMapping("api/OrderDetail")
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

    @GetMapping("/{order_id}-{product_id}")
    public OrderDetailDTO get1(@PathVariable Long order_id, @PathVariable Long product_id) {
        return service.findById(order_id, product_id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{order_id}-{product_id}")
    public OrderDetailDTO update(@PathVariable Long order_id, @PathVariable Long product_id, @Valid @RequestBody OrderDetailDTO dto) {
        dto.setProductId(product_id);
        dto.setOrderId(order_id);
        return service.update(order_id, product_id, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{order_id}-{product_id}")
    public OrderDetailDTO delete(@PathVariable Long order_id, @PathVariable Long product_id) {
        return service.delete(order_id, product_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public OrderDetailDTO insert(@Valid @RequestBody OrderDetailDTO d) {
        return getService().save(d);
    }
}
