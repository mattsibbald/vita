package de.unistuttgart.vis.vita.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import javax.persistence.*;

import de.unistuttgart.vis.vita.model.document.Chapter;

/**
 * Represents a data access object for accessing Chapters.
 */
@MappedSuperclass
@NamedQueries({
  @NamedQuery(name = "Chapter.findAllChapters",
      query = "SELECT c "
              + "FROM Chapter c"),

  @NamedQuery(name = "Chapter.findChapterById",
    query = "SELECT c "
            + "FROM Chapter c "
            + "WHERE c.id = :chapterId"),

  @NamedQuery(name = "Chapter.findChapterByTitle",
    query = "SELECT c "
            + "FROM Chapter c "
            + "WHERE c.title = :chapterTitle"),

  @NamedQuery(name = "Chapter.findChapterByOffset",
    query = "SELECT c "
            + "FROM Document d, DocumentPart dp, Chapter c "
            + "WHERE d.id = :documentId "
            + "AND dp MEMBER OF d.content.parts "
            + "AND c MEMBER OF dp.chapters "
            + "AND :offset BETWEEN c.range.start.offset AND c.range.end.offset"),

  @NamedQuery(name = "Chapter.getAverageLength",
    query = "SELECT AVG(c.length) "
            + "FROM Document d, DocumentPart dp, Chapter c "
            + "WHERE d.id = :documentId "
            + "AND dp MEMBER OF d.content.parts "
            + "AND c MEMBER OF dp.chapters")})
public class ChapterDao extends JpaDao<Chapter, String> {

  private long avgChapterLength;

  private static final String DOCUMENT_ID_PARAMETER = "documentId";
  private static final String OFFSET_PARAMETER = "offset";
  private static final String CHAPTER_TITLE_PARAMETER = "title";

  /**
   * Creates a new data access object to access Chapter using the given {@link EntityManager}.
   * 
   * @param em - the EntityManager to be used in the new ChapterDao
   */
  public ChapterDao(EntityManager em) {
    super(Chapter.class, em);
  }

  /**
   * Find the Chapter with the given name.
   * 
   * @param chapterTitle - the title of the Chapter to search for
   * @return the Chapter matching the given title
   */
  public Chapter findChapterByTitle(String chapterTitle) {
    TypedQuery<Chapter> titleQuery = em.createNamedQuery("Chapter.findChapterByTitle",
                                                         Chapter.class);
    titleQuery.setParameter(CHAPTER_TITLE_PARAMETER, chapterTitle);
    return titleQuery.getSingleResult();
  }
  
  /**
   * Finds the surrounding Chapter of a given offset.
   * 
   * @param docId - the id of the Document to search in
   * @param offset - the offset which lays in the chapter to find
   * @return the surrounding Chapter
   */
  public Chapter findChapterByOffset(String docId, int offset) {
    return getChaptersByOffsetQuery(docId, offset).getSingleResult();
  }
  
  /**
   * Returns the surrounding Chapters of a given offset.
   * 
   * @param docId - the id of the Document to search in
   * @param offset - the offset which lays in the chapters to find
   * @return list of surrounding chapters
   */
  public List<Chapter> findChaptersByOffset(String docId, int offset) {
    return getChaptersByOffsetQuery(docId, offset).getResultList();
  }

  /**
   * Returns a TypedQuery for Chapters surrounding a given offset in an also given Document.
   *
   * @param docId - the id of the Document to search in
   * @param offset - the char offset of the position
   * @return the query for the Chapters at the given position
   */
  private TypedQuery<Chapter> getChaptersByOffsetQuery(String docId, int offset) {
    TypedQuery<Chapter> query = em.createNamedQuery("Chapter.findChapterByOffset", Chapter.class);
    query.setParameter(DOCUMENT_ID_PARAMETER, docId);
    query.setParameter(OFFSET_PARAMETER, offset);
    return query;
  }

  /**
   * Returns the average length of a chapter in the document with the given id.
   *
   * @param documentId - the id of the Document to search in
   * @return the average Chapter length in chars
   */
  public long getAverageChapterLength(String documentId) {
    // average chapter length won't change so query it only once
    if (avgChapterLength == 0) {
      Query lengthQuery = em.createNamedQuery("Chapter.getAverageLength");
      lengthQuery.setParameter(DOCUMENT_ID_PARAMETER, documentId);
      avgChapterLength = Math.round((double)lengthQuery.getSingleResult());
    }
    return avgChapterLength;
  }

}
