package com.example.reversequestions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Score> mDataset = new ArrayList<Score>();
    private Context ctx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView name;
        public TextView score;
        public ImageView avatar;
        public ConstraintLayout paretLayout;

        public MyViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.name);
            score = v.findViewById(R.id.score);
            avatar = v.findViewById(R.id.avatar);
            paretLayout = v.findViewById(R.id.ranking_item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context ctx, ArrayList<Score>  myDataset) {
        this.ctx = ctx;
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rank, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(mDataset.get(position).getName());
        holder.score.setText("Wynik: "+mDataset.get(position).getScore());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
