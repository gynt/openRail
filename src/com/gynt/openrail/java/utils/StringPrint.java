/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gynt.openrail.java.utils;

import java.lang.reflect.Field;

/**
 *
 * @author frank
 */
public class StringPrint {
    
    public static String getString(Object o) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        String result = "";
        
        for(Field f : fields) {
            f.setAccessible(true);
            String subresult = "\n";
            subresult+=f.getName();
            subresult+=" ";
            subresult+=f.get(o).toString();
            result+=subresult;
        }
        
        return result;
    }
    
}
