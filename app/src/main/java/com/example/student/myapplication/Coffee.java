package com.example.student.myapplication;

import java.io.Serializable;

public class Coffee implements Serializable{
    private String title;
    private int price;
    private int img_resource_id;

    public Coffee (String title,int price,int img_resource_id){
        this.title = title;
        this.price = price;
        this.img_resource_id = img_resource_id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public int getPrice(){
        return price;
    }
    public void setprice(int price){
        this.price = price;
    }
    public int getImg_resource_id(){
        return img_resource_id;
    }
    public void setImg_resource_id(int img_resource_id){
        this.img_resource_id = img_resource_id;
    }
    @Override
    public String toString(){
        return "Coffee{" +
                "title="+ title + '\'' +
                ",price="+ price +
                ",img_resource_id=" + img_resource_id +
                '}';
    }
}
