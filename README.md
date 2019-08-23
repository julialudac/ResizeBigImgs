README ResizeBigImgs
====================

# Summary
This Java program allows to have reduced versions of too big photos from a directory. It will recursively search for the too big photos. For each of them, it will output a smaller version of it to a directory named 'reduced', located into the same directory as the *.class* files. This folder must exist (for now). 
It has been built using classes from other developers: 

* ImageResizer from *https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java*
* Filewalker from *https://stackoverflow.com/questions/2056221/recursively-list-files-in-java*

So my biggest contribution is class ResizeImgTooBig.

## Content
For those of you who don't care about the code source and just want to use the program, just download the *.class* files located into *compiledClass* folder.
If you want the source code, download the *.java* files and compile them yourself: `javac Main.java`


# How to use
In the directory that contains the *.class*  files, enter `java Main <sourceFolder>`
with sourceFolder the folder from which we want to look at too big pictures.
## Example
`java Main C:/Users/julia/Downloads/apart/lille/palais_rihour.jpg`


# Outlook
This is an unfinished work.

* For now, it saves all the reduced versions of the picked photos to a same folder. But then, it would be better to have recursive destination folders. 
* Do an executable of the program.
* Less redundancy, maybe. But in this case, we'll have to modify the original classes. Not really worth it because the redundancies are not numerous.
* Create destination folder if non existent.
* put more variables as arguments, e.g. the limit dimensions, the incremental factor
* See the TODOs.

