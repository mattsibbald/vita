package de.unistuttgart.vis.vita.importer.epub.extractors;

import java.io.IOException;
import java.util.List;

import de.unistuttgart.vis.vita.importer.util.ChapterPosition;
import de.unistuttgart.vis.vita.importer.util.Line;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;

/**
 * The abstract Epub Extractor defines the input and output for all concrete EpubExtractors and
 * defines some auxiliary methods, which can be helpful to extract epubs in general.
 */
public abstract class AbstractEpubExtractor {
  protected List<Resource> resources;
  protected Resource tocResource;

  /**
   * Initializes an Epub Extractor for a given book.
   * 
   * @param book The book to extract data from.
   * @throws IOException If Error occured while extracting the file. Extraction is aborted.
   */
  public AbstractEpubExtractor(Book book) throws IOException {
    super();

    this.tocResource = book.getNcxResource();
    this.resources = book.getContents();
  }

  /**
   * For each part of the book a list of lines will returned. The element of the list is the first
   * part, ... the last element is the last part of the book.
   * 
   * @return The extracted parts and text of the parts.
   * @throws IOException 
   */
  public abstract List<List<Line>> getPartList() throws IOException;

  /**
   * For a part a chapter position describes the position of the chapters. The n-th element of the
   * list, belongs to the n-th part of the book.
   * 
   * @return The positions of the chapters in a part.
   * @throws IOException 
   */
  public abstract List<ChapterPosition> getChapterPositionList() throws IOException;

  /**
   * The list contains the titles of the parts. The n-th element of the list belongs to the n-th
   * part of the book.
   * 
   * @return The titles of the parts.
   * @throws IOException 
   */
  public abstract List<String> getTitleList() throws IOException;
}
