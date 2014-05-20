package org.soulsight.argouml.coauthor.cluster;

import java.io.IOException;

public interface ClassCluster 
{
	public void cluster(String inputFilename, String outputFilename) throws IOException;
	public void batchCluster(String srcDir, String desDir) throws IOException;
}
