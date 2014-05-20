package org.soulsight.argouml.coauthor.evaluation;

import java.util.List;

import org.soulsight.argouml.coauthor.data.AuClass;

public interface Evaluation {

	public double evaluate(List<AuClass> predict, List<String> groundtruth);

}
