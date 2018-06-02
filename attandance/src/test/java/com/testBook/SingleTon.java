package com.testBook;

import javafx.beans.binding.ObjectExpression;

import java.util.*;

public class SingleTon {

    public static void m1(){
        Map map = new HashMap();
        map.put(1,"张三");
        map.put(2,"李四");
        map.put(3,"王五");
        List<Object> list = new ArrayList<>();
        /*Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            list.add(obj);
            System.out.println();
        }
        for(Object o:list){
            System.out.println(o);
        }*/

        for(Object o:map.keySet()){
            System.out.println(o);
        }
    }

    public  static  void  main(String  args[ ]){

        System.out.println(1+'a'+"a");
    }

    // 比较字符串
    public static void m2(String str1,String str2){
        char[] byte1 = str1.toCharArray();
        char[] byte2 = str2.toCharArray();
        Arrays.sort(byte1);
        Arrays.sort(byte2);
        str1 = new String(byte1);
        str2 = new String(byte2);
        if(str1.equals(str2)){
            System.out.println("equal");
        }else{
            System.out.println("not equal");
        }
    }

    public static String m3(String str){
        StringBuffer stringBuffer = new StringBuffer();
        char first = str.charAt(0);
        int j=1;
        for(int i=1;i<str.length();i++){
            System.out.println(str.charAt(i));
            if(str.charAt(i) == first){
                j++;
            }else{
                stringBuffer.append(first);
                stringBuffer.append(j);
                first = str.charAt(i);
                j=1;
            }
        }
        stringBuffer.append(first);
        stringBuffer.append(j);
        return String.valueOf(stringBuffer);
    }

}
