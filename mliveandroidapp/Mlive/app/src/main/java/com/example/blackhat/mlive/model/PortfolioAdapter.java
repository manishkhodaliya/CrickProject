package com.example.blackhat.mlive.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.pojo.Portfolio;

import java.util.List;

/**
 * Created by DigitalDesk on 4/14/2017.
 */

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.MyViewHolder> {

    private List<Portfolio> list;

    public PortfolioAdapter(List<Portfolio> list) {

        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_show_script, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.txtScriptName.setText(list.get(position).getScriptname());


    }

    @Override
    public int getItemCount() {

        //System.out.println("Get Item Count="+rateList.size());
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtScriptName;
        public CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtScriptName = (TextView) itemView.findViewById(R.id.txtScriptName);
            checkBox= (CheckBox) itemView.findViewById(R.id.scriptCheckbox);
        }


    }


}




