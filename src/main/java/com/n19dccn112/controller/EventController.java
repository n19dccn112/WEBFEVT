package com.n19dccn112.controller;

import com.n19dccn112.controller.Interface.IBaseController;
import com.n19dccn112.model.dto.CategoryDTO;
import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/events")
@Tag(name = "Event")
public class EventController implements IBaseController<EventDTO, Long, EventService> {
    @Resource
    @Getter
    private EventService service;

    @GetMapping("")
    public List<EventDTO> getAll() {
        return getService().findAll();
    }
}
