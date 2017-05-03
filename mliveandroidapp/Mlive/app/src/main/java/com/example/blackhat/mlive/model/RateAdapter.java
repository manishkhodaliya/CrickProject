package com.example.blackhat.mlive.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.activity.MainActivity;
import com.example.blackhat.mlive.pojo.MkW;

import java.util.List;

/**
 * Created by SonyVaio on 3/13/2017.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.MyViewHolder> {

    private List<MkW> rateList;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_script_list,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //Change Text Size by change event of seek bar in MainActivity.class
        holder.scriptName.setTextSize(MainActivity.size_of_items);
        holder.rate1.setTextSize(MainActivity.size_of_items);
        holder.rate2.setTextSize(MainActivity.size_of_items);
        holder.rate3.setTextSize(MainActivity.size_of_items);
        holder.rate4.setTextSize(MainActivity.size_of_items);
        holder.rate5.setTextSize(MainActivity.size_of_items);
        holder.rate6.setTextSize(MainActivity.size_of_items);

        MkW rateModel = rateList.get(position);

        holder.scriptName.setText(rateModel.getSn());
        holder.rate1.setText(rateModel.getD1());
        holder.rate2.setText(rateModel.getD2());
        holder.rate3.setText(rateModel.getD3());
        holder.rate4.setText(rateModel.getD4());
        holder.rate5.setText(rateModel.getD5());
        holder.rate6.setText(rateModel.getD6());
        if(rateModel.getN()!=null)
        {
        Float newF=Float.parseFloat(rateModel.getN());
        if(newF>0){
            holder.imageArrow.setBackgroundResource(R.mipmap.blue_arrow);

        }else{
            holder.imageArrow.setBackgroundResource(R.mipmap.red_arrow);
        }}
   }

    @Override
    public int getItemCount() {

        //System.out.println("Get Item Count="+rateList.size());

        return rateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageArrow;
        public TextView scriptName,rate1,rate2,rate3,rate4,rate5,rate6;
        public MyViewHolder(View itemView) {
            super(itemView);

            scriptName= (TextView) itemView.findViewById(R.id.txtScript);
            rate1= (TextView) itemView.findViewById(R.id.txtRate1);
            rate2= (TextView) itemView.findViewById(R.id.txtRate2);
            rate3= (TextView) itemView.findViewById(R.id.txtRate3);
            rate4= (TextView) itemView.findViewById(R.id.txtRate4);
            rate5= (TextView) itemView.findViewById(R.id.txtRate5);
            rate6= (TextView) itemView.findViewById(R.id.txtRate6);
            imageArrow= (ImageView) itemView.findViewById(R.id.imgArrow);

        }



    }

    public RateAdapter(List<MkW> rateList){

        this.rateList = rateList;

        System.out.println("ratelist="+rateList);
    }


}
