package com.example.photoeditingapp_main.Activity_Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Mainpage.MainpageActivity;
import com.example.photoeditingapp_main.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signin_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signin_page extends Fragment {

    final String
            EMPTY_ERROR = "Field can't be empty",
            TOO_SHORT_ERROR = "Text too short";

    TextInputLayout accountLayout, passwordLayout;
    TextInputEditText accountText, passwordText;

    TextView signupHyperlink;
    MaterialButton signinBtn;

    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signin_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signin_page.
     */
    // TODO: Rename and change types and number of parameters
    public static signin_page newInstance(String param1, String param2) {
        signin_page fragment = new signin_page();
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
        return inflater.inflate(R.layout.fragment_signin_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountLayout = view.findViewById(R.id.textFieldAccount);
        passwordLayout = view.findViewById(R.id.textFieldPassword);

        accountText = view.findViewById(R.id.accountText);
        passwordText = view.findViewById(R.id.passwordText);

        signupHyperlink = view.findViewById(R.id.signUpHyperlink);
        signinBtn = view.findViewById(R.id.signinBtn);

        Snackbar declineBar = Snackbar.make(view, "Couldn't find the account. Check information.", 1000);
        Snackbar errorBar = Snackbar.make(view, "Error. Can't search for account", 1000);

        accountText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = accountText.getText().toString();
                    if (text.isEmpty()) accountLayout.setError(EMPTY_ERROR);
                    else {
                        accountLayout.setError(null);
                        accountLayout.setErrorEnabled(false);
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
                    else {
                        passwordLayout.setError(null);
                        passwordLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        signupHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signin_page_to_signup_page);
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setMessage("Loading");
                pd.show();

                accountLayout.clearFocus();
                passwordLayout.clearFocus();

                String acc = accountText.getText().toString();
                String psw = passwordText.getText().toString();

                if ( !acc.isEmpty() && !psw.isEmpty() &&
                        accountLayout.getError() == null && passwordLayout.getError() == null) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(acc).matches()) {
                        firestoreDB.collection("users").whereEqualTo("usr", acc).whereEqualTo("psw", psw).get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot snapshot) {
                                        if (!snapshot.isEmpty()) {
                                            pd.dismiss();
                                            Intent intent = new Intent(getActivity(), MainpageActivity.class);
                                            intent.putExtra("usr", acc);
                                            startActivity(intent);
                                        } else {
                                            pd.dismiss();
                                            declineBar.show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Log.d("DB_ERROR", "Error getting data: ", e);
                                        errorBar.show();
                                    }
                        });
                    } else {
                        firestoreDB.collection("users").whereEqualTo("email", acc).whereEqualTo("psw", psw).get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot snapshot) {
                                        if (!snapshot.isEmpty()) {
                                            pd.dismiss();
                                            Intent intent = new Intent(getActivity(), MainpageActivity.class);
                                            intent.putExtra("usr", snapshot.getDocuments().get(0).get("usr").toString());
                                            startActivity(intent);
                                        } else {
                                            pd.dismiss();
                                            declineBar.show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Log.d("DB_ERROR", "Error getting data: ", e);
                                        errorBar.show();
                                    }
                        });
                    }
                } else {
                    pd.dismiss();
                    declineBar.show();
                }
            }
        });
    }
}