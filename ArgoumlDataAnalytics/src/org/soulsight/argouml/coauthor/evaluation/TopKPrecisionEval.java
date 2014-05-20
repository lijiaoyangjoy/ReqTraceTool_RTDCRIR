package org.soulsight.argouml.coauthor.evaluation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.soulsight.argouml.coauthor.data.AuClass;

public class TopKPrecisionEval implements Evaluation {

	private int topK = 0;

	public TopKPrecisionEval(int topK) {
		SetTopK(topK);
	}

	public void SetTopK(int topK) {
		this.topK = topK;
	}

	@Override
	public double evaluate(List<AuClass> predict, List<String> groundtruth) {
		Set<String> evalSet = new HashSet<String>(groundtruth);
		
		int totalCount = Math.min(topK, predict.size());
		
		double relevant = 0;
		List<String> classNamesSet = new ArrayList<String>();
		for (int i = 0; i < totalCount; i++) {
			String name = predict.get(i).getName();
			if (name.contains("$")) {
				name = name.substring(0, name.indexOf("$"));
			}
			name = name.substring(name.lastIndexOf(".") + 1, name.length());
//			System.out.println(name);

			int flag = 1;
			for (int k = 0; k < classNamesSet.size(); k++) {
				if (classNamesSet.get(k).equals(name)) {
					flag = 0;
//					System.out.println("+++++++++classNamesSet.contains(name):"
//							+ name);
					break;
				}
			}
			if (flag == 1) {
				classNamesSet.add(name);
				// System.out.println("evaluate-->predict.get(i).getName():"+name);
				for (String s : evalSet) {
					s = s.substring(s.lastIndexOf(".") + 1, s.length());
					if (s.equals(name)) {
						System.out.println("Find : " + name);
						relevant += 1;
						break;
					}
				}
			}

		}
		return relevant / totalCount;
	}

}
