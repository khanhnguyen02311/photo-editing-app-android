package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.ExpandableGridView;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class account_page extends Fragment {

    _GlobalVariables gv;

    TextView nameText, bioText, photoNumber, timeStarted;
    ImageView accountImageView;
    MaterialCardView editProfileBtn;

    public account_page() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static account_page newInstance(String param1, String param2) {
        return new account_page();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_page, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gv = (_GlobalVariables) requireActivity().getApplication();

        TextView pageName = requireActivity().findViewById(R.id.pageName);
        pageName.setText("ACCOUNT");

        nameText = view.findViewById(R.id.name_box);
        bioText = view.findViewById(R.id.bio_box);
        photoNumber = view.findViewById(R.id.photo_number);
        timeStarted = view.findViewById(R.id.time_started);
        accountImageView = view.findViewById(R.id.account_image);
        editProfileBtn = view.findViewById(R.id.edit_profile_btn);

        gv.getFirestoreDB().collection("users").whereEqualTo("usr", gv.getLocalDB().getActiveUser().get(0)).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot snapshots) {
                                if (!snapshots.isEmpty()) {
                                    nameText.setText((String) snapshots.getDocuments().get(0).get("name"));
                                    bioText.setText((String) snapshots.getDocuments().get(0).get("bio"));
                                    timeStarted.setText((String) snapshots.getDocuments().get(0).get("timestart"));
                                    if (Objects.requireNonNull(snapshots.getDocuments().get(0).get("avatar")).equals("")) {
                                        Glide.with(requireContext()).load(R.drawable.stewdioplaceholder).centerCrop().into(accountImageView);

                                        /*gv.getStorageDB().getReference().child("/ESSENTIAL/stewdioplaceholder.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Glide.with(requireContext()).load(uri.toString()).centerCrop().into(accountImageView);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                e.printStackTrace();
                                            }
                                        });*/
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view, "Cannot get information.", 1000).show();
                    }
                });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_account_page_to_profile_edit_page);
            }
        });

        ArrayList<Integer> list = new ArrayList<>();

        ExpandableGridView gridView = view.findViewById(R.id.GridViewAccount);
        gridView.setExpanded(true);
        //_AccountGridViewAdapter adapter = new _AccountGridViewAdapter(getContext(), list);
        //gridView.setAdapter(adapter);

    }
}