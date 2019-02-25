package com.example.dell.m3_week1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.m3_week1.R;
import com.example.dell.m3_week1.bean.ShowBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import retrofit2.http.GET;

public class ShowAdapter extends XRecyclerView.Adapter
{
    private final int ONE=0;
    private final int TWO=1;
    private Context context;
    private List<ShowBean.NewslistBean> list;

    public ShowAdapter(Context context, List<ShowBean.NewslistBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position)
    {
        if (position==ONE)
        {
            View view = View.inflate(context, R.layout.s1_layout, null);
            return new ItemHolder1(view);
        }
        else
        {
            View view = View.inflate(context, R.layout.s2_layout, null);
            return new ItemHolder2(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ItemHolder1)
        {
            ((ItemHolder1) holder).sim_view.setImageURI(list.get(position).getPicUrl());
            ((ItemHolder1) holder).text_title.setText(list.get(position).getTitle());
        }
        if (holder instanceof ItemHolder2)
        {
            ((ItemHolder2) holder).sim_view2.setImageURI(list.get(position).getPicUrl());
            ((ItemHolder2) holder).text_title2.setText(list.get(position).getTitle());
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position%2)
        {
            case 0:
                return ONE;
        }
        return TWO;
    }
    class ItemHolder1 extends XRecyclerView.ViewHolder {

        private final SimpleDraweeView sim_view;
        private final TextView text_title;

        public ItemHolder1(View itemView) {
            super(itemView);
            sim_view = itemView.findViewById(R.id.sim_view);
            text_title = itemView.findViewById(R.id.text_title);

        }
    }
    class ItemHolder2 extends XRecyclerView.ViewHolder {
        private final SimpleDraweeView sim_view2;
        private final TextView text_title2;
        public ItemHolder2(View itemView) {
            super(itemView);
            sim_view2 = itemView.findViewById(R.id.sim_view2);
            text_title2 = itemView.findViewById(R.id.text_title2);
        }
    }
}
