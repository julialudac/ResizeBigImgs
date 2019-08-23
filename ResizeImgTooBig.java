import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import javax.imageio.ImageIO;


// Resizes all images from a folder that are too big, recursively. To do so, it does not edit the existing files, but it creates a smaller version of them into another folder.
public class ResizeImgTooBig{

	private String startFolder; // folder from which we want to get reduced versions of its images, recursively. 
	private String outputFolder; // must be an existing path (for now). TODO: creates the folder if does not exist.
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

	public void resizeFolderImgs() {
	
		// use the FileWalker class to get the file names
		Filewalker fw = new Filewalker();
		fw.walkNReturn(startFolder);
		ArrayList<String> al = fw.copyAl(); 

		// Create the existing directories in the startFolder into the outputFolder, so we don't get any error when putting new files to these directories
		System.out.println("Creating the subdirectories...");
		Filewalker fw2 = new Filewalker();
		fw2.walkNCreateDirs(startFolder, startFolder, outputFolder);

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
		// System.out.println("Image name:" + imageName);

		try{
			// get image dimensions. Ok it's not optimized that we create a Buffer again that is already created in the class just to know the dimensions that we need. But I don't want to modify ImageResizer.
			File inputFile = new File(pathToOne);
			BufferedImage inputImage = ImageIO.read(inputFile);
			int w = inputImage.getWidth();
			int h = inputImage.getHeight();
			double factor = 1.0;
			while(w / factor > bigW && h / factor > bigH) {
				factor *= factorIncrement;
			}

			// if image needs to have a smaller version, save it to the output folder at a similar path to the startfolder. 
			// Ex: if startFolder = C:/a, pathToOne = C:/a/b/c/toto.jpg and outputFolder = ./reduced, write the new image to ./reduced/b/c/toto.jpg.
			if(factor > 1){
				String dirRelativePath = pathToOne.substring(startFolder.length() + 1, pathToOne.length());
				String dest = outputFolder + "/" + dirRelativePath;
				dest = dest.replace("\\", "/");
				ImageResizer.resize(pathToOne, dest , 1.0 / factor); 
				System.out.println("Resizing image at " + pathToOne + " and saving the new image to " + dest);
			}
		}catch (IOException ex) {
			System.out.println("Error resizing the image.");
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
			if(args.length == 0) {
				throw new RuntimeException("As argument, you must give a path to the folder you want a reduced version of the photos!");
			}else{
				String src = args[0]; // "C:/Users/julia/Downloads/apart/aachen0819/sat170819/restos";
				System.out.println("All photos from " + src + " will potentially have a smaller version of themselves");
				ResizeImgTooBig ri = new ResizeImgTooBig(src, "./reduced", 2000, 1500, 1.5);
				ri.resizeFolderImgs();
			}


		}
}

			


