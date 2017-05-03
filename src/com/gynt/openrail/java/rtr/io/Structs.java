package com.gynt.openrail.java.rtr.io;

public class Structs {

    public static class ARGB {

        byte alpha;
        byte red;
        byte green;
        byte blue;
    };

    public static class TrackElement2 {

        int[] unknown = new int[47];
        int[] unknown2 = new int[55];
    };

    public static class tr2 {

        int[] unknown = new int[9];
    };

    public static class tr3 {

        int[] unknown = new int[24];
    };

    public static class r12 {

        float unknown;
    };

    public static class r11 {

        int unknown;
        float unknown2;
        float[] unknown3 = new float[21];
        int unknown4;
        int unknown5;
    };

    public static class HeightMap {

        float x_or_z;
        float x_or_z2;
        float y;
        float[] unknown = new float[3];
    };

    public static class tr10_2 {

        float unknown;
    };

    public static class TextureMap {

        float[] unknown = new float[7];
    };

    public static class tr10_4 {

        int[] unknown = new int[4];
    };

    public static class Terrain {

        int unknown;
        int unknown2;
        int unknownCount;
        HeightMap[] terrain = new HeightMap[unknownCount];
        tr10_2[] unknown3 = new tr10_2[unknownCount];
        TextureMap[] u_texture = new TextureMap[unknownCount * 6];
        int unknownCount2;
        tr10_4[] unknown4 = new tr10_4[unknownCount2];
    };

    public static class r9 {

        float[] ebp_C1 = new float[9];
        float ebp_4;
        float[] ebp_C2 = new float[2];
        float ebp_8_1;
        float ebp_8_2;
        float ebp_8_3;
        float[] ebp_C3 = new float[2];
    };

    public static class View {
        //FFFFFFFF

        float eax_d;
        float[] eax_b = new float[3]; //x,z,y?
        float x;
        float z;
        float y;
        float[] eax_c = new float[3]; //x,z,y?
        float eax_d2;
        float eax_a;
        float unknown;
        float unknown1;
        float unknown2;
        float[] unknown3 = new float[4];//This appears conditional, but I do not know how it works exactly.

        //After some shuffling around, 
    };

    public static class r7 {

        byte unknown;
        int u_non0;
        float[] unknown2 = new float[u_non0 * 2];
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

        float[] unknown1 = new float[2];
        FieldObjectType u_type;
        int id;
        float x;
        float z;
        float y;
        float unknown2;
        float unknown3;
        float unknown4;
        float unknown5;
        float unknown6;
        float unknown7;
        float unknown8;
        float[] unknown9 = new float[4];
        float rotation;
        float[] unknown10 = new float[5];
        ARGB rgb1;
        int unknown11;
        ARGB rgb2;
        int[] unknown12 = new int[2];
        byte[] name = new byte[16];
        int[] unknown13 = new int[14];
    };

    public static class TrackElement {

        int[] unknown = new int[24];
    };

    public static class Train {

        int[] unknown = new int[12];
        int id;
        byte[] engineName = new byte[12];
        float[] unknown2 = new float[4];
        float unknown3;
        float halfsize;
        int speedLimit;
        int unknown4;

        float x_center;
        float z_center;
        float y_center;
        float rotation_center;
        float[] unknown5 = new float[2];

        float[] unknown6 = new float[8];

        float x_back;
        float z_back;
        float y_back;
        float rotation_back;
        int[] unknown7 = new int[2];

        float x_front;
        float z_front;
        float y_front;
        float rotation_front;
        int[] unknown8 = new int[2];

        float[] unknown9 = new float[1206];
    };

    public static class TrackEnd {

        int u_connection; //Unknown
        int connectedTo;
        int[] unknown = new int[4];
        float x;
        float z;
        float y;
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

        int exit0;
        int exit1;
        int trackNumber;
        int unknown;
        TrackTypes trackType;
        float trackLength;
        int unknown2;
        float x;
        float z;
        float y;
        int unknown3;
        float rotation;
        int[] unknown4 = new int[2];
        TrackEnd[] trackEnds = new TrackEnd[3];
        int[] unknown5 = new int[4];
        int powerLevel;
        int powerLevelOriginTrack;
        int[] unknown6 = new int[34];

    };

    public static class Header {
        byte[] header = new byte[15];
    }
    
    public static class Tracks {
        Track[] tracks;
    }
    
    public static class r2 {
        tr2[] unknown;
    }
    
    public static class TrackElements {
        TrackElement[] trackElements;
        int unknownTrackElementData;
    }
    
    public static class Trains {
        Train[] trains;
    }
    
    public static class TrackElements2 {
        TrackElement2[] unknown2;
        int unknowntr5Data;
    }
    
    public static class FieldObjects {
        FieldObject[] fieldobjects;
    }
    
    public static class SaveFile {

        Header header;

        Tracks tracks;
        
        r2 r2;

        TrackElements trackElements;

        Trains trains;
        
        TrackElements2 TrackElements2;

        FieldObjects fieldObjects;

        r7 r7;
        View view;
        r9 r9;
        Terrain terrain;
        r11 r11;
        r12 r12;
    };

}
