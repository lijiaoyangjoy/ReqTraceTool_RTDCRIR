package org.soulsight.argouml.coauthor.evaluation;

import java.util.List;

import org.soulsight.argouml.coauthor.data.AuClass;

public class FMeasureEval implements Evaluation {
	private double threshold;
	private int b;
	
	private Evaluation recallEval;
	private Evaluation precisionEval;
	
	public FMeasureEval(double threshold, int b)
	{
		this.threshold = threshold;
		this.b = b;
		initEval();
	}
	
	private void initEval()
	{
		recallEval = new ThresholdRecallEval(threshold);
		precisionEval = new ThresholdPrecisionEval(threshold);
	}
	
	@Override
	public double evaluate(List<AuClass> predict, List<String> groundtruth) {
		double recall = recallEval.evaluate(predict, groundtruth);
		double precision = precisionEval.evaluate(predict, groundtruth);
		double b2 = b * b;
		return (double)(1 + b2) / (b2 / recall + 1 / precision);
	}

	public static void main(String[] args) {
		
	}

}
