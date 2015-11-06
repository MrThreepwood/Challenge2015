package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myriadmobile.Challenge2015.Easter.TiltBallActivity;
import com.squareup.picasso.Picasso;

import Models.*;
import butterknife.Bind;
import butterknife.ButterKnife;


public class QuestDetails extends Fragment {
    @Bind(R.id.quest_name) TextView tvQuestName;
    @Bind(R.id.quest_description) TextView tvQuestDescription;
    @Bind(R.id.giver_name) TextView tvGiverName;
    @Bind(R.id.giver_description) TextView tvGiverDesciption;
    @Bind(R.id.quest_image) ImageView ivQuestImage;
    int clickCount = 0;
    public Models.Character character;
    public String characterId;

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
        tvGiverDesciption.setText("Retrieving bio...");
        if (character != null) {
            tvGiverDesciption.setText(character.getBio());
        }
        characterId = quest.getGiver().getId();
        if(!quest.getImage().isEmpty()) {
            Picasso.with(getActivity()).load(quest.getImage()).into(ivQuestImage);
        }
        ivQuestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCount > 4) {
                    ((ChildManager) getActivity()).startNewActivity(TiltBallActivity.class);
                } else {
                    clickCount ++;
                }
            }
        });
        return v;
    }
    @Override
    public void onResume () {
        super.onResume();
        clickCount = 0;
    }
    public void setCharacter (Models.Character character) {
        this.character = character;
        if (tvGiverDesciption == null) {
            return;
        }
        if (character == null || character.getBio().isEmpty()) {
            tvGiverDesciption.setText("Unfortunately this individual has not provided a bio.");
        } else {
            tvGiverDesciption.setText(character.getBio());
        }
    }


}
