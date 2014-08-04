package de.unistuttgart.vis.vita.model.progress;

/**
 * Represents the progress of a document analysis, holding the progresses of the separate software
 * features.
 */
public class AnalysisProgress {

  // attributes
  private FeatureProgress graphViewProgress;
  private FeatureProgress wordCloudProgress;
  private FeatureProgress placesProgress;
  private FeatureProgress personsProgress;
  private FeatureProgress fingerPrintProgress;
  private FeatureProgress textProgress;

  /**
   * Creates a new instance of AnalysisProgress, setting all FeatureProgresses to Zero and not
   * ready.
   */
  public AnalysisProgress() {
    this.graphViewProgress = new FeatureProgress();
    this.wordCloudProgress = new FeatureProgress();
    this.placesProgress = new FeatureProgress();
    this.personsProgress = new FeatureProgress();
    this.fingerPrintProgress = new FeatureProgress();
    this.textProgress = new FeatureProgress();
  }

  /**
   * @return the progress of the GraphView feature
   */
  public FeatureProgress getGraphViewProgress() {
    return graphViewProgress;
  }

  /**
   * Sets the progress for the GraphView feature.
   *
   * @param newGraphViewProgress - the new progress of the GraphView feature
   */
  public void setGraphViewProgress(FeatureProgress newGraphViewProgress) {
    this.graphViewProgress = newGraphViewProgress;
  }

  /**
   * @return the progress of the WordCloud feature
   */
  public FeatureProgress getWordCloudProgress() {
    return wordCloudProgress;
  }

  /**
   * Sets the progress of the WordCloud feature.
   *
   * @param newWordCloudProgress - the new progress of the WordCloud feature
   */
  public void setWordCloudProgress(FeatureProgress newWordCloudProgress) {
    this.wordCloudProgress = newWordCloudProgress;
  }

  /**
   * @return the progress of the Places feature
   */
  public FeatureProgress getPlacesProgress() {
    return placesProgress;
  }

  /**
   * Sets the progress for the Places feature.
   *
   * @param newPlacesProgress - the new progress for the Places feature
   */
  public void setPlacesProgress(FeatureProgress newPlacesProgress) {
    this.placesProgress = newPlacesProgress;
  }

  /**
   * @return the progress of the Persons feature
   */
  public FeatureProgress getPersonsProgress() {
    return personsProgress;
  }

  /**
   * Sets the progress of the Persons feature.
   *
   * @param newPersonsProgress - the new progress for the Persons feature
   */
  public void setPersonsProgress(FeatureProgress newPersonsProgress) {
    this.personsProgress = newPersonsProgress;
  }

  /**
   * @return the progress of the FingerPrint feature
   */
  public FeatureProgress getFingerPrintProgress() {
    return fingerPrintProgress;
  }

  /**
   * Sets the progress of the FingerPrint feature.
   *
   * @param newFingerPrintProgress - the new progress for the FingerPrint feature
   */
  public void setFingerPrintProgress(FeatureProgress newFingerPrintProgress) {
    this.fingerPrintProgress = newFingerPrintProgress;
  }

  /**
   * @return the progress of the text feature
   */
  public FeatureProgress getTextProgress() {
    return textProgress;
  }

  /**
   * Sets the progress of the text feature.
   *
   * @param newTextProgress - the new progress for the text feature
   */
  public void setTextProgress(FeatureProgress newTextProgress) {
    this.textProgress = newTextProgress;
  }

}