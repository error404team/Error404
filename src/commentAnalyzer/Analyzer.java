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

public class Analyzer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
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
			out.write(aLine);
			out.newLine();
		}
		
		in.close();
		out.close();
	
		InputStream modelIn = new FileInputStream("en-sent.bin");

		try {
		  SentenceModel model = new SentenceModel(modelIn);
		  SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			String sentences[] = sentenceDetector.sentDetect("  First sentence. Second sentence. ");
		
			InputStream modelPOS = null;
			modelPOS = new FileInputStream("en-pos-maxent.bin");
			  POSModel modelpos = new POSModel(modelPOS);
			  POSTaggerME tagger = new POSTaggerME(modelpos);
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
		
		
		DoccatModel model = null;

		InputStream dataIn = null;
		try {
		  dataIn = new FileInputStream("en-sentiment.train");
		  ObjectStream<String> lineStream =
				new PlainTextByLineStream(dataIn, "UTF-8");
		  ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
		  File dir1 =new File (".");
			String modelFile=dir1.getCanonicalPath()+File.separator+"modelFile.bin";
			
		  model = DocumentCategorizerME.train("en", sampleStream);
		  
		  OutputStream modelOut = null;
		  modelOut = new BufferedOutputStream(new FileOutputStream(modelFile));
		  model.serialize(modelOut);
		  modelOut.close();
		}
		catch (IOException e) {
		  // Failed to read or parse training data, training failed
		  e.printStackTrace();
		}
		finally {
		  if (dataIn != null) {
		    try {
		      dataIn.close();
		    }
		    catch (IOException e) {
		      // Not an issue, training already finished.
		      // The exception should be logged and investigated
		      // if part of a production system.
		      e.printStackTrace();
		    }
		  }
		}

		
		
		
		
	
	}

}
