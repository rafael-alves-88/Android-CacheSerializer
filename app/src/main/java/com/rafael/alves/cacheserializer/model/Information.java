package com.rafael.alves.cacheserializer.model;

import java.io.Serializable;

public class Information implements Serializable {

    public static final String SERIALIZER_UUID = "3f2eca75-225c-40e5-a319-de86bbba20ef";

    public String Field1;
    public String Field2;

    public Information() {
        Field1 = "";
        Field2 = "";
    }
}
