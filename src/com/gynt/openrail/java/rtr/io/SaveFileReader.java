package com.gynt.openrail.java.rtr.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SaveFileReader {
	
	public static final void read(byte[] data) {
		
		ByteBuffer bb = ByteBuffer.wrap(data);
		final ByteOrder original = bb.order();
		bb.order(ByteOrder.LITTLE_ENDIAN);
		byte[] temp = null;
		
		temp = new byte[15];
		bb.get(temp);
		String fileheader = new String(temp);
		
		int trackcount = bb.getInt(); 
		int i = 0;		
		
		while(i<trackcount) {
			i++;
			
			byte[] unknown = new byte[4];
			
			byte[] id1 = new byte[4];
			bb.get(id1);
			
			byte[] id2 = new byte[4];
			bb.get(id2);
			
			int tracknumber = bb.getInt();
			
			bb.get(unknown);
			bb.get(unknown);
			
			float tracklength = bb.getFloat();
			
			bb.get(unknown);
			
			float x0 = bb.getFloat();
			float z0 = bb.getFloat();
			
			bb.get(unknown);
			bb.get(unknown);

			float rotation = bb.getFloat();
			
			bb.get(unknown);
			bb.get(unknown);
			bb.get(unknown);
			
			int end1_connected_with_id = bb.getInt();
			bb.get(unknown);
			int totalconnectedexits1 = bb.getInt(); //2-connected Uncertain
			bb.get(unknown);
			bb.get(unknown);
			float x1 = bb.getFloat();
			float z1 = bb.getFloat();
			
			bb.get(unknown);
			bb.get(unknown);
			
			int end2_connected_with_id = bb.getInt();
			bb.get(unknown);
			int totalconnectedexits2 = bb.getInt(); //2-connected Uncertain
			bb.get(unknown);
			bb.get(unknown);
			float x2 = bb.getFloat();
			float z2 = bb.getFloat();
			
			bb.get(unknown);
			bb.get(unknown);
			
			bb.get(unknown);
			bb.get(unknown);
			bb.get(unknown);
			bb.get(unknown);
			bb.get(unknown);
			float height1 = bb.getFloat(); //Unsure!
			float height2 = bb.getFloat(); //Unsure!
			
			byte[] unknownremainder = new byte[40*4];
			bb.get(unknownremainder);
			
		}
		
		
		
		
	}

}
