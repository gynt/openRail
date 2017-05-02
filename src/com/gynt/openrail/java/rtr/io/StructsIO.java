package com.gynt.openrail.java.rtr.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;

import javax.swing.JFileChooser;

import com.gynt.lwproto.LWProto;
import com.gynt.lwproto.LWProto.AbstractSerializer;
import com.gynt.openrail.java.rtr.io.Structs.SaveFile;
import com.gynt.openrail.java.rtr.io.Structs.Terrain;
import com.gynt.openrail.java.rtr.io.Structs.Track;
import com.gynt.openrail.java.rtr.io.Structs.r10_u_Terrain;
import com.gynt.openrail.java.rtr.io.Structs.r7;
import com.gynt.openrail.java.rtr.io.Structs.tr10_2;
import com.gynt.openrail.java.rtr.io.Structs.tr10_4;
import com.gynt.openrail.java.rtr.io.Structs.u_Texture;

public class StructsIO {

	static {
//		LWProto.register(Structs.SaveFile.class, new AbstractSerializer<Structs.SaveFile>(Structs.SaveFile.class) {
//			
//			@Override
//			public SaveFile deserialize(ByteBuffer bb)
//					throws InstantiationException, IllegalAccessException, ParseException {
//				bb.order(ByteOrder.LITTLE_ENDIAN);
//				
//				byte[] tmp = new byte[15];
//				bb.get(tmp);
//				
//				SaveFile result = new SaveFile();
//				
//				String header = new String(tmp, Charset.forName("UTF-8"));
//				if(!header.equals("File version:15")) throw new ParseException("Invalid header: " + header, 0);
//								
//				result.tracks = LWProto.retrieve(Structs.Track[].class).deserialize(bb);
//				
//				result.unknown = LWProto.retrieve(Structs.tr2[].class).deserialize(bb);
//
//				result.trackElements = LWProto.retrieve(Structs.TrackElement[].class).deserialize(bb);
//
//				result.trains = LWProto.retrieve(Structs.Train[].class).deserialize(bb);
//				
//				result.unknown2 = LWProto.retrieve(Structs.tr5[].class).deserialize(bb);
//				
//				result.fieldobjects = LWProto.retrieve(Structs.FieldObject[].class).deserialize(bb);
//				
//				result._r7 = LWProto.retrieve(Structs.r7.class).deserialize(bb);
//				result._r8_u_View = LWProto.retrieve(Structs.r8_u_View.class).deserialize(bb);
//				result._r9 = LWProto.retrieve(Structs.r9.class).deserialize(bb);
//				result._r10_u_Terrain = LWProto.retrieve(Structs.r10_u_Terrain.class).deserialize(bb);
//				result._r11 = LWProto.retrieve(Structs.r11.class).deserialize(bb);
//				result._r12 = LWProto.retrieve(Structs.r12.class).deserialize(bb);
//				
//				return result;
//			}
//
//			@Override
//			public SaveFile deserialize(byte[] data) throws InstantiationException, IllegalAccessException, ParseException {
//				ByteBuffer bb = ByteBuffer.wrap(data);
//				return super.deserialize(bb);
//			}
//
//			@Override
//			public byte[] serialize(SaveFile obj) throws IllegalArgumentException, IllegalAccessException,
//					NoSuchFieldException, SecurityException, InstantiationException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		});
		LWProto.register(Structs.Track[].class, new LWProto.Serializer<Structs.Track[]>(Structs.Track[].class));
		for(Class<?> c : Structs.class.getDeclaredClasses()) {
			if(c.isEnum()) continue;
			LWProto.register(c, new LWProto.Serializer<>(c, false));
			Class<?> arraytype = Array.newInstance(c, 0).getClass();
			LWProto.register(arraytype, new LWProto.Serializer<>(arraytype));
		}
		LWProto.register(Structs.r7.class, new AbstractSerializer<Structs.r7>(Structs.r7.class) {

			@Override
			public r7 deserialize(ByteBuffer b) throws InstantiationException, IllegalAccessException, ParseException {
				r7 result = new r7();
				result.unknown = b.get();
				result.u_non0=b.getInt();
				result.unknown2 = new float[result.u_non0];
				for(int i = 0; i < result.unknown2.length; i++) {
					result.unknown2[i]=b.getFloat();
				}
				return result;
			}
			
			@Override
			public r7 deserialize(byte[] data) throws InstantiationException, IllegalAccessException, ParseException {
				return deserialize(ByteBuffer.wrap(data));
			}

			@Override
			public byte[] serialize(r7 obj) throws IllegalArgumentException, IllegalAccessException,
					NoSuchFieldException, SecurityException, InstantiationException {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		LWProto.register(Structs.r10_u_Terrain.class, new AbstractSerializer<Structs.r10_u_Terrain>(Structs.r10_u_Terrain.class) {

			@Override
			public r10_u_Terrain deserialize(ByteBuffer b)
					throws InstantiationException, IllegalAccessException, ParseException {
				r10_u_Terrain result = new r10_u_Terrain();
				result.unknown = b.getInt();
				result.unknown2 = b.getInt();
				result.unknownCount = b.getInt();
				result.terrain = new Structs.Terrain[result.unknownCount];
				for(int i = 0; i < result.unknownCount; i++) {
					result.terrain[i] = LWProto.retrieve(Structs.Terrain.class).deserialize(b);
				}
				result.unknown3 = new tr10_2[result.unknownCount];
				for(int i = 0; i < result.unknownCount; i++) {
					result.unknown3[i] = LWProto.retrieve(Structs.tr10_2.class).deserialize(b);
				}
				result.u_texture = new u_Texture[result.unknownCount*6];
				for(int i = 0; i < result.unknownCount*6; i++) {
					result.u_texture[i] = LWProto.retrieve(Structs.u_Texture.class).deserialize(b);
				}
				result.unknownCount2 = b.getInt();
				result.unknown4 = new tr10_4[result.unknownCount2];
				for(int i = 0; i < result.unknownCount2; i++) {
					result.unknown4[i] = LWProto.retrieve(Structs.tr10_4.class).deserialize(b);
				}
				return result;
			}
			
			@Override
			public r10_u_Terrain deserialize(byte[] data)
					throws InstantiationException, IllegalAccessException, ParseException {
				return deserialize(ByteBuffer.wrap(data));
			}

			@Override
			public byte[] serialize(r10_u_Terrain obj) throws IllegalArgumentException, IllegalAccessException,
					NoSuchFieldException, SecurityException, InstantiationException {
				// TODO Auto-generated method stub
				return null;
			}
			
		});

		
	}
	
	public static SaveFile deserialize(byte[] data) throws InstantiationException, IllegalAccessException, ParseException {
		ByteBuffer b = ByteBuffer.wrap(data);
		b.order(ByteOrder.LITTLE_ENDIAN);
		return LWProto.retrieve(Structs.SaveFile.class).deserialize(b);
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ParseException, IOException {
		
		SaveFile s1 = deserialize(Files.readAllBytes(new File("v1.roa").toPath()));
		SaveFile s2 = deserialize(Files.readAllBytes(new File("v2.roa").toPath()));
		
		System.out.println(s1._r8_u_View.x);
		System.out.println(s1._r8_u_View.z);
		System.out.println(s1._r8_u_View.y);
		System.out.println("");
		System.out.println(s2._r8_u_View.x);
		System.out.println(s2._r8_u_View.z);
		System.out.println(s2._r8_u_View.y);
	}
	
}
