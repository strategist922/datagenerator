package distributions;

public class Uniform extends Distribution {

	public Uniform(int from, int to){
		super(from,to);
		this.setType(DistType.UNIFORM);
		this.createKeyValues();
	}
	
	@Override
	public double cdf(int x){
		if(x<this.getLowBound())
			return 0.0;
		else if(x>this.getUpperBound())
			return 1.0;
		else
			return 1.0*(x-this.getLowBound()+1)/(this.getUpperBound()-this.getLowBound()+1);
	}
}
