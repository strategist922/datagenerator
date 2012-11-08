package distributions;

public class Zipf extends Distribution {

	private int s;
	
	public Zipf(int from, int to, int s){
		super(from,to);
		this.setType(DistType.ZIPF);
		this.setS(s);
		this.createKeyValues();
	}
	
	private double H(int N, int s){
		double sum=0.0;
		for(int i=1;i<N;i++)
			sum+=1.0/Math.pow(i, s);
		return sum;
	}
	
	@Override
	public double cdf(int k) {
		k=k+this.getLowBound();
		int N=this.getUpperBound()-this.getLowBound();
		return H(k,this.s)/H(N,this.s);
	}
	
}