package com.myriadmobile.Challenge2015;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import API.ChallengeAPI;
import Models.KingdomBriefModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class KingdomListFragment extends Fragment {
    List<KingdomBriefModel> kingdoms = new ArrayList<KingdomBriefModel>();
    KingdomListAdapter adapter;
    @Bind(R.id.cardList) RecyclerView recyclerView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.empty_text) TextView tvEmpty;
    @Bind(R.id.swiper) SwipeRefreshLayout swiper;

    public static KingdomListFragment newInstance(String param1, String param2) {
        KingdomListFragment fragment = new KingdomListFragment();
        return fragment;
    }

    public KingdomListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getKingdoms();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kingdom_list, container, false);
        ButterKnife.bind(this, view);
        tvEmpty.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new KingdomListAdapter(kingdoms);
        recyclerView.setAdapter(adapter);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getKingdoms();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvEmpty.setVisibility(View.GONE);
        if (kingdoms != null) {
            progressBar.setVisibility(View.GONE);
            return;
        }
        getKingdoms();
    }

    private void getKingdoms() {
        MyApplication application = (MyApplication) getActivity().getApplication();
        ChallengeAPI api = application.getApiInstance();
        api.getKingdoms(new Callback<List<KingdomBriefModel>>() {
            @Override
            public void success(List<KingdomBriefModel> kingdomsResponse, Response response) {
                if (kingdomsResponse != null && !kingdomsResponse.isEmpty()) {
                    tvEmpty.setVisibility(View.GONE);
                    kingdoms = kingdomsResponse;
                    adapter.kingdoms = kingdoms;
                    adapter.notifyDataSetChanged();
                    swiper.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                } else {
                    noData();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                swiper.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                String errorMessage = "";
                if (error != null) {
                    errorMessage = error.getMessage();
                }
                tvEmpty.setText(errorMessage + " Pull to try again.");
                tvEmpty.setVisibility(View.VISIBLE);
            }
            public void noData() {
                swiper.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                tvEmpty.setText("Sorry, no kingdoms were found. Pull to look again.");
            }
        });
    }
}
