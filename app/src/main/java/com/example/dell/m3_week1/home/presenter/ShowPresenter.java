package com.example.dell.m3_week1.home.presenter;

import com.example.dell.m3_week1.ShowActivity;
import com.example.dell.m3_week1.home.model.Imodel;
import com.example.dell.m3_week1.home.model.ShowModel;

public class ShowPresenter implements Ipresenter
{
    ShowActivity sview;
    private final ShowModel showModel;

    public ShowPresenter(ShowActivity sview) {
        this.sview = sview;
        showModel = new ShowModel();
    }

    @Override
    public void getpdata(int page,int num)
    {
        showModel.getmdata(page,num, new Imodel.ShowCallBack() {
            @Override
            public void getSuccess(Object object) {
                sview.getvdata(object);
            }

            @Override
            public void getFailed() {

            }
        });
    }
}
