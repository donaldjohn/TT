package mx.prisma.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

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
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}

	public static File convertByteArrayToFile(String ruta, byte[] bImage)
			throws FileNotFoundException, IOException {
		if (bImage == null) {
			System.err.println("No se puede convertir un arreglo nulo");
			return null;
		}

		File file = new File(ruta);
		file.getParentFile().mkdirs();
		file.createNewFile();

		try {

			FileOutputStream fileOuputStream = new FileOutputStream(file);
			fileOuputStream.write(bImage);
			fileOuputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
	
	public static byte[] decodeByteArrayB64( byte[] bImage) {
		Base64 decoder = new Base64();
		byte[] bImageDecod = null;
		if(bImage != null) {
			bImageDecod = decoder.decode(bImage);
		} else {
			System.err.println("No se puede decodificar un array nulo");
		}
		return bImageDecod;
	}

	public static byte[] encodeByteArrayB64(byte[] bImagen) {
		Base64 encoder = new Base64();
		byte[] bImageEncod = null;
		if(bImagen != null) {
			bImageEncod = encoder.encode(bImagen);
		} else {
			System.err.println("No se puede codificar un array nulo");
		}
		
		return bImageEncod;
	}

	public static byte[] convertStringPNGB64ToBytes(String string) {
		if(string != null && !string.equals("")) {
			int index = string.indexOf("base64") + 7;
			return string.substring(index).getBytes();
		} else {
			System.err.println("No se puede convertir una cadena nula a byte array");
		}
		return null;
	}

	public static String convertBytesToStringPNGB64(byte[] bytes) {
		String string = null;
		if(bytes != null) {
			string = new String(bytes);
			string = "data:image/png;base64,".concat(string);
		} else {
			System.err.println("No se puede convertir un byte array nulo a cadena");
		}
		
		return string;
	}
}
