package de.unistuttgart.vis.vita.analysis.results;

import de.unistuttgart.vis.vita.model.entity.BasicEntity;

import java.util.Collection;

/**
 * The result of detecting entities
 *
 * All the properties of BasicEntity will be populated. The algorith may decide to merge several
 * names to one entity; the names will be available via getNameAttributes(). No other attributes
 * will be detected. The basic relations between the entities is analyzed.
 */
public interface BasicEntityCollection {

  /**
   * Gets the detected entities
   *
   * @return the entities
   */
  public Collection<BasicEntity> getEntities();
}
