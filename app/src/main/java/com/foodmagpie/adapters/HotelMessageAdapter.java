package com.foodmagpie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodmagpie.R;
import com.foodmagpie.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class HotelMessageAdapter extends RecyclerView.Adapter<HotelMessageAdapter.MessagesViewHolder> {

    private final Context context;

    public HotelMessageAdapter(List<Chat> msg, String from, Context context){
        this.from=from;
        this.context=context;
        this.msg=msg;
    }
    List<Chat> msg=new ArrayList<>();
    String from;
    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =null;
        if(viewType==1){
            itemView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.messagesview, parent, false);
            return new MessagesViewHolder(itemView);
        }else{
            itemView=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msgview, parent, false);
            return new MessagesViewHolder(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(msg.get(position).getFrm().equals(from)){
            return 1;
        }
        else{
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Chat m=msg.get(position);

        holder.msgfield.setText(m.getMessage());
    }

    public void data(List<Chat> msg){
        this.msg=msg;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    class MessagesViewHolder extends  RecyclerView.ViewHolder{
        TextView msgfield;
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            msgfield=itemView.findViewById(R.id.msgs);
        }

    }


}
