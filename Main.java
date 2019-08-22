import java.io.*;		
import java.lang.RuntimeException;
import java.util.ArrayList;


// Resizes all images from a folder that are too big
public class Main{

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
	//	public static void main(String[] args) {
	//	Filewalker fw = new Filewalker();
	//	fw.walkNReturn("C:/Users/julia/Downloads/apart");
	//	ArrayList<String> al = fw.copyAl(); 
	//	System.out.println("--------- In the list form: ----------------------------");
	//	for(int i=0; i<al.size(); i++) {
	//		System.out.println(al.get(i));
	//	}
	//}

}
