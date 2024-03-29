package de.unistuttgart.vis.vita.importer.epub.extractors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Extracts various traits regarding Epub2
 * 
 *
 */
public class Epub2TraitsExtractor {
  private final List<Resource> resources;
  private final Resource tocResource;
  private ContentBuilder contentBuilder = new ContentBuilder();
  private PartsAndChaptersReviser reviser = new PartsAndChaptersReviser();

  public Epub2TraitsExtractor(List<Resource> resources, Resource tocResource) {
    this.resources = resources;
    this.tocResource = tocResource;
  }

  /**
   * Extracts the lines of a chapter and transforms them into a List<Epubline>
   * 
   * @param currentElement
   * @param document
   * @param currentResource
   * @param ids
   * @return
   * @throws IOException
   */
  public List<Epubline> extractChapterEpublines(Element currentElement, Document document,
      Resource currentResource, List<String> ids) throws IOException {

    List<Epubline> chapter = new ArrayList<Epubline>();
    List<Element> editedElements = new ArrayList<Element>();

    int start = getSubheadingPosition(currentElement, document, chapter, ids);

    // iterate through the current resource
    for (int i = document.getAllElements().indexOf(currentElement) + start; i < document
        .getAllElements().size(); i++) {

      Element innerElement = document.getAllElements().get(i);
      if (!ids.contains(innerElement.id())&& !innerElement.text().matches(Constants.PART)) {
        addElementTexts(chapter, editedElements, innerElement);
      } else {
        return chapter;
      }
    }

    // iterate through the remaining resources
    for (int j = resources.indexOf(currentResource) + 1; j < resources.size(); j++) {
      Document nextDocument =
          Jsoup.parse(contentBuilder.getStringFromInputStream(resources.get(j).getInputStream()));
      for (int k = 0; k < nextDocument.getAllElements().size(); k++) {
        
        Element innerElement = nextDocument.getAllElements().get(k);
        if (!ids.contains(innerElement.id()) && !innerElement.text().matches(Constants.PART)) {
          addElementTexts(chapter, editedElements, innerElement);
        } else {
          return chapter;
        }
      }
    }
    return chapter;
  }

  private void addElementTexts(List<Epubline> chapter, List<Element> editedElements,
      Element innerElement) {
    if (!innerElement.text().isEmpty()
        && !innerElement.attr(Constants.CLASS).matches(Constants.TOC + "|" + Constants.FOOT)
        && !reviser.elementEdited(editedElements, innerElement)) {

      if (innerElement.tagName().equals(Constants.PARAGRAPH_TAGNAME)) {
        reviser.addText(chapter, innerElement, reviser.existsSpan(innerElement), Constants.TEXT);

      } else if (innerElement.tagName().matches(Constants.DIV)
          && innerElement.attr(Constants.CLASS).matches(Constants.PGMONOSPACED)) {
        reviser.addDivTexts(chapter, innerElement, editedElements, Constants.TEXT);
      }
    }
  }

  /**
   * Returns the correct Epubline regarding the HEADING
   * 
   * @param chapter
   * @return
   */
  public Epubline getHeading(List<Epubline> chapter) {
    for (Epubline epubline : chapter) {
      if (epubline.getMode().equals(Constants.HEADING)) {
        return epubline;
      }
    }
    return null;
  }

  /**
   * Returns the correct Epubline regarding the TEXTSTART
   * 
   * @param chapter
   * @return
   */
  public Epubline getTextStart(List<Epubline> chapter) {
    for (Epubline epubline : chapter) {
      if (epubline.getMode().equals(Constants.TEXTSTART)) {
        return epubline;
      }
    }
    return null;
  }

  /**
   * Returns the correct Epubline regarding the TEXTEND
   * 
   * @param chapter
   * @return
   */
  public Epubline getTextEnd(List<Epubline> chapter) {
    for (Epubline epubline : chapter) {
      if (epubline.getMode().equals(Constants.TEXTEND)) {
        return epubline;
      }
    }
    return null;
  }

  /**
   * Returns the correct position of the Subheading and adds the text to the chapter
   * 
   * @param currentElement
   * @param document
   * @param chapter
   * @param ids
   * @return
   */
  private int getSubheadingPosition(Element currentElement, Document document,
      List<Epubline> chapter, List<String> ids) {

    int start = 1;
    org.jsoup.select.Elements allElements = document.getAllElements();
    if (!currentElement.equals(allElements.get(allElements.size() - 1))
        && (ids.contains(allElements.get(allElements.indexOf(currentElement) + 1).id())
            || allElements.get(allElements.indexOf(currentElement) + 1).attr(Constants.CLASS)
                .matches(Constants.CHAPTER_TITLE) || allElements
            .get(allElements.indexOf(currentElement) + 1).attr(Constants.ID).toLowerCase()
            .contains(Constants.CHAPTER))) {

      chapter.add(new Epubline(Constants.SUBHEADING, document.getAllElements()
          .get(document.getAllElements().indexOf(currentElement) + 1).text(), document
          .getAllElements().get(document.getAllElements().indexOf(currentElement) + 1).id()));
      start = 2;
    }

    return start;
  }

  /**
   * Returns a List<List<List<Epubline>>> which contains parts of a book
   * 
   * @param chapters
   * @return
   * @throws IOException
   */
  public List<List<List<Epubline>>> getPartsEpublines(List<List<Epubline>> chapters)
      throws IOException {
    Epub2IdsAndTitlesExtractor epub2IdsExtracor =
        new Epub2IdsAndTitlesExtractor(resources, tocResource);
    List<List<String>> partsWithChaptersIds = epub2IdsExtracor.getPartsChaptersIds();
    List<List<List<Epubline>>> parts = new ArrayList<List<List<Epubline>>>();

    for (List<String> part : partsWithChaptersIds) {
      List<List<Epubline>> partChapters = getChaptersEpublines(part, chapters);
      parts.add(partChapters);
    }
    return parts;
  }
  
  /**
   * Returns a List<List<Epubline>> which contains all chapters of one part.
   * 
   * @param part
   * @param chapters
   * @return
   */
  private List<List<Epubline>> getChaptersEpublines(List<String> part, List<List<Epubline>> chapters){
    List<List<Epubline>> partChapters = new ArrayList<List<Epubline>>();
    for (int i = 0; i < part.size(); i++) {
      for (List<Epubline> chapter : chapters) {
        if (part.get(i).matches(chapter.get(0).getId())) {
          partChapters.add(chapter);
        }
      }
    }
    return partChapters;
  }
  
}
