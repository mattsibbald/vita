package de.unistuttgart.vis.vita.analysis.results;

import de.unistuttgart.vis.vita.model.document.Document;

/**
 * Provides everything one needs to know about a document to persist information about it
 */
public interface DocumentPersistenceContext {

  /**
   * Returns the complete document.
   *
   * @return The currently analysed document.
   */
  public Document getDocument();

  /**
   * Gets the id of the document being analyzed
   *
   * @return the document id
   */
  public String getDocumentId();

  /**
   * Returns the content id which is the same for all analysis with the same document content.
   * @return Document content id.
   */
  public String getDocumentContentId();

  /**
   * @return The name of the document/file.
   */
  public String getFileName();
}
