/* 
 * This file was copied from edu.umn.biomedicus.evaluation.EngineFactory in the Biomedicus project (see http://biomedicus.googlecode.com).
 * The original file is made available under the ASL 2.0 with the following text:

 Copyright 2010 University of Minnesota  
 All rights reserved. 

 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 

 http://www.apache.org/licenses/LICENSE-2.0 

 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS, 
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 See the License for the specific language governing permissions and 
 limitations under the License.
 */

package org.cleartk.eval.provider;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.classifier.ClassifierFactory;
import org.cleartk.classifier.CleartkAnnotator;
import org.cleartk.classifier.DataWriterFactory;

/**
 * 
 * @author Philip Ogren
 * 
 */

public interface CleartkPipelineProvider {

  /**
   * This method creates a pipeline of analysis engines that will create a training data for the
   * learner being used. This will generally include a {@link CleartkAnnotator} that is initialized
   * with a {@link DataWriterFactory}. However, a number of other analysis engines may also be
   * required depending on what data from the gold-standard corpus is provided and what needs to be
   * generated. This aggregate analysis engine should generally operate on the
   * {@link ViewNames#GOLD_VIEW}.
   * 
   * @param name
   *          will generally be a value such as 'fold-01' or 'holdout'. It provides a name for this
   *          method to provide to the pipeline it returns that serves as a key for that pipeline.
   *          One expected use of the name is for naming a subdirectory that a data writer could use
   *          to write training data to.
   * 
   * @return
   * @throws ResourceInitializationException
   */
  public List<AnalysisEngine> getTrainingPipeline(String name)
      throws ResourceInitializationException;

  /**
   * This method trains a model(s) for a given training pipeline (identified by name) using whatever
   * training data was generated as a result of running the training pipeline. If your task involves
   * only a single {@link CleartkAnnotator} which runs off of a single classification model, then
   * this method will generally consist of a single line of code. However, it is not uncommmon for a
   * task to require many models. In such cases, this method needs to know how to train each model
   * that needs to be trained.
   * 
   * @param name
   *          will generally be a value such as 'fold-01' or 'holdout'. It provides a name for this
   *          method for the pipeline whose data we are training a model(s) for. One expected use of
   *          the name is to identify a subdirectory where training data is located.
   * @param trainingArguments
   *          arguments that are passed on to the classifiers model trainer.
   * @throws Exception
   */
  public void train(String name, String... trainingArguments) throws Exception;

  /**
   * This method creates a pipeline of analysis engines that will run the task using the model(s)
   * generated by {@link #train(String, String...)}. This will generally include a
   * {@link CleartkAnnotator} that is initialized with a {@link ClassifierFactory}. However, a
   * number of other analysis engines may also be required depending on what data from the
   * gold-standard corpus is provided and what needs to be generated. This pipeline of analysis
   * engines should generally operate on the {@link ViewNames#SYSTEM_VIEW}.
   * 
   * @param name
   * @return
   * @throws ResourceInitializationException
   */

  public List<AnalysisEngine> getClassifyingPipeline(String name)
      throws ResourceInitializationException;

}
