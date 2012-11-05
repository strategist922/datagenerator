package generator;

import distributions.DistType;


public class Generator {
	private int attributes;
	private int lines;
	private DistType type;

	public void setNumberOfAttributes(int att){
		this.attributes=att;
	}
	
	public void setNumberOfLines(int lines){
		this.lines=lines;
	}
	
	public void setTypeOfDistribution(DistType type){
		this.type=type;
	}

}
