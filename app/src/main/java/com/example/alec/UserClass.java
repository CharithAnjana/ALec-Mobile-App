package com.example.alec;

public class UserClass {
    String type;
    String id;

    public UserClass(String type, String id){
        this.type = type;
        this.id = id;
    }

    public String getType(){return type;}
    public void setType(String name){
        this.type = type;
    }

    public String getId(){return id;}
    public void setId(String id){ this.id = id; }

}
