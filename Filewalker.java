// src: https://stackoverflow.com/questions/2056221/recursively-list-files-in-java

import java.io.File;
import java.util.ArrayList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Filewalker {

	private ArrayList<String> al; // al like ArrayList. This variable is used by method walkNReturn.


	public Filewalker() {
		al = new ArrayList<String>(); 
	}

	// renamed method from walk -> walkNDisplay
	// Recursively browse the files and folders from path and display them.
    public void walkNDisplay( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walkNDisplay( f.getAbsolutePath() );
                System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else {
                System.out.println( "File:" + f.getAbsoluteFile() );
            }
        }
    }

	// created method
	// Contrarily to its name, does not return something (it is recursive so don't want to take any risk) but modifies an attribute of the class whose value can be 
	// copied into another variable thanks to method copyAl.
	// After calling the method, the attribute al is filled. For now, I don't need to clear it. It may be useful in the future (TODO?)
    public void walkNReturn( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return ;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walkNReturn( f.getAbsolutePath() );
			}
            if (! f.isDirectory() ) {
				// we want to convert all \ to /
				String absPath = f.getAbsoluteFile().toString().replace("\\", "/"); 
				//System.out.println("Path name:" + absPath);
				al.add(absPath);
            }
        }
    }

	// created method
	// Create directories situated into the sourcePath and place them in the destPath. The created directories are empty.
	// rootPath is the very beginning while the sourcePath changes depending on the recursion call.
	public void walkNCreateDirs(String rootPath, String sourcePath, String destPath) {
		
        File root = new File( sourcePath );
        File[] list = root.listFiles();

        if (list == null) return ;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walkNCreateDirs(rootPath, f.getAbsolutePath(), destPath );
				// If it's a directory, we create it in the destPath 
				try{
					String dirRelativePath = f.getAbsoluteFile().toString().substring(rootPath.length() + 1, f.getAbsoluteFile().toString().length());
					Files.createDirectories(Paths.get(destPath + "/" + dirRelativePath));
					System.out.println("Creating subdirectory " + dirRelativePath + " at " + destPath);
				}catch(IOException e){
					System.out.println("Error, maybe a permission denied?.");
					e.printStackTrace();
				}
			}
        }
	}

	// created method
	public ArrayList<String> copyAl() {
		
		ArrayList<String> al2 = new ArrayList<String>(al);
		return al2;

	}

	public static void main(String[] args) {
		Filewalker fw = new Filewalker();
		fw.walkNDisplay("C:/Users/julia/Downloads/apart" );
		fw.walkNReturn("C:/Users/julia/Downloads/apart");
		ArrayList<String> al = fw.copyAl(); 
		System.out.println("--------- In the list form: ----------------------------");
		for(int i=0; i<al.size(); i++) {
			System.out.println(al.get(i));
		}
	}


}
