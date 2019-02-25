package com.example.dell.m3_week1.home.model;

public interface Imodel
{
    void getmdata(int page,int num,ShowCallBack showCallBack);
    interface ShowCallBack
    {
        void getSuccess(Object object);
        void getFailed();
    }


}
