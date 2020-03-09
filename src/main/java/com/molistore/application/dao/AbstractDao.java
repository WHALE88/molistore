package com.molistore.application.dao;

import com.molistore.application.util.reflection.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

import static com.molistore.application.util.CommonUtils.isNotNull;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbstractDao<ENTITY> implements DaoInterface<ENTITY> {

    protected Class<ENTITY> persistentClass;

    @PersistenceContext(unitName = "lending-PU")
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        persistentClass = ReflectionUtils.getGenericParameterClass(this.getClass(), AbstractDao.class, 0);
    }

    @Override
    public ENTITY findById(Long id) {
        return entityManager.find(persistentClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ENTITY> findAll() {
        return entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<ENTITY> executeNamedQueryAndGetResultList(String namedQuery, Map<String, Object> params) {
        TypedQuery<?> query = entityManager.createNamedQuery(namedQuery, persistentClass);
        params.forEach(query::setParameter);
        return (List<ENTITY>) query.getResultList();
    }

    @Override
    public boolean save(ENTITY entity) {
        if (isNull(entity)) {
            return false;
        }
        entityManager.persist(entity);
        entityManager.flush();
        return true;
    }

    @Override
    public ENTITY update(ENTITY entity) {
        if (isNotNull(entity)) {
            try {
                ENTITY mergedEntity = entityManager.merge(entity);
                entityManager.flush();
                return mergedEntity;
            } catch (PersistenceException ex) {
                log.error("Update Error: {}", ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public void detach(ENTITY entity) {
        if (isNull(entity)) {
            return;
        }
        entityManager.detach(entity);
    }

    @Override
    public void delete(ENTITY entity) {
        entityManager.remove(entity);
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ENTITY> getAll() {
        return entityManager.createQuery("Select t from " + persistentClass.getSimpleName() + " t").getResultList();
    }
}
