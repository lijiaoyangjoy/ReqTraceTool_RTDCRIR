package org.soulsight.argouml.coauthor.similarity;

import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.argouml.coauthor.data.AuRequirement;

public interface Similarity {
	public double getScore(AuRequirement requirement, AuClass clazz);
}
