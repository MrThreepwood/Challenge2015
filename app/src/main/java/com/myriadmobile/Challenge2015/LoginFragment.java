package com.myriadmobile.Challenge2015;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private ChildManager mListener;


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
            mListener = (ChildManager) activity;
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
        final String emailText = email.getText().toString();
        if (!nameText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            MyApplication application = (MyApplication) getActivity().getApplication();
            ChallengeAPI api = application.getApiInstance();
            View v = getActivity().getCurrentFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            api.logIn(emailText, new Callback<MessageModel>() {
                @Override
                public void success(MessageModel messageModel, Response response) {
                    if (response.getStatus() == 200) {
                        mListener.setPreferences("login", emailText);
                        KingdomsFragment fragment = new KingdomsFragment();
                        mListener.swapFragments(R.id.fragment_container, fragment, false);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } else if (nameText.isEmpty()) {
            Toast.makeText((Context) mListener, "The name field is required", Toast.LENGTH_LONG).show();
        } else if (emailText.isEmpty()) {
            Toast.makeText((Context) mListener, "The email field is required", Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            Toast.makeText((Context) mListener, "The email you provided is not valid", Toast.LENGTH_LONG).show();
        }
    }

}
