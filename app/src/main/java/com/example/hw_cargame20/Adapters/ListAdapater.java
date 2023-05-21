package com.example.hw_cargame20.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw_cargame20.Interfaces.CallBack_SendClick;
import com.example.hw_cargame20.Interfaces.OnItemClickListener;
import com.example.hw_cargame20.Models.List;
import com.example.hw_cargame20.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ListAdapater extends RecyclerView.Adapter<ListAdapater.ListViewHolder> {

    private OnItemClickListener OnItemClickListener;
    private Context context;

    private ArrayList<List> list;

    public ListAdapater(Context context, ArrayList<List> list , OnItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        OnItemClickListener = itemClickListener;

    }

    private CallBack_SendClick callBack_sendClick;

    public void setCallBack_sendClick(CallBack_SendClick callBack_sendClick) {
        this.callBack_sendClick = callBack_sendClick;


    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view, OnItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapater.ListViewHolder holder, int position) {
        List list = getItem(position);
        holder.list_TXT_name.setText(list.getName());
        holder.list_TXT_score.setText(list.getScore() + "");
        holder.list_TXT_position.setText(list.getPosition() + "");

    }

    @Override
    public int getItemCount() {
        if(this.list == null)
            return 0;
        else if(list.size() < 10)
            return list.size();
        else
           return 10;

//        return this.list == null ? 0 : this.list.size();
    }

    private List getItem(int position) {
        return this.list.get(position);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener itemClickListener;
        MaterialTextView list_TXT_name;
        MaterialTextView list_TXT_position;
        MaterialTextView list_TXT_score;


        public ListViewHolder(@NonNull View itemView, OnItemClickListener clickListener) {
            super(itemView);
            list_TXT_name = itemView.findViewById(R.id.list_TXT_name);
            list_TXT_position = itemView.findViewById(R.id.list_TXT_position);
            list_TXT_score = itemView.findViewById(R.id.list_TXT_score);
            itemClickListener = clickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAbsoluteAdapterPosition());

        }
    }


}

