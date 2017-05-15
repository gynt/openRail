package com.gynt.openrail.java.rtr.io;

public class Structs {

    public static class ARGB {

        public byte alpha;
        public byte red;
        public byte green;
        public byte blue;
    };

    public static class TrackElement2 {

        public int[] unknown = new int[47];
        public int[] unknown2 = new int[55];
    };

    public static class tr2 {

        public int[] unknown = new int[9];
    };

    public static class tr3 {

        public int[] unknown = new int[24];
    };

    public static class r12 {

        public float unknown;
    };

    public static class r11 {

        public int unknown;
        public float unknown2;
        public float[] unknown3 = new float[21];
        public int unknown4;
        public int unknown5;
    };

    public static class HeightMap {

        public float x_or_z;
        public float x_or_z2;
        public float y;
        public float[] unknown = new float[3];
    };

    public static class tr10_2 {

        public float unknown;
    };

    public static class TextureMap {

        public float[] unknown = new float[7];
    };

    public static class tr10_4 {

        public int[] unknown = new int[4];
    };

    public static class Terrain {

        public int unknown;
        public int unknown2;
        public int width_height_multiplied;
        public HeightMap[] terrain = new HeightMap[width_height_multiplied];
        public   tr10_2[] unknown3 = new tr10_2[width_height_multiplied];
        public TextureMap[] u_texture = new TextureMap[width_height_multiplied * 6];
        public int unknownCount2;
        public   tr10_4[] unknown4 = new tr10_4[unknownCount2];
    };

    public static class r9 {

        public float[] ebp_C1 = new float[9];
        public float ebp_4;
        public float[] ebp_C2 = new float[2];
        public float ebp_8_1;
        public float ebp_8_2;
        public float ebp_8_3;
        public float[] ebp_C3 = new float[2];
    };

    public static class View {
        //FFFFFFFF

        public float eax_d;
        public float[] eax_b = new float[3]; //x,z,y?
        public float x;
        public float z;
        public float y;
        public float[] eax_c = new float[3]; //x,z,y?
        public float eax_d2;
        public float eax_a;
        public float unknown;
        public float unknown1;
        public float unknown2;
        public float[] unknown3 = new float[4];//This appears conditional, but I do not know how it works exactly.

        //After some shuffling around, 
    };

    public static class r7 {

        public byte unknown;
        public int u_non0;
        public float[] unknown2 = new float[u_non0 * 2];
    };

    enum FieldObjectType {
        UNKNOWN0,
        UNKNOWN1,
        PLANT,
        HOUSE,
        INDUSTRY,
        RAILWAY,
        MISC
    };

    public static class FieldObject {

        public float[] unknown1 = new float[2];
        FieldObjectType u_type;
        public int id;
        public float x;
        public float z;
        public float y;
        public float unknown2;
        public float unknown3;
        public float unknown4;
        public float unknown5;
        public float unknown6;
        public float unknown7;
        public float unknown8;
        public float[] unknown9 = new float[4];
        public float rotation;
        public float[] unknown10 = new float[5];
        public   ARGB rgb1;
        public int unknown11;
        public     ARGB rgb2;
        public int[] unknown12 = new int[2];
        public byte[] name = new byte[16];
        public int[] unknown13 = new int[14];
    };

    public static class TrackElement {

        public int[] unknown = new int[24];
    };

    public static class Train {

        public int[] unknown = new int[12];
        public int id;
        public byte[] engineName = new byte[12];
        public float[] unknown2 = new float[4];
        public float unknown3;
        public float halfsize;
        public int speedLimit;
        public int unknown4;

        public float x_center;
        public float z_center;
        public float y_center;
        public float rotation_center;
        public float[] unknown5 = new float[2];

        public float[] unknown6 = new float[8];

        public float x_back;
        public float z_back;
        public float y_back;
        public float rotation_back;
        public int[] unknown7 = new int[2];

        public float x_front;
        public float z_front;
        public float y_front;
        public float rotation_front;
        public int[] unknown8 = new int[2];

        public float[] unknown9 = new float[1206];
    };

    public static class TrackEnd {

        public int u_connection; //Unknown
        public int connectedTo;
        public int[] unknown = new int[4];
        public float x;
        public float z;
        public float y;
    };

    enum TrackTypes {
        NORMAL,
        LARGE,
        CURVE_NORMAL,
        SWITCH_LEFT,
        SWITCH_RIGHT,
        BUMPER,
        SMALL,
        CURVE_LARGE,
        CURVE_XLARGE
    };

    public static class Track {

        public int exit0;
        public int exit1;
        public int trackNumber;
        public int unknown;
        public     TrackTypes trackType;
        public float trackLength;
        public int unknown2;
        public float x;
        public float z;
        public float y;
        public int unknown3;
        public float rotation;
        public int[] unknown4 = new int[2];
        public     TrackEnd[] trackEnds = new TrackEnd[3];
        public int[] unknown5 = new int[4];
        public int powerLevel;
        public int powerLevelOriginTrack;
        public int[] unknown6 = new int[34];

    };

    public static class Header {
        public byte[] header = new byte[15];
    }
    
    public static class Tracks {
    	public    Track[] tracks;
    }
    
    public static class r2 {
    	public    tr2[] unknown;
    }
    
    public static class TrackElements {
    	public    TrackElement[] trackElements;
        public int unknownTrackElementData;
    }
    
    public static class Trains {
    	public     Train[] trains;
    }
    
    public static class TrackElements2 {
    	public     TrackElement2[] unknown2;
        public int unknowntr5Data;
    }
    
    public static class FieldObjects {
    	public   FieldObject[] fieldobjects;
    }
    
    public static class SaveFile {

       public Header header;

       public  Tracks tracks;
        
       public   r2 r2;

       public   TrackElements trackElements;

       public   Trains trains;
        
       public   TrackElements2 TrackElements2;

       public    FieldObjects fieldObjects;

       public    r7 r7;
       public     View view;
        public   r9 r9;
        public      Terrain terrain;
        public    r11 r11;
        public     r12 r12;
    };

}
