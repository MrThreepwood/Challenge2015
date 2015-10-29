package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Models.Quest;
import butterknife.Bind;
import butterknife.ButterKnife;


public class QuestDetails extends Fragment {
    @Bind(R.id.quest_name) TextView tvQuestName;
    @Bind(R.id.quest_description) TextView tvQuestDescription;
    @Bind(R.id.giver_name) TextView tvGiverName;
    @Bind(R.id.giver_description) TextView tvGiverDesciption;
    @Bind(R.id.quest_image) ImageView ivQuestImage;

    public QuestDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quest_details, container, false);
        ButterKnife.bind(this, v);
        Quest quest = (Quest) getArguments().getSerializable("quest");
        tvQuestName.setText(quest.getName());
        tvQuestDescription.setText(quest.getDescription());
        tvGiverName.setText(quest.getGiver().getName());
        tvGiverDesciption.setText("Not available at this time");
        if(!quest.getImage().isEmpty())
            Picasso.with(getActivity()).load(quest.getImage()).into(ivQuestImage);
        return v;
    }


}
