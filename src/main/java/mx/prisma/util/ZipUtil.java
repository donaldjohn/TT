package mx.prisma.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	private static List<String> fileList;
    /**
     * Zip it
     * @param source folder
     * @param zipFile output ZIP file location
     */
    public static void zipIt(String source, String zipFile){
    	fileList = new ArrayList<String>();
    	generateFileList(source, new File(source));
    	
     byte[] buffer = new byte[1024];
    	
     try{
    		
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
    		
    	for(String file : fileList){
    			
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
               
        	FileInputStream in = 
                       new FileInputStream(source + File.separator + file);
       	   
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
               
        	in.close();
    	}
    		
    	zos.closeEntry();
    	//remember close it
    	zos.close();
          
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   }
    
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param source folder
     * @param node file or directory
     */
    public static void generateFileList(String source, File node){

    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(source, node.getAbsoluteFile().toString()));
	}
		
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(source, new File(node, filename));
		}
	}
 
    }

    /**
     * Format the file path for zip
     * @param source file path
     * @param file file path
     * @return Formatted file path
     */
    private static String generateZipEntry(String source, String file){
    	return file.substring(source.length(), file.length());
    }
}
