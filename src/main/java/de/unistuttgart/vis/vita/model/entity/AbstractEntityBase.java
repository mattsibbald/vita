package de.unistuttgart.vis.vita.model.entity;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlElement;

/**
 * An abstract superclass for all objects that can be persisted in a data base
 */
@MappedSuperclass
public abstract class AbstractEntityBase {
  @Id
  private String id;

  /**
   * Creates a new instance of AbstractEntityBase, generating a UUID.
   */
  public AbstractEntityBase() {
    id = UUID.randomUUID().toString();
  }

  /**
   * @return the id of this entity
   */
  @XmlElement
  public String getId() {
    return id;
  }

  /**
   * Sets the id of the entity to the given one.
   *
   * @param id - the new id for this entity
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Indicates whether some object represents the same persistable object as this one. Two objects
   * are considered equal if their ids are the same.
   *
   * @param obj - the object to be tested equals
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof AbstractEntityBase)) {
      return false;
    }

    AbstractEntityBase other = (AbstractEntityBase) obj;
    return getId().equals(other.getId());
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override public String toString() {
    return getClass().getName() + "#" + id;
  }
}
