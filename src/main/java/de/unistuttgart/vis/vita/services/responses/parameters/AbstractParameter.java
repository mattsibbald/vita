/*
 * AbstractParameter.java
 *
 */

package de.unistuttgart.vis.vita.services.responses.parameters;

import java.lang.reflect.Type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({MinMaxParameter.class, BooleanParameter.class})
public abstract class AbstractParameter {

  protected String name;
  protected Type attributeType;
  protected String description;

  public AbstractParameter() {
  }

  public AbstractParameter(String name, Type attributeType, String description) {
    this.name = name;
    this.attributeType = attributeType;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Type getAttributeType() {
    return attributeType;
  }

  public void setAttributeType(Type attributeType) {
    this.attributeType = attributeType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}