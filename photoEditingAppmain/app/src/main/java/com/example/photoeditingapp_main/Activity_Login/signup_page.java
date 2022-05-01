package com.example.photoeditingapp_main.Activity_Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.photoeditingapp_main.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signup_page extends Fragment {

    TextInputEditText usernameText, emailText, passwordText, confirmPasswordText;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signup_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signup_page.
     */
    // TODO: Rename and change types and number of parameters
    public static signup_page newInstance(String param1, String param2) {
        signup_page fragment = new signup_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameText = view.findViewById(R.id.usernameText);
        emailText = view.findViewById(R.id.emailText);
        passwordText = view.findViewById(R.id.passwordText);
        confirmPasswordText = view.findViewById(R.id.confirmPasswordText);

        TextView signinHyperlink = view.findViewById(R.id.signInHyperlink);

        signinHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signup_page_to_signin_page);
            }
        });


    }

    public boolean checkValidAccount() {
        String usr, email, psw, cfpsw;
        try {
            usr = usernameText.getText().toString();
            email = emailText.getText().toString();
            psw = passwordText.getText().toString();
            cfpsw = confirmPasswordText.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (usr.isEmpty() || psw.isEmpty());

        return true;
    }
}