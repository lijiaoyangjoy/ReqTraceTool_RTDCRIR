package org.soulsight.argouml.coauthor.similarity;

import java.io.IOException;

import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.argouml.coauthor.data.AuRequirement;

public class HybridSim implements Similarity{

	private double alpha = 0;

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	private static TextSim textSim = new TextSim();
	private static CoauthorSim coauthorSim = new CoauthorSim();
	public static void init(String clusterFilename) throws IOException
	{
		CoauthorSim.init(clusterFilename);
	}
	
	@Override
	public double getScore(AuRequirement requirement, AuClass clazz) {
		return alpha * textSim.getScore(requirement, clazz) 
				+ (1 - alpha) * coauthorSim.getScore(requirement, clazz);
	}


}
