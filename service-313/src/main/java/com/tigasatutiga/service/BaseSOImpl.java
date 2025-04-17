package com.tigasatutiga.service;

import com.tigasatutiga.mapper.BaseMapper;
import com.tigasatutiga.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BaseSOImpl<E, M, ID extends Serializable> implements BaseSO<E, M, ID> {
    protected final BaseRepository<E, ID> repository;
    private final BaseMapper<E, M> customMapper;
    protected M rawModel;

    protected BaseSOImpl(BaseRepository<E, ID> repository, BaseMapper<E, M> mapper) {
        this.repository = repository;
        this.customMapper = mapper;
    }

    protected BaseSOImpl(BaseRepository<E, ID> repository) {
        this.repository = repository;
        this.customMapper = null;
    }

    @Override
    @Transactional
    public M save(E entity) {
        E saved = repository.save(entity);
        if (Objects.isNull(customMapper)) {
            log.error("CustomMapper is not defined");
            throw new RuntimeException("CustomMapper is not defined");
        }
        return customMapper.toModel(saved);
    }

    @Override
    @Transactional
    public M saveAndFlush(E entity) {
        E saved = repository.saveAndFlush(entity);
        if (Objects.isNull(customMapper)) {
            log.error("CustomMapper is not defined");
            throw new RuntimeException("CustomMapper is not defined");
        }
        return customMapper.toModel(saved);
    }

    @Override
    @Transactional
    public M saveModel(M model) {
        if (Objects.isNull(customMapper)) {
            log.error("CustomMapper is not defined");
            throw new RuntimeException("CustomMapper is not defined");
        }
        E e = customMapper.toEntity(model);
        preSaveModel(model, e);
        setDependency(e);
        E savedEntity = repository.save(e);
        M savedModel = customMapper.toModel(savedEntity);
        postSaveModel(savedModel, savedEntity, model);
        return savedModel;
    }

    @Override
    @Transactional
    public Optional<M> findById(ID id) throws Exception {
        Optional<E> saved = repository.findById(id);
        if (Objects.isNull(customMapper)) {
            log.error("CustomMapper is not defined");
            throw new RuntimeException("CustomMapper is not defined");
        }
        return Optional.ofNullable(customMapper.toModel(saved.orElse(null)));
    }

    @Override
    @Transactional
    public Optional<E> findEntityById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    protected void preSaveModel(M model, E entity) {
        // The method is an intentionally-blank override.
    }

    protected void postSaveModel(M model, E entity, M rawModel) {}

    @Override
    public void setDependency(E entity) {
        // The method is an intentionally-blank override.
    }
}
