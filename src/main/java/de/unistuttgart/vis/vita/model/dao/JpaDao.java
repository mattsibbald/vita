package de.unistuttgart.vis.vita.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Generic implementation of a data access object (DAO).
 * 
 * @param <T> the type of the entities to be accessed via this DAO
 * @param <I> the type of the entity id field
 */
public abstract class JpaDao<T, I extends Serializable> implements Dao<T, I> {
  
  private final Class<T> persistentClass;

  protected EntityManager em;

  /**
   * Creates a new instance of JpaDao for the given class using the also given EntityManager.
   *
   * @param persistedClass - the class of the entities to be accessed via this new dao
   * @param em - the EntityManager to be used
   */
  public JpaDao(Class<T> persistedClass, EntityManager em) {
    this.persistentClass = persistedClass;
    this.em = em;
  }

  /**
   * @return the class of the persisted objects
   */
  public Class<T> getPersistentClass() {
    return persistentClass;
  }

  /**
   * @return the prefix for named queries
   */
  public String getPersistentClassName() {
    return persistentClass.getSimpleName();
  }

  /**
   * Finds an entity by its given id.
   * 
   * @param id - the id of the entity to find
   * @return the entity with the given id
   */
  @Override
  public T findById(I id) {
      T result = em.find(getPersistentClass(), id);

    if (result == null) {
      throw new NoResultException("No entity of type '" + getPersistentClassName()
                                  + "' found with id '" + id + "'!");
    }
    
    return result;
  }

  /**
   * Finds all entities.
   * 
   * @return list of all entities
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAll() {
    return em.createQuery("From " + getPersistentClassName()).getResultList();
  }

  /**
   * Saves the given entity in the database.
   * 
   * @param entity - the entity to be saved
   */
  @Override
  public void save(T entity) {
    em.persist(entity);
  }

  /**
   * Updates the values of the given entity in the database.
   *
   * @param entity - the entity and its values to be updated in the database
   */
  @Override
  public void update(T entity) {
    em.merge(entity);
  }

  /**
   * Removes a given entity from the database.
   * 
   * @param entity - the entity to be removed
   */
  @Override
  public void remove(T entity) {
    em.remove(entity);
  }

  /**
   * Returns the single result of a given query.
   *
   * @param queryName - the name of the NamedQuery to be performed
   * @param parameters - parameters as name and value pairs, so the amount must be even
   * @return the single result for the given query
   */
  protected T queryOne(String queryName, Object... parameters) {
    TypedQuery<T> query = buildQuery(queryName, parameters);
    return query.getSingleResult();
  }

  /**
   * Returns a list of results for the given query.
   *
   * @param queryName - the name of the NamedQuery to be performed
   * @param parameters - a list of parameters as name and value pairs, so the amount must be even
   * @return a list of results for the given query
   */
  protected List<T> queryAll(String queryName, Object... parameters) {
    TypedQuery<T> query = buildQuery(queryName, parameters);
    return query.getResultList();
  }

  /**
   * Builds a TypedQuery for the given name and parameters and returns it.
   *
   * @param queryName - the name of the NamedQuery to be executed
   * @param parameters - parameters as name and value pairs, so the amount must be even
   * @return a TypedQuery with all the given parameters set
   */
  protected TypedQuery<T> buildQuery(String queryName, Object... parameters) {
    TypedQuery<T> query = em.createNamedQuery(queryName, persistentClass);
    if (parameters.length % 2 != 0) {
      throw new IllegalArgumentException("parameters must have an even number (names and their values");
    }

    for (int i = 0; i < parameters.length; i += 2) {
      Object key = parameters[i];
      if (!(key instanceof String)) {
        throw new IllegalArgumentException("Every even parameter must be the key, thus a string");
      }
      query.setParameter((String)key, parameters[i + 1]);
    }

    return query;
  }
  
}
