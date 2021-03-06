package de.unistuttgart.vis.vita.analysis.modules;

import java.util.Arrays;

import javax.persistence.EntityManager;

import de.unistuttgart.vis.vita.analysis.ProgressListener;
import org.apache.lucene.search.IndexSearcher;

import de.unistuttgart.vis.vita.analysis.ModuleResultProvider;
import de.unistuttgart.vis.vita.analysis.annotations.AnalysisModule;
import de.unistuttgart.vis.vita.analysis.results.DocumentPersistenceContext;
import de.unistuttgart.vis.vita.analysis.results.GlobalWordCloudResult;
import de.unistuttgart.vis.vita.model.Model;
import de.unistuttgart.vis.vita.model.document.Document;
import de.unistuttgart.vis.vita.model.progress.AnalysisProgress;
import de.unistuttgart.vis.vita.model.progress.FeatureProgress;
import de.unistuttgart.vis.vita.model.wordcloud.WordCloud;

/**
 * The feature module that stores document outline and document metadata
 *
 * "Feature module" means that it interacts with the data base. It stores the progress as well as
 * the result.
 *
 * This module depends on {@link IndexSearcher} which guarantees that the document text is persisted
 * in lucene.
 */
@AnalysisModule(dependencies = {DocumentPersistenceContext.class, Model.class,
                                GlobalWordCloudResult.class}, weight = 0.1)
public class GlobalWordCloudFeatureModule extends AbstractFeatureModule<GlobalWordCloudFeatureModule> {

  @Override
  public GlobalWordCloudFeatureModule storeResults(ModuleResultProvider result, Document document,
      EntityManager em, ProgressListener progressListener)
      throws Exception {

    WordCloud wordCloud = result.getResultFor(GlobalWordCloudResult.class).getGlobalWordCloud();
    em.persist(wordCloud);
    document.getContent().setGlobalWordCloud(wordCloud);

    return this;
  }

  @Override
  protected Iterable<FeatureProgress> getProgresses(AnalysisProgress progress) {
    return Arrays.asList(progress.getWordCloudProgress());
  }

}
