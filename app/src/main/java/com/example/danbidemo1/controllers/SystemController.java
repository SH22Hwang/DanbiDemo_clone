package com.example.danbidemo1.controllers;

/** 정렬에 관한 메소드를 포함한 클래스..추가 예정 */
public class SystemController {

    public static String addTag(String expert){
        String result="";
        String temp[];
        temp = expert.split(",");
        for(int i = 0 ; i < temp.length ; i++){
            result += "#" + temp[i].trim() + " ";
        }
        return result;
    }
}
