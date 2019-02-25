package com.example.dell.m3_week1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.m3_week1.R;
import com.example.dell.m3_week1.bean.ShopBean;


import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private Context context;
    private List<ShopBean.DataBean.ListBean> slist=new ArrayList<>();

    public CarAdapter(Context context, List<ShopBean.DataBean.ListBean> slist) {
        this.context = context;
        this.slist = slist;
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shangpin_layout, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder viewHolder, final int i)
    {
        viewHolder.car_title.setText(slist.get(i).getTitle());
        viewHolder.car_price.setText("¥"+slist.get(i).getPrice()+"");
        Glide.with(context).load(slist.get(i).getImages().split("\\|")[0].replace("https","http")).into(viewHolder.car_img);


        //商品
        //根据记录状态,改变勾选,设置复选框状态
        viewHolder.car_checbox.setChecked(slist.get(i).isIscheck());
        //商品跟商家不同,商品的选中改变监听
        viewHolder.car_checbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //先改变自己的状态
                slist.get(i).setIscheck(isChecked);
                //回调 ,到activity,选中状态被改变
                if (shopCallBack!=null)
                {
                    shopCallBack.callBack();
                }
            }
        });

    }


   //点击商家,商品进行全选反选,
    // 修改子商品的全选和反选
    public void selectorRemoveAll(boolean ischecked)
    {
        for (ShopBean.DataBean.ListBean listBean:slist)
        {
            listBean.setIscheck(ischecked);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return slist.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox car_checbox;
        private final ImageView car_img;
        private final TextView car_title;
        private final TextView car_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            car_img = itemView.findViewById(R.id.car_img);
            car_checbox = itemView.findViewById(R.id.car_checbox);
            car_title = itemView.findViewById(R.id.car_title);
            car_price = itemView.findViewById(R.id.car_price);
        }
    }

    private ShopCallBack shopCallBack;
    public void setListener(ShopCallBack shopCallBack) {
        this.shopCallBack = shopCallBack;
    }

    //定义接口
    public interface ShopCallBack
    {
        void callBack();
    }
}
