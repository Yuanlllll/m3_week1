package com.example.dell.m3_week1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.dell.m3_week1.adapter.ShowAdapter;
import com.example.dell.m3_week1.bean.ShowBean;
import com.example.dell.m3_week1.home.presenter.ShowPresenter;
import com.example.dell.m3_week1.home.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity implements Iview, View.OnClickListener {

    @BindView(R.id.xrecy_view)
    XRecyclerView xrecyView;
    @BindView(R.id.img_ce)
    ImageView imgCe;
    @BindView(R.id.draw_layout)
    DrawerLayout drawLayout;
    @BindView(R.id.draw_con)
    LinearLayout drawCon;
    @BindView(R.id.list_view)
    ListView listView;
    private ShowPresenter showPresenter;
    private ShowBean showBean;
    int page = 1;
    int num = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        showPresenter = new ShowPresenter(this);
        showPresenter.getpdata(page, num);
        imgCe.setOnClickListener(this);
        xrecyView.setPullRefreshEnabled(true);
        xrecyView.setLoadingMoreEnabled(true);
        xrecyView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        showPresenter.getpdata(page, num);
                        xrecyView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        showPresenter.getpdata(page, num);
                        xrecyView.loadMoreComplete();
                    }
                }, 2000);

            }
        });

        getch();

    }

    private void getch() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("首頁");
        arrayList.add("购物车");
        listView.setAdapter(new ArrayAdapter<String>(ShowActivity.this, android.R.layout.simple_list_item_1, arrayList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(ShowActivity.this,ShopActivity.class));
                    finish();

            }
        });
    }

    @Override
    public void getvdata(Object object) {
        if (object != null) {
            showBean = (ShowBean) object;
        }
        Log.d("hh", "getvdata" + showBean.getMsg());
        //创建布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        xrecyView.setLayoutManager(manager);
        //创建适配器
        List<ShowBean.NewslistBean> list = showBean.getNewslist();
        ShowAdapter showAdapter = new ShowAdapter(ShowActivity.this, list);
        xrecyView.setAdapter(showAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ce:
                drawLayout.openDrawer(drawCon);
                break;
        }
    }
}
