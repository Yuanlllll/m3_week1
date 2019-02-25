package com.example.dell.m3_week1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dell.m3_week1.R;
import com.example.dell.m3_week1.bean.ShopBean;


import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context context;
    //private ShopBean shopBean;
    private List<ShopBean.DataBean> mList = new ArrayList<>();

    public ShopAdapter(Context context) {
        this.context = context;
        //this.shopBean = shopBean;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.shangjia_layout, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopAdapter.ViewHolder viewHolder, final int i)
    {
        viewHolder.text_title.setText(mList.get(i).getSellerName());
        //创建布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(context);
        viewHolder.shop_view.setLayoutManager(manager);
        //创建适配器
        List<ShopBean.DataBean.ListBean> list = mList.get(i).getList();
        final CarAdapter carAdapter = new CarAdapter(context,list);
        viewHolder.shop_view.setAdapter(carAdapter);

        //商家,给复选框设置状态,商品全选中,商家选中
        viewHolder.chec_box.setChecked(mList.get(i).isIscheck());
        carAdapter.setListener(new CarAdapter.ShopCallBack() {
            @Override
            public void callBack() {
                //从商品适配器回调回来
                if (shopCallBack!=null)
                {
                    shopCallBack.callBack(mList);
                }
                List<ShopBean.DataBean.ListBean> listBeans = mList.get(i).getList();
                //创建一个临时的标志位，用来记录现在点击的状态
                boolean isAllChecked = true;
                for (ShopBean.DataBean.ListBean bean : listBeans) {
                    if (!bean.isIscheck()) {
                        //只要有一个商品未选中，商家不进行选中,标志位设置成false，并且跳出循环
                        isAllChecked = false;
                        break;
                    }
                }
                //刷新商家的状态,进行选中/未选中
                viewHolder.chec_box.setChecked(isAllChecked);
                mList.get(i).setIscheck(isAllChecked);
            }
        });

        //监听checkBox的点击事件
        //目的是改变商家下面所有商品的选中状态
        viewHolder.chec_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先改变自己的标志位
                mList.get(i).setIscheck(viewHolder.chec_box.isChecked());
                //调用产品adapter的方法，用来进行商品的全选和反选
                carAdapter.selectorRemoveAll(viewHolder.chec_box.isChecked());
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text_title;
        private final RecyclerView shop_view;
        private final CheckBox chec_box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            shop_view = itemView.findViewById(R.id.shop_view);
            chec_box = itemView.findViewById(R.id.chec_box);
        }
    }

    public void setList(List<ShopBean.DataBean> mlist) {
        this.mList=mlist;
        notifyDataSetChanged();
    }
    private ShopCallBack shopCallBack;
    public void setListener(ShopCallBack shopCallBack) {
        this.shopCallBack = shopCallBack;
    }

    //定义接口
    public interface ShopCallBack
    {
        void callBack(List<ShopBean.DataBean> list);
    }
}
