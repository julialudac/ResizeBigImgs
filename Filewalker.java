// src: https://stackoverflow.com/questions/2056221/recursively-list-files-in-java

import java.io.File;
import java.util.ArrayList;

public class Filewalker {

	private ArrayList<String> al;


	public Filewalker() {
		al = new ArrayList<String>(); 
	}

	// renamed method from walk -> walkNDisplay
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
	// does not return something (it is recursive so don't want to take any risk) but modifies sth that you can use (here, al)
	// after calling the method, the list al is filled. For now, I don't need to clear it.
    public void walkNReturn( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

        if (list == null) return ;

        for ( File f : list ) {
            if ( f.isDirectory() ) {
                walkNReturn( f.getAbsolutePath() );
			}
            if (! f.isDirectory() ) {
				// we want to convert \ to /
				String absPath = f.getAbsoluteFile().toString().replace("\\", "/"); 
				//System.out.println("Path name:" + absPath);
				al.add(absPath);
            }
        }
    }

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
