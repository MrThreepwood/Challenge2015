package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import API.ChallengeAPI;
import Models.*;
import Models.Character;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class KingdomQuestPager extends Fragment {
    String kingdomId;
    KingdomDetailedModel kingdomDetails;
    ChallengeAPI api;
    KingdomDetailsPager detailAdapter;
    List<Fragment> fragments;
    @Bind(R.id.pager) ViewPager pager;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.empty_text) TextView tvEmptyText;


    public KingdomQuestPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.pager_kingdom_quest, container, false);
        ButterKnife.bind(this, v);
        tvEmptyText.setVisibility(View.GONE);
        kingdomId = getArguments().getString("kingdomId");
        getKingdomFromAPI();
        return v;
    }

    private void getKingdomFromAPI() {
        api = ((MyApplication) getActivity().getApplication()).getApiInstance();
        api.getKingdomDetails(kingdomId, new Callback<KingdomDetailedModel>() {
            @Override
            public void success(KingdomDetailedModel kingdomDetailedModel, Response response) {
                if (kingdomDetailedModel != null) {
                    kingdomDetails = kingdomDetailedModel;
                    fragments = getFragments();
                    detailAdapter = new KingdomDetailsPager(getChildFragmentManager(), fragments);
                    pager.setAdapter(detailAdapter);
                    progressBar.setVisibility(View.GONE);
                    getCharacterFromAPI();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                String errorMessage = "";
                if (error != null) {
                    errorMessage = error.getMessage();
                }
                tvEmptyText.setText(errorMessage + " Please try again later.");
                tvEmptyText.setVisibility(View.VISIBLE);
            }
        });
    }
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        Bundle kingdomArgs = new Bundle();
        kingdomArgs.putSerializable("kingdom", kingdomDetails);
        KingdomDetails detailFrag = new KingdomDetails();
        detailFrag.setArguments(kingdomArgs);
        fragments.add(detailFrag);
        for (Quest quest: kingdomDetails.getQuests()) {
            Bundle questArgs = new Bundle();
            questArgs.putSerializable("quest", quest);
            QuestDetails questFrag = new QuestDetails();
            questFrag.setArguments(questArgs);
            fragments.add(questFrag);
        }
        return fragments;
    }
    private void getCharacterFromAPI() {
        for (Quest quest: kingdomDetails.getQuests()) {
            if (quest.getGiver().getId() != null && !quest.getGiver().getId().isEmpty()) {
                api = ((MyApplication)getActivity().getApplication()).getApiInstance();
                api.getCharacter(quest.getGiver().getId(), new Callback<Models.Character>() {
                    @Override
                    public void success(Character character, Response response) {
                        for (Fragment fragment: fragments) {
                            if (fragment instanceof QuestDetails) {
                                QuestDetails questDetails = (QuestDetails) fragment;
                                String characterId = questDetails.characterId;
                                if (characterId == null) {
                                    Quest fragQuest = (Quest) questDetails.getArguments().getSerializable("quest");
                                    characterId = fragQuest.getGiver().getId();
                                }
                                if (characterId == character.getId() && questDetails.character == null) {
                                    questDetails.setCharacter(character);
                                }
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        }
    }


}
