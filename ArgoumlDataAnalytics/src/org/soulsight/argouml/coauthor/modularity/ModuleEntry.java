package org.soulsight.argouml.coauthor.modularity;

import java.io.IOException;

public class ModuleEntry {

	public static void main(String[] args) throws IOException {
		ModuleMap map = new ModuleMap("./data/module.txt");
		//DeltaScore ds = new DeltaScore(map);
		DeltaScore ds = new DeltaScore(map, 0.1);
		ds.moduleDir("./data/IR-result/", "./data/IR-result-Moduled/");
	}

}
