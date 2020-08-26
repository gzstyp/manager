package com.fwtai.jdk8;

/**
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-08-19 21:44
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
public final class User{

    private Integer id = 1024;

    private String name = "Typ";

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return "User{" + "id=" + id + ", name=" + name + "}";
    }
}