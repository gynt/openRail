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
		
		byte[] unknown = new byte[4];
		
		while(i<trackcount) {
			i++;
			
			byte[] id1 = new byte[4];
			bb.get(id1);
			
			byte[] id2 = new byte[4];
			bb.get(id2);
			
			int tracknumber = bb.getInt();
			
			int sometimestracktype = bb.getInt();
			int tracktype = bb.getInt();
			
			float tracklength = bb.getFloat();
			
			bb.get(unknown);
			
			float x0 = bb.getFloat();
			float z0 = bb.getFloat();
			float y0 = bb.getFloat(); //More negative means higher...
			
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
			float y1 = bb.getFloat(); //More negative means higher...
			
			bb.get(unknown);
			
			int end2_connected_with_id = bb.getInt();
			bb.get(unknown);
			int totalconnectedexits2 = bb.getInt(); //2-connected Uncertain
			bb.get(unknown);
			bb.get(unknown);
			float x2 = bb.getFloat();
			float z2 = bb.getFloat();
			float y2 = bb.getFloat();
			
			bb.get(unknown);
			
			int end3_connected_with_id = bb.getInt();
			bb.get(unknown);
			int totalconnectedexits3 = bb.getInt(); //2-connected Uncertain.
			bb.get(unknown);
			bb.get(unknown);
			
			//Has value in case of three exits: switches. Else: -1000000
			float x3 = bb.getFloat();
			float y3 = bb.getFloat();
			float z3 = bb.getFloat();
			
			byte[] unknownremainder = new byte[39*4];
			bb.get(unknownremainder);
			
		}
		
		
		
		
	}

}
