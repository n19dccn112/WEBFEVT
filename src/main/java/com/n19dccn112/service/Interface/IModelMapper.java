package com.n19dccn112.service.Interface;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface IModelMapper <D, E>{
    E createFromD(D dto);
    D createFromE(E entity);
    E updateEntity(E entity, D dto);
    default List<D> createFromEntities(final Collection<E> entities){
        return entities.stream()
                .map(this::createFromE)
                .collect(Collectors.toList());
    }
}
