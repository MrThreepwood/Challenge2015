package com.myriadmobile.Challenge2015;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import API.ChallengeAPI;
import Models.KingdomDetailedModel;
import Models.Quest;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class KingdomPager extends Fragment {
    String kingdomId;
    KingdomDetailedModel kingdomDetails;
    ChallengeAPI api;
    DetailAdapter detailAdapter;
    List<Fragment> fragments;
    @Bind(R.id.pager) ViewPager pager;


    public KingdomPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kingdomId = getArguments().getString("kingdomId");
        kingdomDetails = getKingdomFromAPI();
        View v = inflater.inflate(R.layout.fragment_kingdom_pager, container, false);
        ButterKnife.bind(this, v);
        return v;
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

    private KingdomDetailedModel getKingdomFromAPI() {
        if (kingdomId != null) {
            api = ((MyApplication)getActivity().getApplication()).getApiInstance();
        }
        api.getKingdomDetails(kingdomId, new Callback<KingdomDetailedModel>() {
            @Override
            public void success(KingdomDetailedModel kingdomDetailedModel, Response response) {
                if(kingdomDetailedModel != null) {
                    kingdomDetails = kingdomDetailedModel;
                    fragments = getFragments();
                    detailAdapter = new DetailAdapter(getChildFragmentManager(),fragments);
                    pager.setAdapter(detailAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return kingdomDetails;
    }


}
