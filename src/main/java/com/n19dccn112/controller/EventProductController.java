package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.key.EventProductId;
import com.n19dccn112.service.EventProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/eventProducts")
@Tag(name = "EventProduct")
public class EventProductController{
    @Resource
    @Getter
    private EventProductService service;

    @GetMapping("")
    public List<EventProductDTO> getAll(@RequestParam(required = false) Long idEvent) {
        if (idEvent == null)
            return getService().findAll();
        else
            return getService().findAll(idEvent);
    }

    @GetMapping("/{eventId}-{productId}")
    public EventProductDTO get1(@PathVariable Long eventId, @PathVariable Long productId) {
        return service.findById(eventId, productId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{eventId}-{productId}")
    public EventProductDTO update(@PathVariable Long eventId, @PathVariable Long productId, @Valid @RequestBody EventProductDTO dto) {
        dto.setProductId(productId);
        dto.setEventId(eventId);
        return service.update(eventId, productId, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{eventId}-{productId}")
    public EventProductDTO delete(@PathVariable Long eventId, @PathVariable Long productId) {
        return service.delete(eventId, productId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public EventProductDTO insert(@Valid @RequestBody EventProductDTO d) {
        return getService().save(d);
    }
}
