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
@RequestMapping("api/EventProduct")
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

    @GetMapping("/{event_id}-{product_id}")
    public EventProductDTO get1(@PathVariable Long event_id, @PathVariable Long product_id) {
        return service.findById(event_id, product_id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{event_id}-{product_id}")
    public EventProductDTO update(@PathVariable Long event_id, @PathVariable Long product_id, @Valid @RequestBody EventProductDTO dto) {
        dto.setProductId(product_id);
        dto.setEventId(event_id);
        return service.update(event_id, product_id, dto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{event_id}-{product_id}")
    public EventProductDTO delete(@PathVariable Long event_id, @PathVariable Long product_id) {
        return service.delete(event_id, product_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public EventProductDTO insert(@Valid @RequestBody EventProductDTO d) {
        return getService().save(d);
    }
}
