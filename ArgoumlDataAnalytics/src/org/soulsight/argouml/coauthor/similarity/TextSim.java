package org.soulsight.argouml.coauthor.similarity;

import org.soulsight.argouml.coauthor.DataRespo;
import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.argouml.coauthor.data.AuRequirement;

import weka.core.Instance;

public class TextSim implements Similarity{
	
	@Override
	public double getScore(AuRequirement requirement, AuClass clazz )
	{
		try
		{
			return cosine(DataRespo.getInstance(requirement.getName()), DataRespo.getInstance(clazz.getName()));
		}
		catch (Exception e) {
			System.out.println(e);
			System.out.println(requirement.getName());
			System.out.println(clazz.getName());
			return -1;
		}
	}
	
	public static double cosine(Instance first, Instance second) throws Exception
	{
		if(first.numAttributes() != second.numAttributes())
		{
			throw new Exception("Num Attribution not equal!");
		}
		
		double vectorFirstModulo = 0.00;
		double vectorSecondModulo = 0.00;
		double vectorProduct = 0.0;
		for(int i = 1; i < first.numAttributes(); i++)
		{
			vectorFirstModulo += first.value(i) * first.value(i);
			vectorSecondModulo += second.value(i) * second.value(i);
			vectorProduct += first.value(i) * second.value(i);
		}
		
		return vectorProduct / (Math.sqrt(vectorFirstModulo) * Math.sqrt(vectorSecondModulo));
	}
}
