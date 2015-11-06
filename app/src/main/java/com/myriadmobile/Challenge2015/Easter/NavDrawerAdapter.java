package com.myriadmobile.Challenge2015.Easter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myriadmobile.Challenge2015.R;

/**
 * Created by joshuaswoyer on 11/4/15.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter {
    String[] strings;
    public NavDrawerAdapter (String[] strings) {
        this.strings = strings;
    }
    @Override
    public StringHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        return new StringHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StringHolder mHolder = (StringHolder) holder;
        mHolder.tvString.setText(strings[position]);
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }

    public static class StringHolder extends RecyclerView.ViewHolder {
        TextView tvString;
        View view;
        public StringHolder (View view) {
            super(view);
            this.view = view;
            tvString = (TextView) view.findViewById(R.id.string_text);
        }
    }
}
