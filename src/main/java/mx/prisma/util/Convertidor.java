package mx.prisma.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class Convertidor {
	public static byte[] convertFileToByteArray(File file) {
		if (file == null) {
			System.err.println("No se puede convertir un archivo nulo");
			return null;
		}
		FileInputStream fileInputStream = null;
		byte[] bFile = null;
		try {
			bFile = new byte[(int) file.length()];
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			/*
			 * for (int i = 0; i < bFile.length; i++) { System.out.print((char)
			 * bFile[i]); }
			 */

			// System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}

	public static File convertByteArrayToFile(String ruta, byte[] bImage)
			throws FileNotFoundException, IOException {
		System.out.println("ruta desde convertidor " + ruta);
		if (bImage == null) {
			System.err.println("No se puede convertir un arreglo nulo");
			return null;
		}

		File file = new File(ruta);
		System.out.println("isDire desde convertidor " + file.isDirectory());
		if (file.getParentFile().mkdirs()) {
			System.out.println("Se creo la carpeta");
		} else {
			System.out.println("NO se creo la carpeta");
		}
		
		if(file.createNewFile()){
			System.out.println("Se creo la imagen");
		} else {
			System.out.println("La imagen se actualizo");
		}

		try {

			FileOutputStream fileOuputStream = new FileOutputStream(file);
			fileOuputStream.write(bImage);
			fileOuputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
}
