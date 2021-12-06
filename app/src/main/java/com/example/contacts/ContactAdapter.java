package com.example.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    Context context;
    ArrayList<ContactModel>arraylist;

    public ContactAdapter(Context context,ArrayList<ContactModel>arraylist){
        this.context=context;
        this.arraylist=arraylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.sample_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name=arraylist.get(position).getName();
        holder.nameTextView.setText(name);
        String n=""+name.charAt(0);
        n=n.toUpperCase();
        holder.imageTextView.setText(n);
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nameTextView,imageTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.sampleTextViewId);
            imageTextView=itemView.findViewById(R.id.sampleImageTextId);
            imageView=itemView.findViewById(R.id.sampleImageViewId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactModel contactModel=arraylist.get(getAdapterPosition());
                    Intent intent=new Intent(context,DetailActivity.class);
                    intent.putExtra("CONTACT",contactModel);
                    context.startActivity(intent);
                    //((Activity)context).finish();
                }
            });
        }
    }
    public void filterList(ArrayList<ContactModel> filteredList){
        arraylist =filteredList;
        notifyDataSetChanged();
    }
}
