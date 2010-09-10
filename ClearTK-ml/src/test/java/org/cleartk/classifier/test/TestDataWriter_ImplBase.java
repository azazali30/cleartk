 /** 
 * Copyright (c) 2007-2010, Regents of the University of Colorado 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of the University of Colorado at Boulder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
*/
package org.cleartk.classifier.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.cleartk.CleartkException;
import org.cleartk.classifier.encoder.features.NameNumber;
import org.cleartk.classifier.jar.JarDataWriter;


/**
 * <br>Copyright (c) 2007-2010, Regents of the University of Colorado 
 * <br>All rights reserved.

 * <p>
 * 
 * @author Philip Ogren
 * @author Steven Bethard
 */
public abstract class TestDataWriter_ImplBase<OUTCOME_TYPE> extends JarDataWriter<OUTCOME_TYPE, String, List<NameNumber>> {

	public static final String TRAINING_DATA_FILE_NAME ="training-data.test"; 

	public TestDataWriter_ImplBase(File outputDirectory) throws IOException {
		super(outputDirectory);
		// initialize output writer and Classifier class
		this.trainingDataWriter = this.getPrintWriter(TRAINING_DATA_FILE_NAME);
	}

	protected PrintWriter trainingDataWriter;

	@Override
	public void writeEncoded(List<NameNumber> features, String outcome) throws CleartkException{
		if(outcome == null) {
			throw new CleartkException("all consumed instances must have an outcome.  outcome="+outcome);
		}
		this.trainingDataWriter.print(outcome);

		if (features.size() == 0) {
			trainingDataWriter.print(" null=0");
		}

		// write each of the string features, encoded, into the training data
		for (NameNumber nameNumber : features) {
			this.trainingDataWriter.print(' ');
			if(nameNumber.number.doubleValue() == 1.0)
				trainingDataWriter.print(nameNumber.name);
			else
				trainingDataWriter.print(nameNumber.name + "=" + nameNumber.number);

		}

		// complete the feature line
		this.trainingDataWriter.println();
	}


}
