package de.unistuttgart.vis.vita.importer.epub.input;

import java.io.IOException;

import de.unistuttgart.vis.vita.importer.epub.extractors.AbstractEpubExtractor;
import de.unistuttgart.vis.vita.importer.epub.extractors.Epub2Extractor;
import de.unistuttgart.vis.vita.importer.epub.extractors.Epub3Extractor;
import de.unistuttgart.vis.vita.importer.epub.util.NoExtractorFoundException;
import nl.siegmann.epublib.domain.Book;

/**
 * All possible versions of epub-formats. The class also knows the Extractor which fits to a version.
 */
public enum EpubVersion {
  UNKNOWN, STANDARD2, STANDARD3;

  /**
   * Gets the correct Extractor for a given version.
   * 
   * @param version The version for which a Extractor should be returned.
   * @param book The analyzed book to which the version belongs.
   * @return The Extractor for the book, which is able to extract data from books of the given version.
   * @throws IOException If Error occured while extracting the file. Extraction is aborted.
   * @throws NoExtractorFoundException No Extractor fits to the given version.
   */
  public static AbstractEpubExtractor getExtractorForVersion(EpubVersion version, Book book) throws IOException, NoExtractorFoundException{
    AbstractEpubExtractor extractor;
    switch(version){
      case STANDARD2:
        extractor = new Epub2Extractor(book);
        break;
      case STANDARD3:
        extractor = new Epub3Extractor(book);
        break;
      default:
        throw new NoExtractorFoundException();
    }
    return extractor;
  }
  
}
