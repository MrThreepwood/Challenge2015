package com.myriadmobile.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import API.ChallengeAPI;
import Models.MessageModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginFragment extends Fragment {
    @Bind(R.id.name) EditText name;
    @Bind(R.id.email) EditText email;
    @Bind(R.id.submit) Button submit;

    private FragmentSwapper mListener;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentSwapper) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
    }

    public void validate(){
        String nameText = name.getText().toString();
        String emailText = email.getText().toString();
        if (nameText != null && !nameText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            MyApplication application = (MyApplication) getActivity().getApplication();
            ChallengeAPI api = application.getApiInstance();
            api.logIn(emailText, new Callback<MessageModel>() {
                @Override
                public void success(MessageModel messageModel, Response response) {
                    Log.d("response", Integer.toString(response.getStatus()));
                    if (response.getStatus() == 200) {
                        mListener.setPreferences("login", true);
                        KingdomsFragment fragment = new KingdomsFragment();
                        mListener.swapFragments(R.id.main_container, fragment);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

}
