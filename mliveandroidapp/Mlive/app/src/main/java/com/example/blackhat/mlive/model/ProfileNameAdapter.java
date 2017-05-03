package com.example.blackhat.mlive.model;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.activity.Portfolio;
import com.example.blackhat.mlive.activity.ProfileList;
import com.example.blackhat.mlive.pojo.ProfileNamePojo;

import java.util.List;

/**
 * Created by DigitalDesk on 4/16/2017.
 */

public class ProfileNameAdapter extends RecyclerView.Adapter<ProfileNameAdapter.ProfileNameViewHolder>{


    List<ProfileNamePojo> profileList;

    public ProfileNameAdapter(List<ProfileNamePojo> profileList) {
        this.profileList = profileList;
    }

    @Override
    public ProfileNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile_name,parent,false);
        return new ProfileNameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfileNameViewHolder holder, final int position) {

        final ProfileNamePojo profileName=profileList.get(position);

        if(profileName.getDefault()!=null)
        holder.tvProfileName.setTextColor(Color.BLUE);

        holder.tvProfileName.setText(profileName.getProfileName());

        // Remove Profile When Click on Delete Button
      /*  holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Profile Removed="+profileList.get(position).getProfileName());
                profileList.remove(position);
                notifyDataSetChanged();
            }
        });

        // View Script List When Click on Profile Name
        holder.tvProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Selected Profile="+profileList.get(position).getProfileName());

            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class ProfileNameViewHolder extends RecyclerView.ViewHolder
    {
      //  public Button btnDelete,btnDefault;
        public TextView tvProfileName;
        public ProfileNameViewHolder(View itemView) {
            super(itemView);

         //   btnDelete= (Button) itemView.findViewById(R.id.btnDelete);
           // btnDefault= (Button) itemView.findViewById(R.id.btnDefault);
            tvProfileName= (TextView) itemView.findViewById(R.id.tvProfileName);
        }
    }

}
