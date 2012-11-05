package distributions;

import java.util.Random;

/**
 * @author Giannis Giannakopoulos  
 **/

public abstract class Distribution {
	private DistType 	type;
	private int 		low,upper;
	private Random 		random;
	private int keys[];
	private double values[];
	
	public Distribution(int from, int to){
		this.random=new Random();
		this.setLowBound(from);
		this.setUpperBound(to);
		//this.createKeyValues();
	}

	public void setType(DistType type){
		this.type=type;
	}
	
	public DistType getType(){
		return this.type;
	}
	
	public void setLowBound(int value){
		this.low=value;
	}
	
	public void setUpperBound(int value){
		this.upper=value;
	}
	
	public int getLowBound(){
		return this.low;
	}
	
	public int getUpperBound(){
		return this.upper;
	}
	
	private double getRandom(){
		return this.random.nextDouble();
	}
	
	public void createKeyValues(){
		this.keys=new int[this.getUpperBound()-this.getLowBound()+1];
		this.values=new double[this.getUpperBound()-this.getLowBound()+1];
		for(int i=this.getLowBound();i<=this.getUpperBound();i++){
			keys[i-this.getLowBound()]=i;
			values[i-this.getLowBound()]=this.cdf(i);
		}
	}

	private int binarySearch(double value){
		int from=0, to=this.values.length-1, med=0;
		
		while(med!=(from+to)/2){
			med=(from+to)/2;
			if(value>this.values[med])
				from=med+1;
			else if(value<this.values[med])
				to=med;
			else 
				break;
		}
		return med;
	}
	
	private int inverseCdf(double possibility){
		int index=binarySearch(possibility);
	/*	int index=0;
		for(int i=0;i<this.values.length;i++){
			if(possibility<this.values[i]){
				index=i;
				break;
			}
		}*/
		if(index==0)
			index=1;
		
		if(this.values[index]-possibility<possibility-this.values[index-1])
			return this.keys[index];
		else
			return this.keys[index-1];
	}
	
	/**
	 * This method returns the inverse cdf value
	 * @param possibility is the possibility given
	 * @return the value for the defined possibility
	 * */
	public int getValue(){
		return this.inverseCdf(this.getRandom());
	}
	
	/**
	 * This is an overridable method. The class that extends Distribution class must define
	 * a valid cdf function.
	 * @param x the value that must be between the lower and upper bound defined
	 * @return 
	 * */
	abstract public double cdf(int x);
	
	public String toString(){
		String buffer = new String();
		for(int i=0;i<this.keys.length;i++)
			buffer+=this.keys[i]+"\t"+this.values[i]+"\n";
		return buffer;
	}
}