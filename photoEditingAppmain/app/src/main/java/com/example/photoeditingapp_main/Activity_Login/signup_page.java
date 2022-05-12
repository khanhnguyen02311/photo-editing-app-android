package com.example.photoeditingapp_main.Activity_Login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.ThreadLockedTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signup_page#newInstance} factory method to
 * create an instance of this fragment.
 */

public class signup_page extends Fragment {

    final String
            EMPTY_ERROR = "Field can't be empty",
            TOO_SHORT_ERROR = "Text too short",
            NOT_VALID_EMAIL = "Email not valid",
            NOT_IDENTICAL_PASSWORD = "The passwords must be identical",
            USED_USERNAME = "Username has been used",
            USED_EMAIL = "Email has been used";


    TextInputLayout usernameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    TextInputEditText usernameText, emailText, passwordText, confirmPasswordText;
    CheckBox showPassword;

    MaterialButton signupBtn;
    TextView signinHyperlink;

    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

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

        showPassword = view.findViewById(R.id.showPasswordChecker);

        signupBtn = view.findViewById(R.id.signupBtn);
        signinHyperlink = view.findViewById(R.id.signInHyperlink);

        Snackbar declineBar = Snackbar.make(view, "Information not valid.", 1000);
        Snackbar acceptBar = Snackbar.make(view, "Sign up completed", 2000);
        Snackbar errorBar = Snackbar.make(view, "Error. Can't add new user", 1000);

        //Add EditText listeners when clear focus
        usernameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = usernameText.getText().toString();

                    if (text.isEmpty()) usernameLayout.setError(EMPTY_ERROR);
                    else if (text.length() <= 6) usernameLayout.setError(TOO_SHORT_ERROR);
                    else {
                        usernameLayout.setError(null);
                        usernameLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        emailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = emailText.getText().toString();

                    if (text.isEmpty()) emailLayout.setError(EMPTY_ERROR);
                    else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches())
                        emailLayout.setError(NOT_VALID_EMAIL);
                    else {
                        emailLayout.setError(null);
                        emailLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = passwordText.getText().toString();

                    if (text.isEmpty()) passwordLayout.setError(EMPTY_ERROR);
                    else if (text.length() <= 6) passwordLayout.setError(TOO_SHORT_ERROR);
                    else if (!confirmPasswordText.getText().toString().equals(text))
                        passwordLayout.setError(NOT_IDENTICAL_PASSWORD);
                    else {
                        passwordLayout.setError(null);
                        passwordLayout.setErrorEnabled(false);
                        confirmPasswordLayout.setError(null);
                        confirmPasswordLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        confirmPasswordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = confirmPasswordText.getText().toString();

                    if (text.isEmpty()) confirmPasswordLayout.setError(EMPTY_ERROR);
                    else if (text.length() <= 6) confirmPasswordLayout.setError(TOO_SHORT_ERROR);
                    else if (!passwordText.getText().toString().equals(text))
                        confirmPasswordLayout.setError(NOT_IDENTICAL_PASSWORD);
                    else {
                        passwordLayout.setError(null);
                        passwordLayout.setErrorEnabled(false);
                        confirmPasswordLayout.setError(null);
                        confirmPasswordLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                passwordText.setSelection(passwordText.getText().length());
                confirmPasswordText.setSelection(confirmPasswordText.getText().length());
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
                ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setMessage("Loading");
                pd.show();

                usernameLayout.clearFocus();
                emailLayout.clearFocus();
                passwordLayout.clearFocus();
                confirmPasswordLayout.clearFocus();

                String usr = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String psw = passwordText.getText().toString();

                if (usernameLayout.getError() == null &&
                        emailLayout.getError() == null &&
                        passwordLayout.getError() == null &&
                        confirmPasswordLayout.getError() == null) {

                    firestoreDB.collection("users").whereEqualTo("usr", usr).whereEqualTo("email", email).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot snapshot) {
                            if (snapshot.isEmpty()) {
                                firestoreDB.collection("users").whereEqualTo("email", email).get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot snapshot) {
                                        if (snapshot.isEmpty()) {
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("usr", usr);
                                            user.put("email", email);
                                            user.put("psw", psw);

                                            firestoreDB.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("NEWUSER", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    pd.dismiss();
                                                    acceptBar.show();
                                                    Navigation.findNavController(view).navigate(R.id.action_signup_page_to_signin_page);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("NEWUSER", "Error adding document", e);
                                                    errorBar.show();
                                                    pd.dismiss();
                                                }
                                            });
                                        } else {
                                            emailLayout.setError(USED_EMAIL);
                                            pd.dismiss();
                                            declineBar.show();
                                        }
                                    }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("DB_ERROR", "Error getting data: ", e);
                                        errorBar.show();
                                        pd.dismiss();
                                    }
                                });
                            } else {
                                usernameLayout.setError(USED_USERNAME);
                                pd.dismiss();
                                declineBar.show();
                            }
                        }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("DB_ERROR", "Error getting data: ", e);
                            errorBar.show();
                            pd.dismiss();
                        }
                    });
                } else declineBar.show();
            }
        });
    }
}