package com.myriadmobile.Challenge2015;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Models.KingdomBriefModel;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by joshuaswoyer on 10/21/15.
 */
public class KingdomListAdapter extends RecyclerView.Adapter<KingdomListAdapter.KingdomsViewHolder> {
    public List<KingdomBriefModel> kingdoms;

    public KingdomListAdapter(List<KingdomBriefModel> kingdoms) {
        this.kingdoms = kingdoms;
    }

    @Override
    public int getItemCount() {
        return kingdoms.size();
    }

    @Override
    public void onBindViewHolder (KingdomsViewHolder kingdomsViewHolder, int i) {
        KingdomBriefModel kingdom = kingdoms.get(i);
        kingdomsViewHolder.vKingdomName.setText(kingdom.getName());
        //Picasso.with(swapper.getContext()).setLoggingEnabled(true);
        //Picasso.with(swapper.getContext()).setIndicatorsEnabled(true);
        if (kingdom.getImage() != null && !kingdom.getImage().isEmpty()) {
            Picasso.with(kingdomsViewHolder.view.getContext())
                    .load(kingdom.getImage())
                    .fit()
                    .into(kingdomsViewHolder.vImage);
        }
        Log.d("KingdomId", "onBindViewHolder " + kingdom.getId());
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
        @Bind(R.id.kingdom_image) ImageView vImage;
        @Bind(R.id.kingdom_name) TextView vKingdomName;
        protected View view;


        public KingdomsViewHolder (View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kingdomId = (String) view.getTag();
                    Bundle args = new Bundle();
                    args.putString("kingdomId", kingdomId);
                    KingdomQuestPager f = new KingdomQuestPager();
                    f.setArguments(args);
                    ChildManager swapper = (ChildManager) view.getContext();
                    swapper.swapFragments(R.id.fragment_container, f, true);
                }
            });
        }

    }
}
