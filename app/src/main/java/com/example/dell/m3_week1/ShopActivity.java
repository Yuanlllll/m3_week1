package com.example.dell.m3_week1;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.m3_week1.adapter.ShopAdapter;
import com.example.dell.m3_week1.bean.CarBean;
import com.example.dell.m3_week1.bean.ShopBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shop_view;
    private List<ShopBean.DataBean> list=new ArrayList<>();
    private CheckBox iv_cricle;
    private ShopBean shopBean;
    private ShopAdapter shopAdapter;
    private TextView total_num;
    private TextView total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        shop_view = findViewById(R.id.shop_view);
        iv_cricle = findViewById(R.id.iv_cricle);
        total_num = findViewById(R.id.total_num);
        total_price = findViewById(R.id.total_price);
        getData();
        shopAdapter.setListener(new ShopAdapter.ShopCallBack() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                double zj=0;
                //勾选商品数量,不是商品的购买数量
                int num=0;
                int totalNum=0;
                for (int i=0;i<list.size();i++)
                {
                    //获取商家里面的商品
                    List<ShopBean.DataBean.ListBean> listAll = list.get(i).getList();
                    for (int j=0;j<listAll.size();j++)
                    {
                        totalNum=totalNum+Integer.parseInt(listAll.get(j).getNum());
                        //取选中状态
                        if (listAll.get(j).isIscheck())
                        {
                            zj=zj+(listAll.get(j).getPrice()*Integer.parseInt(listAll.get(j).getNum()));

                            num+=Integer.parseInt(listAll.get(j).getNum());

                        }
                    }
                }
                if (num<totalNum)
                {
                    //不是全部选中
                    iv_cricle.setChecked(false);
                }
                else
                {
                    //全部选中
                    iv_cricle.setChecked(true);
                }
                total_price.setText("合计:"+zj);
                total_num.setText("去结算("+num+")");
            }
        });

        iv_cricle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSeller(iv_cricle.isChecked());
                shopAdapter.notifyDataSetChanged();
            }
        });

    }



    private void getData()
    {
        try {
            InputStream inputStream = getAssets().open("cart.json");
            String json=streamToJson(inputStream);
            //Log.d("hh","getData"+json);
            Gson gson = new Gson();
            ShopBean shopBean = gson.fromJson(json, ShopBean.class);
            List<ShopBean.DataBean> list = shopBean.getData();
            LinearLayoutManager manager = new LinearLayoutManager(this);
            shop_view.setLayoutManager(manager);
            //创建适配器
            shopAdapter = new ShopAdapter(this);
            shop_view.setAdapter(shopAdapter);
            if (list!=null)
            {
                list.remove(0);
                shopAdapter.setList(list);
            }

       } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //修改选中状态,获取价格和数量
    private void checkSeller(boolean b)
    {
        double zj=0;
        int num=0;
        for (int j=0;j<list.size();j++)
        {
            ShopBean.DataBean dataBean = list.get(j);
            dataBean.setIscheck(b);

            List<ShopBean.DataBean.ListBean> listBeans = list.get(j).getList();
            for (int i=0;i<listBeans.size();i++)
            {
                listBeans.get(i).setIscheck(b);
                zj=zj+((Integer.parseInt(listBeans.get(i).getNum()))*listBeans.get(i).getPrice());
                //zj=zj+(Integer.parseInt(listBeans.get(i).getNum()))*Double.valueOf(listBeans.get(i).getPrice());
                num=num+Integer.parseInt(listBeans.get(i).getNum());

            }
        }

        if (b)
        {
            total_price.setText("合计:"+zj);
            total_num.setText("去结算(" + num + ")");
        }
        else
        {
            total_price.setText("合计:0.0");
            total_num.setText("去结算(0)");
        }

    }

    //解析数据
    private String streamToJson(InputStream inputStream) {
        try {
            InputStreamReader ins=new InputStreamReader(inputStream,"gbk");
            BufferedReader reader=new BufferedReader(ins);
            StringBuilder builder=new StringBuilder();
            String str="";
            while((str=reader.readLine())!=null)
            {
                builder.append(str);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
