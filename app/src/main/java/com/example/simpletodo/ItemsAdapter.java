package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    //interface helps code figure out which items to do delete
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    //Constructor needed to fill ViewHolder with items
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    // creates each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        //wrap view into ViewHolder
        return new ViewHolder(todoView);
    }

    @Override
    //finds position of items and binds data into ViewHolder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grabs the item at position
        String item = items.get(position);
        //bind item to ViewHolder
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to access views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvItem;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // update view inside of ViewHolder with item data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }

            });

        }
    }
}
