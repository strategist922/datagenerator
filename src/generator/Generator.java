package generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import distributions.DistType;
import distributions.Distribution;
import distributions.Gauss;
import distributions.Uniform;
import distributions.Zipf;


public class Generator {
	private int lines;
	private int files;
	private DistType type;
	private Distribution[] dist;
	private int attributes;
	private int s;
	private String[][] bounds;

	public void setNumberOfAttributes(int attributes){
		this.attributes=attributes;
	}
	
	public void setNumberOfLines(int lines){
		this.lines=lines;
	}
	
	public void setNumberOfFiles(int files){
		this.files=files;
	}
	
	public void setTypeOfDistribution(DistType type){
		this.type=type;
	}
	
	public void setS(int s){
		this.s=s;
	}
	
	public void setRangesFile(String file) throws IOException{
		BufferedReader filereader = new BufferedReader(new FileReader(file));
		bounds = new String[this.attributes][];
		for(int i=0;i<this.attributes;i++){
			this.bounds[i]=filereader.readLine().split(",");
		}
		filereader.close();
	}
	
	public void createDistributions(){
		if(type==DistType.GAUSS){
			this.dist=new Gauss[this.attributes];
			for(int i=0;i<this.attributes;i++)
				this.dist[i]=new Gauss(new Integer(this.bounds[i][0]),new Integer(this.bounds[i][1]));
		}
		else if(type==DistType.UNIFORM){
			this.dist=new Uniform[this.attributes];
			for(int i=0;i<this.attributes;i++)
				this.dist[i]=new Uniform(new Integer(this.bounds[i][0]),new Integer(this.bounds[i][1]));
		}
		else if(type==DistType.ZIPF){
			this.dist=new Zipf[this.attributes];
			for(int i=0;i<this.attributes;i++)
				this.dist[i]=new Zipf(new Integer(this.bounds[i][0]),new Integer(this.bounds[i][1]),new Integer(s));
		}
	}
	
	public String printLine(){
		String buffer= new String();
		for(int i=0;i<this.attributes-1;i++)
			buffer+=dist[i].getValue()+",";
		buffer+=dist[this.attributes-1].getValue();
		return buffer;
	}
	
	public void writeFile(String filename) throws IOException{
		String buffer="";
		for(int i=0;i<this.lines-1;i++){
			buffer+=printLine()+"\n";
		}
		buffer+=printLine()+"\n";
		BufferedWriter write = new BufferedWriter(new FileWriter(filename));
		write.write(buffer);
		write.close();
	}
	
	public void writeFiles(String prefix) throws IOException{
		for(int i=0;i<this.files;i++){
			this.writeFile(prefix+i+".txt");
			System.out.println("File "+prefix+i+".txt"+" is done!");
		}
	}
}
