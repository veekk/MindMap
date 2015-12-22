package com.example.veek.mindmap.util;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.veek.mindmap.R;
import com.example.veek.mindmap.fragment.MindMapViewFragment;

import java.util.ArrayList;

/**
 * Created by veek on 30.11.15.
 */

public class MindMapAdapter extends RecyclerView.Adapter<MindMapAdapter.MapViewHolder> {

    ArrayList<ListModel> content = new ArrayList<>();
    Context context;
    CustomFragmentManager fragmentManager = CustomFragmentManager.getInstance();

    public MindMapAdapter(Context context){
        this.context = context;
    }


    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(parent.getContext()).inflate(R.layout.mind_list_row, parent, false);
        return new MapViewHolder(content);
    }

    @Override
    public void onBindViewHolder(MapViewHolder holder, int position) {
        final ListModel model = content.get(position);
        holder.tvRow.setText(model.text);
        holder.tvID.setText("ID: " + model.id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.setFragment(new MindMapViewFragment(), false);
            }
        });
    }


    @Override
    public int getItemCount() {
        return content.size();
    }

    public void addRow(String text, long id){
        content.add(new ListModel(text, id));
        notifyItemInserted(content.size() - 1);
    }

    static class MapViewHolder extends RecyclerView.ViewHolder{
        TextView tvRow;
        TextView tvID;
        public MapViewHolder(View itemView) {
            super(itemView);
            tvRow = (TextView) itemView.findViewById(R.id.tvRow);
            tvID = (TextView) itemView.findViewById(R.id.tvID);
        }
    }

    private class ListModel {
        String text;
        long id;

        public ListModel(String text, long id){
            this.text = text;
            this.id = id;
        }
    }
}
