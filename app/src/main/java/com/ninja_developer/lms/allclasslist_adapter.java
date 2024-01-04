package com.ninja_developer.lms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class allclasslist_adapter extends RecyclerView.Adapter<allclasslist_adapter.ViewHolder> {


    ArrayList<class_model> list;
    Context context;

    allclasslist_adapter(Context context,ArrayList<class_model> list) {

        this.context = context;
        this.list=list;
    }



    @NonNull
    @Override
    public allclasslist_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.class_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull allclasslist_adapter.ViewHolder holder,@SuppressLint("RecyclerView") int position) {

        holder.class_Name.setText(String.valueOf(list.get(position).getRoll()));
        System.out.println(list.get(position).getRoll());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        TextView class_Name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           class_Name = itemView.findViewById(R.id.class_name);
        }
    }
}
