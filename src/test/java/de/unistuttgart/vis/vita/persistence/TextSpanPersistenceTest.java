package de.unistuttgart.vis.vita.persistence;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import de.unistuttgart.vis.vita.model.document.TextPosition;
import de.unistuttgart.vis.vita.model.document.TextSpan;

public class TextSpanPersistenceTest extends AbstractPersistenceTest {
  
  // test data
  private static final int TEST_TEXT_SPAN_START = 10000;
  private static final int TEST_TEXT_SPAN_END = 11000;
  private static final int TEST_TEXT_SPAN_DIFF = 1000;

  @Test
  public void testPersistOneTextSpan() {
    // first set up a TextSpan
    TextSpan ts = createTestTextSpan();
    
    // persist this TextSpan
    em.persist(ts);
    startNewTransaction();
    
    // read persisted TextSpans from database
    List<TextSpan> spans = readTextSpansFromDb();
    
    // check whether data is correct
    assertEquals(1, spans.size());
    TextSpan readTextSpan = spans.get(0);
    checkData(readTextSpan);
  }

  /**
   * Creates a new TextSpan, setting start and end to test values.
   * 
   * @return test text span
   */
  private TextSpan createTestTextSpan() {
    
    TextPosition start = new TextPosition(null, TEST_TEXT_SPAN_START);
    TextPosition end = new TextPosition(null, TEST_TEXT_SPAN_END);
    
    return new TextSpan(start, end);
  }
  
  /**
   * Reads TextSpans from database and returns them.
   * 
   * @return list of text spans
   */
  private List<TextSpan> readTextSpansFromDb() {
    TypedQuery<TextSpan> query = em.createQuery("from TextSpan", TextSpan.class);
    List<TextSpan> spans = query.getResultList();
    return spans;
  }
  
  /**
   * Checks whether the given TextSpan is not <code>null</code> and includes the correct test data.
   * 
   * @param textSpanToCheck - the TextSpan which should be checked
   */
  private void checkData(TextSpan textSpanToCheck) {
    assertNotNull(textSpanToCheck);
    assertNotNull(textSpanToCheck.getId());
    
    int start = textSpanToCheck.getStart().getOffset();
    int end = textSpanToCheck.getEnd().getOffset();
    int diff = end - start;
    
    assertEquals(TEST_TEXT_SPAN_START, textSpanToCheck.getStart().getOffset());
    assertEquals(TEST_TEXT_SPAN_END, textSpanToCheck.getEnd().getOffset());
    assertEquals(TEST_TEXT_SPAN_DIFF, diff);
  }
  
  /**
   * Checks whether all Named Queries of TextSpan are working correctly.
   * @throws Exception
   */
  @Test
  public void testNamedQueries() throws Exception {
    TextSpan testTextSpan = createTestTextSpan();
    
    em.persist(testTextSpan);
    startNewTransaction();
    
    // check Named Query finding all chapters
    TypedQuery<TextSpan> allQ = em.createNamedQuery("TextSpan.findAllTextSpans", TextSpan.class);
    List<TextSpan> allSpans = allQ.getResultList();
    
    assertTrue(allSpans.size() > 0);
    TextSpan readSpan = allSpans.get(0);
    checkData(readSpan);
    
    String id = readSpan.getId();
    
    // check Named Query finding text spans by id
    TypedQuery<TextSpan> idQ = em.createNamedQuery("TextSpan.findTextSpanById", TextSpan.class);
    idQ.setParameter("textSpanId", id);
    TextSpan idTextSpan = idQ.getSingleResult();
    
    checkData(idTextSpan);
  }

}