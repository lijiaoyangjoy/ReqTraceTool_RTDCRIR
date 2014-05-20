package org.soulsight.argouml.coauthor.modularity;

public class ScoredClass implements Comparable<ScoredClass>
{
	private String className;
	private double score;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(ScoredClass o) {
		return - Double.compare(score, o.getScore());
	}
	
}
