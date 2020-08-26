package com.fwtai.Learn;

import java.util.HashMap;
import java.util.Map;

/**
 * idea Debug测试例子
 * F7是进入项目的源码
 * Alt + Shift + F7 进入jar源码,依赖源码
 * F8下一行代码
 * 指定某个异常类型时就开启debug断点调试功能
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-06-25 15:45
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
public final class AppDebug{

    public static void main(String[] args){
        int a = 10;
        HashMap<String,Integer> b = new HashMap<>();
        b.put("a",1);
        b.put("b",2);
        b.put("c",3);
        b.put("d",4);
        b.put("r",5);

        if(a > 5){
            Car car = new Car();
            //F7是进入项目的源码
            car.drive();
            //ALT + Shift + F7 进入jar源码,依赖源码
            System.out.println("yes");
        }

        //bebug过程中修改变量的值
        if(a<5){
            System.out.println("no");
        }

        for(Map.Entry<String,Integer> map : b.entrySet()){
            final String key = map.getKey();
            if(key.equals("a")){
                System.out.println(key+"-->"+map.getValue());
                break;
            }
        }
        b = null;
        System.out.println(b.size());
    }

   static class Car{
        protected void drive(){
            System.out.println("开车");
        }
    }
}