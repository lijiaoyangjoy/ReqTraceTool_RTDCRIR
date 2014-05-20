package org.soulsight.argouml.coauthor.evaluation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.util.ClassUtils;

public class ThresholdPrecisionEval implements Evaluation {

	private double threshold = 0;

	public ThresholdPrecisionEval(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public double evaluate(List<AuClass> predict, List<String> groundtruth) {
		Set<String> evalSet = new HashSet<String>();
		for(String gtClass : groundtruth)
		{
			evalSet.add(ClassUtils.getShortName(gtClass)); //TODO remove '$' (refer to RecallEval.java)
		}
		
		double relevant = 0;
		int retrieved = 0;
		for(int i = 0; i < predict.size(); i++)
		{
			AuClass current = predict.get(i);
			if(Double.compare(current.getScore(), threshold) < 0)
			{
				continue;
			}
			
			retrieved++;
			if(evalSet.contains(ClassUtils.getShortName(current.getName())))
			{
				relevant += 1;
			}
		}
		return relevant / retrieved;
	}

}
