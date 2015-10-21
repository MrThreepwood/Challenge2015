package com.myriadmobile.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import API.ChallengeAPI;
import Models.KingdomModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class KingdomsFragment extends Fragment {
    List<KingdomModel> kingdoms = new ArrayList<KingdomModel>();
    KingdomsAdapter adapter;
    @Bind(R.id.cardList) RecyclerView recyclerView;
    @Bind(R.id.empty_text) TextView tvEmpty;

    public static KingdomsFragment newInstance(String param1, String param2) {
        KingdomsFragment fragment = new KingdomsFragment();
        return fragment;
    }

    public KingdomsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDummyKingdoms();
        getKingdoms();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kingdoms, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new KingdomsAdapter(kingdoms, getActivity());
        recyclerView.setAdapter(adapter);
        if (!kingdoms.isEmpty())
            tvEmpty.setVisibility(View.GONE);
        return view;
    }

    private void createDummyKingdoms() {
        for (int i = 0; i < 12; i++) {
            kingdoms.add(new KingdomModel(Integer.toString(i), "Kingdom " + i, null));
        }
    }
    private void getKingdoms() {
        MyApplication application = (MyApplication) getActivity().getApplication();
        ChallengeAPI api = application.getApiInstance();
        api.getKingdoms(new Callback<List<KingdomModel>>() {
            @Override
            public void success(List<KingdomModel> kingdomsResponse, Response response) {
                if (kingdomsResponse != null && !kingdomsResponse.isEmpty()) {
                    kingdoms = kingdomsResponse;
                    adapter.kingdoms = kingdoms;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GetKingdomsFailure", "failure code " + error.getResponse().getStatus());
            }
        });
    }
}
