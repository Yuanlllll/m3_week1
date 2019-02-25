package com.example.dell.m3_week1.home.model;

import com.example.dell.m3_week1.api.UserApi;
import com.example.dell.m3_week1.bean.ShowBean;
import com.example.dell.m3_week1.net.RetrofitUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ShowModel implements Imodel
{

    private UserApi userApi;

    @Override
    public void getmdata(int page,int num, final ShowCallBack showCallBack)
    {
        userApi = RetrofitUtils.getInstance().create(UserApi.class);
        userApi.getZs(page,num)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<ShowBean>() {
                    @Override
                    public void onNext(ShowBean value) {
                        showCallBack.getSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
