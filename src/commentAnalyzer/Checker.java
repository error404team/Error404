package commentAnalyzer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


public class Checker {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		
		InputStream modelIn = new FileInputStream("cat-model.bin");
          
		try {
			DoccatModel m = new DoccatModel(modelIn);
			//String inputText = "Glass is not transparent ";
			File dir =new File (".");
			String source=dir.getCanonicalPath()+File.separator+"code.txt";
			String output=dir.getCanonicalPath()+File.separator+"Output.txt";
			
			File fin= new File(source);
			FileInputStream fis= new FileInputStream(fin);
			BufferedReader in = new BufferedReader( new InputStreamReader(fis));
			FileWriter fstream = new FileWriter(output,true);
			BufferedWriter out= new BufferedWriter(fstream);
			
			String aLine=null;
			while((aLine = in.readLine())!=null) {
				System.out.println(aLine);
				DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);
				double[] outcomes = myCategorizer.categorize(aLine);
				
				String category = myCategorizer.getBestCategory(outcomes);
				
				//for(int i=0;i<outcomes.length;i++){
					
					//System.out.println(outcomes[i]);
					
				//}
				System.out.println("Analysis Result *** "+category);

				
				out.write(category);
				out.newLine();
			}
			
			in.close();
			out.close();
					
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}
		
		
	
		}

		
		
		
		
	
	

}
