package com.example.photoeditingapp_main.Activity_Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.photoeditingapp_main.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup_page#newInstance} factory method to
 * create an instance of this fragment.
 */

public class signup_page extends Fragment {

    String EMPTY_ERROR = "Field can't be empty",
            TOO_SHORT_ERROR = "Text too short",
            TOO_LONG_ERROR = "Text too long",
            NOT_VALID_EMAIL = "Email not valid",
            NOT_IDENTICAL_PASSWORD = "The passwords must be identical";


    TextInputLayout usernameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
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

        //Binding components
        usernameText = view.findViewById(R.id.usernameText);
        emailText = view.findViewById(R.id.emailText);
        passwordText = view.findViewById(R.id.passwordText);
        confirmPasswordText = view.findViewById(R.id.confirmPasswordText);

        usernameLayout = view.findViewById(R.id.textFieldUsername);
        emailLayout = view.findViewById(R.id.textFieldEmail);
        passwordLayout = view.findViewById(R.id.textFieldPassword);
        confirmPasswordLayout = view.findViewById(R.id.textFieldConfirmPassword);

        MaterialButton signupBtn = view.findViewById(R.id.signupBtn);
        TextView signinHyperlink = view.findViewById(R.id.signInHyperlink);


        //Add EditText listeners when text changed
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void afterTextChanged(Editable editable) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simpleLayourError(usernameLayout, charSequence);
            }
        });

        emailText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void afterTextChanged(Editable editable) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) emailLayout.setError(EMPTY_ERROR);
                else if (!Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) emailLayout.setError(NOT_VALID_EMAIL);
                else emailLayout.setError(null);
            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void afterTextChanged(Editable editable) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simpleLayourError(passwordLayout, charSequence);
                if (!charSequence.equals(confirmPasswordText.getText().toString())) passwordLayout.setError(NOT_IDENTICAL_PASSWORD);
                else passwordLayout.setError(null);
            }
        });

        confirmPasswordText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override public void afterTextChanged(Editable editable) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                simpleLayourError(confirmPasswordLayout, charSequence);
                if (!charSequence.equals(passwordText.getText().toString())) confirmPasswordLayout.setError(NOT_IDENTICAL_PASSWORD);
                else confirmPasswordLayout.setError(null);
            }
        });


        signinHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signup_page_to_signin_page);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usr = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String psw = passwordText.getText().toString();

                if (usernameLayout.getError() == null &&
                        emailLayout.getError() == null &&
                        passwordLayout.getError() == null &&
                        confirmPasswordLayout.getError() == null &&
                        !usr.isEmpty() && !email.isEmpty() && !psw.isEmpty()) {

                    Map<String, Object> user = new HashMap<>();
                    user.put("usr", usr);
                    user.put("email", email);
                    user.put("psw", psw);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("NEWUSER", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("NEWUSER", "Error adding document", e);
                            Snackbar snackbar = Snackbar.make(view, "Error. Can't add new user", 600);
                            snackbar.show();
                        }
                    });
                }
                else {
                    Snackbar snackbar = Snackbar.make(view, "Information not valid.", 600);
                    snackbar.show();
                }
            }
        });

    }

    public void simpleLayourError(TextInputLayout layout, CharSequence sqce) {
        if (sqce.length() == 0) layout.setError(EMPTY_ERROR);
        else if (sqce.length() <= 6) layout.setError(TOO_SHORT_ERROR);
        else if (sqce.length() > 25) layout.setError(TOO_LONG_ERROR);
        else layout.setError(null);
    }
}