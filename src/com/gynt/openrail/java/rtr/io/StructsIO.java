package com.gynt.openrail.java.rtr.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.text.ParseException;


import com.gynt.lwproto.LWProto;
import com.gynt.lwproto.LWProto.AbstractSerializer;
import com.gynt.openrail.java.rtr.io.Structs.SaveFile;
import com.gynt.openrail.java.rtr.io.Structs.Terrain;
import com.gynt.openrail.java.rtr.io.Structs.r7;
import com.gynt.openrail.java.rtr.io.Structs.tr10_2;
import com.gynt.openrail.java.rtr.io.Structs.tr10_4;
import com.gynt.openrail.java.rtr.io.Structs.TextureMap;
import com.gynt.openrail.java.utils.StringPrint;

public class StructsIO {

	static {
		LWProto.register(Structs.Track[].class, new LWProto.Serializer<Structs.Track[]>(Structs.Track[].class));
		for(Class<?> c : Structs.class.getDeclaredClasses()) {
			if(c.isEnum()) continue;
			LWProto.register(c, new LWProto.Serializer(c, false));
			Class<?> arraytype = Array.newInstance(c, 0).getClass();
			LWProto.register(arraytype, new LWProto.Serializer(arraytype));
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
		LWProto.register(Structs.Terrain.class, new AbstractSerializer<Structs.Terrain>(Structs.Terrain.class) {

			@Override
			public Terrain deserialize(ByteBuffer b)
					throws InstantiationException, IllegalAccessException, ParseException {
				Terrain result = new Terrain();
				result.unknown = b.getInt();
				result.unknown2 = b.getInt();
				result.unknownCount = b.getInt();
				result.terrain = new Structs.HeightMap[result.unknownCount];
				for(int i = 0; i < result.unknownCount; i++) {
					result.terrain[i] = LWProto.retrieve(Structs.HeightMap.class).deserialize(b);
				}
				result.unknown3 = new tr10_2[result.unknownCount];
				for(int i = 0; i < result.unknownCount; i++) {
					result.unknown3[i] = LWProto.retrieve(Structs.tr10_2.class).deserialize(b);
				}
				result.u_texture = new TextureMap[result.unknownCount*6];
				for(int i = 0; i < result.unknownCount*6; i++) {
					result.u_texture[i] = LWProto.retrieve(Structs.TextureMap.class).deserialize(b);
				}
				result.unknownCount2 = b.getInt();
				result.unknown4 = new tr10_4[result.unknownCount2];
				for(int i = 0; i < result.unknownCount2; i++) {
					result.unknown4[i] = LWProto.retrieve(Structs.tr10_4.class).deserialize(b);
				}
				return result;
			}
			
			@Override
			public Terrain deserialize(byte[] data)
					throws InstantiationException, IllegalAccessException, ParseException {
				return deserialize(ByteBuffer.wrap(data));
			}

			@Override
			public byte[] serialize(Terrain obj) throws IllegalArgumentException, IllegalAccessException,
					NoSuchFieldException, SecurityException, InstantiationException {
				// TODO Auto-generated method stub
				return null;
			}
			
		});

		
	}
	
	public static SaveFile deserialize(byte[] data) throws InstantiationException, IllegalAccessException, ParseException {
		ByteBuffer b = ByteBuffer.wrap(data);
		b.order(ByteOrder.LITTLE_ENDIAN);
		SaveFile result = LWProto.retrieve(Structs.SaveFile.class).deserialize(b);
                System.out.println("Deserialization succesful. Bytes remaining: " + b.remaining());
                return result;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ParseException, IOException {
		
		SaveFile s1 = deserialize(Files.readAllBytes(new File("v1.roa").toPath()));
		SaveFile s2 = deserialize(Files.readAllBytes(new File("v1a.roa").toPath()));
		
		System.out.println(s1.view.x);
		System.out.println(s1.view.z);
		System.out.println(s1.view.y);
		System.out.println("");
		System.out.println(s2.view.x);
		System.out.println(s2.view.z);
		System.out.println(s2.view.y);
                
                System.out.println(StringPrint.getString(s1.view));
	}
	
}
