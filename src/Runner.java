import generator.Generator;
 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import distributions.DistType;
/*Hrll*/

public class Runner {

	private static String[] args;

	public static String getValueFromArgs(String value){
		for(int i=0;i<Runner.args.length;i++){
			if(value.equals(args[i]))
				return args[i+1];
		}
		return "";
	}
	
	public static String getValueFromFile(String filename, String value) throws IOException{
		BufferedReader file = new BufferedReader(new FileReader(filename));
		while(file.ready()){
			String buffer[]=file.readLine().split("\t");
			if(buffer[0].equals(value)){
				file.close();
				return buffer[buffer.length-1];
			}
		}
		file.close();
		return "";
	}
	
	public static void main(String[] args) throws IOException {
		Runner.args=args;
		String attributes, lines, files, distribution, s, rangesfile;
		if(args.length==0){
			System.out.println("Configuration file or command-line arguments are needed.");
			System.out.println("Arguments");
			System.out.println("\t-file\t\t<conf file>");
			System.out.println("\t-lines\t\t<lines/file>");
			System.out.println("\t-files\t\t<# of files>");
			System.out.println("\t-attributes\t<# of attributes>");
			System.out.println("\t-distribution\t<UNIFORM, GAUSS, ZIPF>");
			System.out.println("\t-rangefile\t<file containing ranges of attributes>");
			System.exit(1);
		}
		
		if(!getValueFromArgs("-file").equals("")){
			String conf=getValueFromArgs("-file");
			lines=getValueFromFile(conf,"lines");
			attributes=getValueFromFile(conf,"attributes");
			files=getValueFromFile(conf,"files");
			distribution=getValueFromFile(conf,"distribution");
			rangesfile=getValueFromFile(conf,"rangefile");
			s=getValueFromFile(conf,"s");
		}
		else{
			lines=getValueFromArgs("-lines");
			attributes=getValueFromArgs("-attributes");
			files=getValueFromArgs("-files");
			distribution=getValueFromArgs("-distribution");
			s=getValueFromArgs("-s");
			rangesfile=getValueFromArgs("-rangefile");
		}

		Generator gen = new Generator();
		
		gen.setNumberOfAttributes(new Integer(attributes));
		gen.setNumberOfLines(new Integer(lines));
		gen.setNumberOfFiles(new Integer(files));
		gen.setRangesFile(rangesfile);
		
		if(distribution.equals("GAUSS"))
			gen.setTypeOfDistribution(DistType.GAUSS);
		else if(distribution.equals("UNIFORM"))
			gen.setTypeOfDistribution(DistType.UNIFORM);
		else if(distribution.equals("ZIPF")){
			gen.setTypeOfDistribution(DistType.ZIPF);
			gen.setS(new Integer(s));
		}
		else{
			System.err.println("Unknown distribution");
			System.exit(1);
		}
		
		gen.createDistributions();
		System.out.println("Generator created");
		gen.writeFiles("data");
	}
}
