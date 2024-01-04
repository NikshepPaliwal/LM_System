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

public class issued_book_adapter extends RecyclerView.Adapter<issued_book_adapter.ViewHolder>{
    ArrayList<model> list;
    Context context;

    issued_book_adapter(Context context,ArrayList<model>list) {

        this.context = context;
        this.list=list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_view_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull issued_book_adapter.ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.book_name.setText(list.get(position).getBook_Title());
        holder.author_name.setText(list.get(position).getAuthor_Name());
        holder.barcode.setText(list.get(position).getBookBarcode());
        holder.date_Time.setText(list.get(position).getDate_Time().substring(0 ,19));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        TextView book_name, author_name, barcode,date_Time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_name=itemView.findViewById(R.id.book_name);
            author_name=itemView.findViewById(R.id.author_name);
            barcode=itemView.findViewById(R.id.barcode);
            date_Time = itemView.findViewById(R.id.date_Time);

        }
    }
}
