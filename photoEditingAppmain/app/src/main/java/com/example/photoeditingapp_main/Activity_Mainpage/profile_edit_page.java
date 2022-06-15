package com.example.photoeditingapp_main.Activity_Mainpage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.ConfigParameters;
import com.example.photoeditingapp_main._Classes.ExpandableGridView;
import com.example.photoeditingapp_main._Classes.GeneralPictureItem;
import com.example.photoeditingapp_main._Classes._AccountGridViewAdapter;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile_edit_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_edit_page extends Fragment {
    _GlobalVariables gv;

    String newUri = "";

    final String
            EMPTY_ERROR = "Field can't be empty",
            TOO_SHORT_ERROR = "Text too short",
            NOT_IDENTICAL_PASSWORD = "The passwords must be identical";

    ImageView imageView;
    TextInputEditText nameText, bioText, usrText, oldPswText, pswText, confirmPswText;
    TextInputLayout nameLayout, bioLayout, usrLayout, oldPswLayout, pswLayout, confirmPswLayout;
    TextView changeImage, changeInfo;
    MaterialButton cancelBtn, confirmBtn;

    CheckBox showPassword;

    LinearLayout accountInfoLayout;

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

        gv = (_GlobalVariables) requireActivity().getApplication();

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

        showPassword = view.findViewById(R.id.showPasswordChecker);

        accountInfoLayout = view.findViewById(R.id.changeAccountInfoLayout);
        accountInfoLayout.setVisibility(View.GONE);

        gv.getFirestoreDB().collection("users").whereEqualTo("usr", gv.getLocalDB().getActiveUser().get(0)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshot) {
                        if (!snapshot.isEmpty()) {
                            nameText.setText((String) snapshot.getDocuments().get(0).get("name"));
                            bioText.setText((String) snapshot.getDocuments().get(0).get("bio"));
                            usrText.setText((String) snapshot.getDocuments().get(0).get("usr"));
                            if (!Objects.requireNonNull(snapshot.getDocuments().get(0).get("avatar")).equals(""))
                                Glide.with(requireContext()).load(Uri.parse((String) snapshot.getDocuments().get(0).get("avatar"))).centerCrop().into(imageView);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = Objects.requireNonNull(nameText.getText()).toString();

                    if (text.isEmpty()) nameLayout.setError(EMPTY_ERROR);
                    else if (text.length() <= 6) nameLayout.setError(TOO_SHORT_ERROR);
                    else {
                        nameLayout.setError(null);
                        nameLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        pswText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = pswText.getText().toString();

                    if (text.isEmpty()) pswLayout.setError(EMPTY_ERROR);
                    else if (text.length() <= 6) pswLayout.setError(TOO_SHORT_ERROR);
                    else if (!Objects.requireNonNull(confirmPswText.getText()).toString().equals(text))
                        pswLayout.setError(NOT_IDENTICAL_PASSWORD);
                    else {
                        pswLayout.setError(null);
                        pswLayout.setErrorEnabled(false);
                        confirmPswLayout.setError(null);
                        confirmPswLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        confirmPswText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (!focused) {
                    String text = Objects.requireNonNull(confirmPswText.getText()).toString();

                    if (text.isEmpty()) confirmPswText.setError(EMPTY_ERROR);
                    else if (text.length() <= 6) confirmPswText.setError(TOO_SHORT_ERROR);
                    else if (!Objects.requireNonNull(pswText.getText()).toString().equals(text))
                        confirmPswLayout.setError(NOT_IDENTICAL_PASSWORD);
                    else {
                        pswLayout.setError(null);
                        pswLayout.setErrorEnabled(false);
                        confirmPswLayout.setError(null);
                        confirmPswLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    pswText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPswText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    pswText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pswText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                pswText.setSelection(Objects.requireNonNull(pswText.getText()).length());
                confirmPswText.setSelection(Objects.requireNonNull(confirmPswText.getText()).length());
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountInfoLayout.getVisibility() == View.GONE) accountInfoLayout.setVisibility(View.VISIBLE);
                else accountInfoLayout.setVisibility(View.GONE);
            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickerDialog();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(view.getContext());
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("Loading");
                pd.show();

                nameLayout.clearFocus();
                bioLayout.clearFocus();
                oldPswLayout.clearFocus();
                pswLayout.clearFocus();
                confirmPswLayout.clearFocus();

                String name = Objects.requireNonNull(nameText.getText()).toString();
                String bio = Objects.requireNonNull(bioText.getText()).toString();

                if (!name.isEmpty() && !bio.isEmpty() &&
                        nameLayout.getError() == null) {
                    gv.getFirestoreDB().collection("users").whereEqualTo("usr", gv.getLocalDB().getActiveUser().get(0)).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot snapshot) {
                                    if (!snapshot.isEmpty()) {
                                        DocumentReference rf = snapshot.getDocuments().get(0).getReference();
                                        rf.update("name", name);
                                        rf.update("bio", bio);
                                        rf.update("avatar", newUri);

                                        if (accountInfoLayout.getVisibility() == View.VISIBLE) {
                                            String usr = Objects.requireNonNull(usrText.getText()).toString();
                                            String oldpsw = Objects.requireNonNull(oldPswText.getText()).toString();
                                            String psw = Objects.requireNonNull(pswText.getText()).toString();
                                            String cfpsw = Objects.requireNonNull(confirmPswText.getText()).toString();

                                            String oldUsr = (String) snapshot.getDocuments().get(0).get("usr");

                                            if (!usr.isEmpty() && usrLayout.getError() == null) {
                                                gv.getFirestoreDB().collection("users").whereEqualTo("usr", usr).get()
                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot snapshot) {
                                                            if (snapshot.isEmpty()) {
                                                                rf.update("usr", usr);
                                                                gv.getFirestoreDB().collection("images").whereEqualTo("usr", oldUsr).get()
                                                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(QuerySnapshot snapshot) {
                                                                                List<DocumentSnapshot> list = snapshot.getDocuments();
                                                                                for (DocumentSnapshot snap: list)
                                                                                    snap.getReference().update("usr", usr);
                                                                                if (!psw.isEmpty() && !oldpsw.isEmpty() && !cfpsw.isEmpty() &&
                                                                                        oldPswLayout.getError() == null &&
                                                                                        pswLayout.getError() == null &&
                                                                                        confirmPswLayout.getError() == null) {
                                                                                    rf.update("psw", gv.hashingAlgorithm(psw)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {
                                                                                            pd.dismiss();
                                                                                            Navigation.findNavController(view).popBackStack();
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                e.printStackTrace();
                                                                                pd.dismiss();
                                                                                Navigation.findNavController(view).popBackStack();
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    });
                                            }
                                        } else {
                                            pd.dismiss();
                                            Navigation.findNavController(view).popBackStack();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    pd.dismiss();
                                    Navigation.findNavController(view).popBackStack();
                                }
                            });
                }
            }
        });
    }

    private void imagePickerDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout._dialog_image_picker);

        Window window = dialog.getWindow();

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        window.setAttributes(windowAttributes);

        ExpandableGridView gridView = dialog.findViewById(R.id.GridViewImages);
        ArrayList<GeneralPictureItem> listImages = gv.getLocalDB().getImagesFromStudio(true);
        _AccountGridViewAdapter adapter = new _AccountGridViewAdapter(getContext(), listImages);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                newUri = listImages.get(i).getImageUri().toString();
                Log.i("NewUri", newUri);
                Glide.with(requireContext()).load(Uri.parse(newUri)).into(imageView);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}