package com.molistore.application.dao;

import java.util.List;

public interface DaoInterface<ENTITY> {

    ENTITY findById(Long id);

    List<ENTITY> findAll();

    boolean save(ENTITY entity);

    ENTITY update(ENTITY entity);

    void detach(ENTITY entity);

    void delete(ENTITY entity);

    void flush();

    @SuppressWarnings("unchecked")
    List<ENTITY> getAll();
}
