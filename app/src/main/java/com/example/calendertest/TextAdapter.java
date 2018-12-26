package com.example.calendertest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.Viewholder>  {
    private List<String> mTextList;
    static class Viewholder extends RecyclerView.ViewHolder{
        TextView textContent;
        public Viewholder(View view){
            super(view);
            textContent=(TextView)view.findViewById(R.id.textOne);
        }
    }
    public TextAdapter(List<String> a){
        mTextList=a;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.textpad_item,parent,false);
        Viewholder holder=new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String te=mTextList.get(position);
        holder.textContent.setText(te);
    }

    @Override
    public int getItemCount() {
        return mTextList.size();
    }


}
