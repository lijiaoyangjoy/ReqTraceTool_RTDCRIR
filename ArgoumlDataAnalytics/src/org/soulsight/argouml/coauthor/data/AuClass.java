package org.soulsight.argouml.coauthor.data;

public class AuClass implements Comparable<AuClass>{
	private String name;
	private double score;
	
	public AuClass(String name)
	{
		this.name = name;
		this.score = 0.0;
	}
	
	public AuClass(String name, double score)
	{
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(AuClass o)
	{
		return - Double.compare(score, o.getScore());
	}
	
}
