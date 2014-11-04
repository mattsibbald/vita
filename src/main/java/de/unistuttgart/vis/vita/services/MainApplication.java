package de.unistuttgart.vis.vita.services;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import de.unistuttgart.vis.vita.analysis.AnalysisController;
import de.unistuttgart.vis.vita.model.Model;

public class MainApplication extends ResourceConfig {
  public MainApplication() {
    super(MultiPartFeature.class, DocumentsService.class);
    packages(true, "de.unistuttgart.vis.vita.services");
    register(new MainApplicationBinder());
  }
  
  private static class MainApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
      bind(Model.class).to(Model.class);
      bind(AnalysisController.class).to(AnalysisController.class);
    }
  }
}