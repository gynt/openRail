package com.gynt.openrail.java.rtr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SaveFileConversion {

	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
			String command = args[0];
			String filepath = args[1];
			File file = new File(filepath);
			
			byte[] data = Files.readAllBytes(file.toPath());

			if (command.equals("-e")) {
				StringBuilder sb = new StringBuilder();
				for (int i = 15; i < data.length - 4; i += 4) {
					sb.append(String.format("%02X ", data[i]));
					sb.append(String.format("%02X ", data[i + 1]));
					sb.append(String.format("%02X ", data[i + 2]));
					sb.append(String.format("%02X ", data[i + 3]));
					sb.append("\n");
				}
				Files.write(new File(filepath + ".csv").toPath(), sb.toString().getBytes());
				
				System.out.println("Succesfully exported");
			} else if (command.equals("-i")) {
				String sdata = new String(data);
				String[] lines = sdata.split("\n");
				System.out.println(String.format("Lines: {}", lines.length));

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				baos.write("File version:15".getBytes());
				String hexstring = "";
				for (String line : lines) {
					String[] bytes = line.split(" ");
					for (String byt : bytes) {
						hexstring+=byt;
					}
				}
				baos.write(hexStringToByteArray(hexstring));
				Files.write(new File(filepath + ".roa").toPath(), baos.toByteArray());
				
				System.out.println("Succesfully imported");
			} else {
				System.out.println("Usage: -i/-e file");
			}
		} else {
			System.out.println("Usage: -i/-e file");
		}
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
