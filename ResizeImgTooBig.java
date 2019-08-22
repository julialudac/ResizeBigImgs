import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import javax.imageio.ImageIO;


// Resizes all images from a folder that are too big
public class ResizeImgTooBig{

	private String startFolder; // folder from which we want to get a reduced versions of its images, not recursively in a first time. TODO: do the recursive part. I mean the listing from FileWalker is recursive but not thr output saving part.
	private String outputFolder; // must be an existing path (for now)
	private int bigW;
	private int bigH;
	private double factorIncrement;

	public ResizeImgTooBig(){
		this.startFolder = ".";
		this.outputFolder = "./reduced";
		this.bigW = 1200;
		this.bigH = 1000;
		this.factorIncrement = 2.0;
	}

	public ResizeImgTooBig(String folder, String outputFolder, int bigW, int bigH, double factorIncrement){
		this.startFolder = folder.replace("\\", "/");
		this.outputFolder = outputFolder.replace("\\", "/");
		this.bigW = bigW;
		this.bigH = bigH;
		this.factorIncrement = factorIncrement;
	}

	// as said upper, the browsing is recursie but not yet the destination to save the reduced images.
	public void resizeFolderImgs() {
	
		// use the FileWalker class to get the file names
		Filewalker fw = new Filewalker();
		fw.walkNReturn(startFolder);
		ArrayList<String> al = fw.copyAl(); 

		// for each image, reduce it when needed (i.e. it's too big)
		for(int i=0; i<al.size(); i++) {
			String currentPath = al.get(i);
			// we are only interested in images
			String suffix = currentPath.substring(currentPath.lastIndexOf(".")+1, currentPath.length()); 
			if(suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png")) { // TODO complete and also handle uppercase when necessary
				potentiallyResizeOne(currentPath);
			}
		}


	}

	// Resize only one. = potentially writes a new reduced image to outputFolder if the image is too big. 
	public void potentiallyResizeOne(String pathToOne){
			
		int nameIndex = pathToOne.lastIndexOf("/") +1;
		String imageName = pathToOne.substring(nameIndex, pathToOne.length());
		// System.out.println("Image name index:" + nameIndex);
		System.out.println("Image name:" + imageName);

		try{
			// get image dimensions. Ok it's not optimized that we create a Buffer again that is already created in the class, just to know the dimensions that we need.
			File inputFile = new File(pathToOne);
			BufferedImage inputImage = ImageIO.read(inputFile);
			int w = inputImage.getWidth();
			int h = inputImage.getHeight();
			double factor = 1.0;
			while(w / factor > bigW && h / factor > bigH) {
				factor *= factorIncrement;
			}

			if(factor > 1){
				ImageResizer.resize(pathToOne, outputFolder + "/" + imageName, 1.0 / factor); 
				System.out.println("Resizing image at " + pathToOne + " and saving the new image to " + outputFolder + "/" + imageName);
			}
		}catch (IOException ex) {
			System.out.println("Error resizing the image.");
			ex.printStackTrace();
		}
	}

	public static void main(String[] args){
		String src = "C:/Users/julia/Downloads/apart";
		ResizeImgTooBig ri = new ResizeImgTooBig(src, "./reduced", 1200, 1000, 1.5);
		ri.resizeFolderImgs();

	}

			

}
