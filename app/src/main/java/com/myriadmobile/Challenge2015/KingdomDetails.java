package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Models.KingdomDetailedModel;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class KingdomDetails extends Fragment {
    @Bind(R.id.kingdom_image) ImageView ivKingdomImage;
    @Bind(R.id.kingdom_name) TextView tvKingdomName;
    @Bind(R.id.climate) TextView tvClimate;
    @Bind(R.id.population) TextView tvPopulation;
    @Bind(R.id.language) TextView tvLanguage;
    KingdomDetailedModel kingdom;

    public KingdomDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kingdom_details, container, false);
        ButterKnife.bind(this, v);
        kingdom = (KingdomDetailedModel) getArguments().getSerializable("kingdom");
        if (kingdom != null) {
            if (kingdom.getImage() != null && !kingdom.getImage().isEmpty())
                Picasso.with(getActivity()).load(kingdom.getImage()).into(ivKingdomImage);
            tvKingdomName.setText(kingdom.getName());
            tvClimate.setText(kingdom.getClimate());
            tvPopulation.setText(kingdom.getPopulation());
            tvLanguage.setText("no launguage field in database.");
        }
        return v;

    }


}
