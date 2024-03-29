package de.unistuttgart.vis.vita.model.document;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Formula;

/**
 * Represents the meta data of a Document, including information like title, author, publisher, year
 * of publication, genre and edition of the Document.
 */
@Embeddable
public class DocumentMetadata {

  // attributes
  @Column(length = 1000)
  private String title;

  @Column(nullable = true)
  private Boolean isUserDefinedTitle;

  @Column(length = 1000)
  private String author;

  @Column(length = 1000)
  private String publisher;

  /* Prevent that Hibernate sets DocumentMetadata and the attributes to null, by not creating a
  column for this DocumentMetadata object */
  @Formula("0")
  private int dummy;
  
  private Integer publishYear;

  @Column(length = 1000)
  private String genre;

  @Column(length = 1000)
  private String edition;

  /**
   * Creates a new instance of DocumentMetadata, setting all attributes to default values.
   */
  public DocumentMetadata() {
    // do nothing
  }

  /**
   * Creates a new instance of DocumentMetadata, setting title and author to the given values.
   * 
   * @param pTitle - the title of the Document this metadata refer to
   * @param pAuthor - the author of the Document this metadata refer to
   */
  public DocumentMetadata(String pTitle, String pAuthor) {
    this.title = pTitle;
    this.author = pAuthor;
  }

  /**
   * @return the title under which the Document is published
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title for the Document.
   *
   * @param newTitle The new title of the document.
   */
  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  /**
   * @return the author of the Document
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Set the author of the Document.
   * 
   * @param newAuthor - the author of the Document
   */
  public void setAuthor(String newAuthor) {
    this.author = newAuthor;
  }

  /**
   * @return the name of the publisher of this Document
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Sets the name of the publisher of this Document.
   * 
   * @param newPublisher - the name of the publisher
   */
  public void setPublisher(String newPublisher) {
    this.publisher = newPublisher;
  }

  /**
   * @return year when the Document was published
   */
  public Integer getPublishYear() {
    return publishYear;
  }

  /**
   * Sets the year of publication.
   * 
   * @param publishYear - the year when the document was published
   */

  public void setPublishYear(Integer publishYear) {
    this.publishYear = publishYear;
  }

  /**
   * @return the name of the genre
   */
  public String getGenre() {
    return genre;
  }

  /**
   * Sets the genre for the Document.
   * 
   * @param newGenre - the genre of the Document
   */
  public void setGenre(String newGenre) {
    this.genre = newGenre;
  }

  /**
   * @return the edition name of the Document
   */
  public String getEdition() {
    return edition;
  }

  /**
   * Sets the edition name of the document.
   * 
   * @param edition - the name of the edition of this Document
   */
  public void setEdition(String edition) {
    this.edition = edition;
  }

  /**
   * Indicates whether the title attribute is user-defined
   * @return true, if the user has set the title
   */
  public boolean isUserDefinedTitle() {
    return isUserDefinedTitle != null && isUserDefinedTitle;
  }

  /**
   * Sets the flag whether the title attribute is user-defined
   * @param isUserDefinedTitle true, if the user has set the title
   */
  public void setIsUserDefinedTitle(boolean isUserDefinedTitle) {
    this.isUserDefinedTitle = isUserDefinedTitle;
  }

}
