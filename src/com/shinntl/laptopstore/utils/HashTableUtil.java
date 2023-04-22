/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shinntl.laptopstore.utils;

import java.util.HashMap;

public class HashTableUtil {
    private static HashTableUtil myHashTable;
    private HashMap<String, Object> hashMap;
    private HashTableUtil() {
        hashMap = new HashMap<>();
    }
    public static HashTableUtil newInstance() {
        if(myHashTable == null) {
            myHashTable =  new HashTableUtil();
        }
        return myHashTable;
    }

    public void put(String key, Object value) {
        hashMap.put(key, value);
    }
    public void remove(String key) {
        hashMap.remove(key);
    }
    
    public Object get(String key) {
        return hashMap.get(key);
    }

    public HashMap<String, Object> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }
    
}
