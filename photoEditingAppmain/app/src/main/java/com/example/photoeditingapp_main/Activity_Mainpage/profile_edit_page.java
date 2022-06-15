package com.example.photoeditingapp_main.Activity_Mainpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photoeditingapp_main.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile_edit_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_edit_page extends Fragment {

    ImageView imageView;
    TextInputEditText nameText, bioText, usrText, oldPswText, pswText, confirmPswText;
    TextInputLayout nameLayout, bioLayout, usrLayout, oldPswLayout, pswLayout, confirmPswLayout;
    TextView changeImage, changeInfo;
    MaterialButton cancelBtn, confirmBtn;

    public profile_edit_page() {
        // Required empty public constructor
    }

    public static profile_edit_page newInstance(String param1, String param2) {
        return new profile_edit_page();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image_view);
        nameText = view.findViewById(R.id.accountnameText);
        bioText = view.findViewById(R.id.bioText);
        usrText = view.findViewById(R.id.usernameText);
        oldPswText = view.findViewById(R.id.passwordText);
        pswText = view.findViewById(R.id.newPasswordText);
        confirmPswText = view.findViewById(R.id.confirmPasswordText);
        nameLayout = view.findViewById(R.id.textFieldAccountname);
        bioLayout = view.findViewById(R.id.textFieldBio);
        usrLayout = view.findViewById(R.id.textFieldUsername);
        oldPswLayout = view.findViewById(R.id.textFieldPassword);
        pswLayout = view.findViewById(R.id.textFieldNewPassword);
        confirmPswLayout = view.findViewById(R.id.textFieldConfirmPassword);
        changeImage = view.findViewById(R.id.change_image_btn);
        changeInfo = view.findViewById(R.id.change_info_btn);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        confirmBtn = view.findViewById(R.id.confirm_btn);

        
    }
}