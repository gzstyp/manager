package com.fwtai.jdk8;

import java.util.Optional;

/**
 * Optional容器类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-08-19 21:42
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
public class ThisOptional{

    protected void demoOptional1(){
        Optional<User> op = Optional.of(null);
        final User user = op.get();
        System.out.println(user);
    }

    protected void demoOptional2(){
        Optional<User> op = Optional.empty();
        final User user = op.get();
        System.out.println(user);
    }

    public static void main(String[] args){
        new ThisOptional().demoOptional1();
    }
}