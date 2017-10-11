package fingerprint.demo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

/**
 * Hello world!
 *
 */



public class App  {
	
	public static void main(String[] args) {
		try {
			//byte[] probeImage = Files.readAllBytes(Paths.get("resources/d0.jpg"));
			//byte[] candidateImage = Files.readAllBytes(Paths.get("resources/d1.jpg"));
			
			byte[] fingerDataScanned=new byte[1];
			byte[] fingerDataStored=new byte[1];
			App fingerPrintUtillity=new App();
			File dir = new File("resources");
			  File[] directoryListing = dir.listFiles();
			  byte [] currentFile=Files.readAllBytes(Paths.get("resources/d0.jpg"));
			  
			  
		        /*
		         * 2. How to convert byte array back to an image file?
		         */
		 
		        ByteArrayInputStream bis = new ByteArrayInputStream(currentFile);
		        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
		 
		        //ImageIO is a class containing static methods for locating ImageReaders
		        //and ImageWriters, and performing simple encoding and decoding. 
		 
		        ImageReader reader = (ImageReader) readers.next();
		        Object source = bis; 
		        ImageInputStream iis = ImageIO.createImageInputStream(source); 
		        reader.setInput(iis, true);
		        ImageReadParam param = reader.getDefaultReadParam();
		 
		        Image image = reader.read(0, param);
		        //got an image file
		 
		        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		        //bufferedImage is the RenderedImage to be written
		 
		        Graphics2D g2 = bufferedImage.createGraphics();
		        g2.drawImage(image, null, null);
		 
		        File imageFile = new File("resources/pgmfile.jpg");
		        ImageIO.write(bufferedImage, "jpg", imageFile);
		 
		        System.out.println(imageFile.getPath());
//			  if (directoryListing != null) {
//				int fileCount =1;
//			    for (File child : directoryListing) {
//			      System.out.println("file number "+fileCount+" "+child.getPath());
//			      byte [] currentFile=Files.readAllBytes(Paths.get(child.getPath()));
//			      for (File innerChild : directoryListing) {
//			    	  byte [] fileToBeMatched=Files.readAllBytes(Paths.get(innerChild.getPath()));
//			    	  double matchScore=fingerPrintUtillity.matchFingerPrintData(currentFile,fileToBeMatched);
//					  System.out.println("Finger print data match score for "+child.getName()+" and "+innerChild.getName()+" is ="+matchScore);	
//			      }
//			    }
//			  } else {
//			    // Handle the case where dir is not really a directory.
//			    // Checking dir.isDirectory() above would not be sufficient
//			    // to avoid race conditions with another process that deletes
//			    // directories.
//			  }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public FingerprintTemplate generateTemplateFromImage(byte [] imageByteArray){
		FingerprintTemplate fingerPrintTemplate = new FingerprintTemplate(imageByteArray);
		return fingerPrintTemplate;
	}
	
	/**
	 * @param scannedData
	 * @param storedData
	 * @return finger print data match score
	 */
	public double matchFingerPrintData(byte []scannedData,byte[] storedData){
		FingerprintTemplate probe=generateTemplateFromImage(scannedData);
		FingerprintTemplate candidate=generateTemplateFromImage(storedData);
		FingerprintMatcher matcher = new FingerprintMatcher(probe);
		double score = matcher.match(candidate);
		return score;
		
	}
	
}

