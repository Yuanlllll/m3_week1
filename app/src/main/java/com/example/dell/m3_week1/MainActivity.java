package com.example.dell.m3_week1;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_tiao)
    TextView textTiao;
    @BindView(R.id.img_view)
    ImageView imgView;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDisposable = Flowable.intervalRange(0, 6, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        textTiao.setText(String.valueOf(5 - aLong) + "s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        startActivity(new Intent(MainActivity.this,ShowActivity.class));
                        finish();

                    }
                }).subscribe();

        //动画效果
        ObjectAnimator py = ObjectAnimator.ofFloat(imgView, "translationY", imgView.getTranslationY(), 800);
        ObjectAnimator rotation= ObjectAnimator.ofFloat(imgView,"rotation",0,360);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imgView,"alpha",0,1,0,1);
        ObjectAnimator xz = ObjectAnimator.ofFloat(imgView, "scaleX", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(py).with(rotation).with(alpha).with(xz);
        //设置动画时间
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
