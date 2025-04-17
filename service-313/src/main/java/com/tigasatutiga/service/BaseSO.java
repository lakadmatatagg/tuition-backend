package com.tigasatutiga.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

public interface BaseSO<E, M, ID extends Serializable> {
    M save(final E object);

    @Transactional
    M saveAndFlush(E entity);

    M saveModel(final M object);

    Optional<M> findById(final ID id) throws Exception;

    Optional<E> findEntityById(final ID id);

    void delete(ID id);

    void setDependency(E entity);
}
