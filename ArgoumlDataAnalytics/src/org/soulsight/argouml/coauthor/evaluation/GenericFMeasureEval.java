package org.soulsight.argouml.coauthor.evaluation;

import java.util.List;

import org.soulsight.argouml.coauthor.data.AuClass;

public class GenericFMeasureEval  implements Evaluation {
	private int b;
	
	private Evaluation precisionEval;
	private Evaluation recallEval;
	
	public GenericFMeasureEval(Evaluation precisionEval, Evaluation recallEval, int b)
	{
		this.precisionEval = precisionEval;
		this.recallEval = recallEval;
		this.b = b;
	}
	
	public GenericFMeasureEval(int topK, int b)
	{
		this.b = b;
		recallEval = new TopKRecallEval(topK);
		precisionEval = new TopKPrecisionEval(topK);
	}
	
	@Override
	public double evaluate(List<AuClass> predict, List<String> groundtruth) {
		double recall = recallEval.evaluate(predict, groundtruth);
		double precision = precisionEval.evaluate(predict, groundtruth);
		double b2 = b * b;
		return (double)(1 + b2) / (b2 / recall + 1 / precision);
	}

}
