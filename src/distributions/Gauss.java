package distributions;

import java.lang.Math;

public class Gauss extends Distribution {

	private static double coef[]={			//used for calculating error function by using taylor's series approach
				0.278393,
				0.230389,
				0.000972,
				0.078108
		};
	private double mi, sigma;
	
	public Gauss(){
		super();
	};
	public Gauss(int from, int to){
		super(from,to);
		this.setType(DistType.GAUSS);
		this.createKeyValues();
	}
	
	private double erf(double x){
		boolean inverse=false;
		if(x<=0){
			inverse=true;
			x=-x;
		}
		double sum=1.0;
		for(int i=0;i<Gauss.coef.length;i++)
			sum+=Gauss.coef[i]*Math.pow(x,i+1);
		if(!inverse)
			return 1-(1.0/Math.pow(sum, 4));
		else
			return -(1-(1.0/Math.pow(sum, 4)));
	}
	
	
	@Override
	public double cdf(int x) {
		this.mi=(this.getUpperBound()-this.getLowBound())/2.0+this.getLowBound();
		this.sigma=(this.getUpperBound()-this.getLowBound())/6.0;
		double temp=(x-this.mi)/(Math.sqrt(2*Math.pow(this.sigma, 2)));
		return 0.5*(1+this.erf(temp));
	}
}
