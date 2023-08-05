package com.n19dccn112.service;

import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.model.entity.Event;
import com.n19dccn112.model.enumeration.EventStatus;
import com.n19dccn112.repository.EventRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import com.n19dccn112.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class EventService implements IBaseService<EventDTO, Long>, IModelMapper<EventDTO, Event> {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public EventService(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventDTO> findAll() {
        return createFromEntities(eventRepository.findAll());
    }

    @Override
    public EventDTO findById(Long eventId) {
        return createFromE(eventRepository.getById(eventId));
    }

    @Override
    public EventDTO update(Long eventId, EventDTO eventDTO) {
        Optional <Event> event = eventRepository.findById(eventId);
        event.orElseThrow(() -> new NotFoundException(EventDTO.class, eventId));
        eventRepository.save(updateEntity(event.get(), eventDTO));
        return createFromE(event.get());
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        Event event = createFromD(eventDTO);
        event.setEventStatus(EventStatus.ACTICATED);
        eventRepository.save(event);
        return createFromE(event);
    }

    @Override
    public EventDTO delete(Long eventId) {
        Optional <Event> event = eventRepository.findById(eventId);
        event.orElseThrow(() -> new NotFoundException(EventDTO.class, eventId));
        EventDTO eventDTO = createFromE(event.get());
        try {
            eventRepository.delete(event.get());
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(EventDTO.class, eventId);
        }
        return eventDTO;
    }

    @Override
    public Event createFromD(EventDTO eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);

        Date currentDate = eventDTO.getStartDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, eventDTO.getAmoutEndDate());
        Date futureDate = calendar.getTime();

        event.setEndDate(futureDate);
        return event;
    }

    @Override
    public EventDTO createFromE(Event event) {
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        Long day = event.getEndDate().getTime() - event.getStartDate().getTime();
        eventDTO.setAmoutEndDate((int) (day / (24 * 60 * 60 * 1000)));
        return eventDTO;
    }

    @Override
    public Event updateEntity(Event event, EventDTO eventDTO) {
        if (event != null && eventDTO != null){
            event.setEventName(eventDTO.getEventName());
            event.setDescription(eventDTO.getDescription());

            Date currentDate = eventDTO.getStartDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, eventDTO.getAmoutEndDate());
            Date futureDate = calendar.getTime();

            event.setStartDate(currentDate);
            event.setEndDate(futureDate);

            event.setDiscountCode(eventDTO.getDiscountCode());
            event.setDiscountValue(eventDTO.getDiscountValue());
            event.setEventStatus(eventDTO.getEventStatus());
        }
        return event;
    }
}
