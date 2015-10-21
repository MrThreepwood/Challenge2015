package com.myriadmobile.myapplication;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Models.KingdomModel;

/**
 * Created by joshuaswoyer on 10/21/15.
 */
public class KingdomsAdapter extends RecyclerView.Adapter<KingdomsAdapter.KingdomsViewHolder> {
    public List<KingdomModel> kingdoms;
    static FragmentSwapper swapper;

    public KingdomsAdapter (List<KingdomModel> kingdoms, FragmentSwapper swapper) {
        this.kingdoms = kingdoms;
        this.swapper = swapper;
    }

    @Override
    public int getItemCount() {
        return kingdoms.size();
    }

    @Override
    public void onBindViewHolder (KingdomsViewHolder kingdomsViewHolder, int i) {
        KingdomModel kingdom = kingdoms.get(i);
        kingdomsViewHolder.vKingdomName.setText(kingdom.getName());
        Picasso.with(swapper.getContext()).setLoggingEnabled(true);
        Picasso.with(swapper.getContext()).setIndicatorsEnabled(true);
        if (kingdom.getImage() != null && !kingdom.getImage().isEmpty())
            Picasso.with(swapper.getContext())
                .load(kingdom.getImage())
                .fit()
                .into(kingdomsViewHolder.vImage);
        kingdomsViewHolder.view.setTag(kingdom.getId());
    }

    @Override
    public KingdomsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_kingdom, viewGroup, false);

        return new KingdomsViewHolder(itemView);
    }
    public static class KingdomsViewHolder extends RecyclerView.ViewHolder {
        //TODO: Figure out if I can use butterknife here.
        protected ImageView vImage;
        protected TextView vKingdomName;
        protected View view;


        public KingdomsViewHolder (View view) {
            super(view);
            this.view = view;
            vImage = (ImageView) view.findViewById(R.id.kingdom_image);
            vKingdomName = (TextView) view.findViewById(R.id.kingdom_name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kingdomId = (String) view.getTag();
                    Bundle args = new Bundle();
                    args.putString("kingdomId", kingdomId);
                    KingdomPager f = new KingdomPager();
                    f.setArguments(args);
                    swapper.swapFragments(R.id.main_container, f);
                }
            });
        }
        public void onClickListener () {

        }

    }
}
