package com.example.photoeditingapp_main.Activity_Mainpage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes._DiscoverAdapter;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.example.photoeditingapp_main._Classes._MainpageAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_page extends Fragment {

    _GlobalVariables gv;

    RecyclerView rvTrending, rvDiscover;
    _MainpageAdapter trendingAdapter, discoverAdapter;
    TextView discoverSeemore;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_page.
     */
    // TODO: Rename and change types and number of parameters
    public static home_page newInstance(String param1, String param2) {
        home_page fragment = new home_page();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gv = (_GlobalVariables) requireActivity().getApplication();

        TextView pageName = requireActivity().findViewById(R.id.pageName);
        pageName.setText("HOMEPAGE");

        rvTrending = view.findViewById(R.id.rvTrending);
        rvDiscover = view.findViewById(R.id.rvDiscover);
        discoverSeemore = view.findViewById(R.id.seemore_btn);

        rvTrending.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvDiscover.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Log.i("NUMIMAGE", "yo");

        gv.getFirestoreDB().collection("images").orderBy("like_amount", Query.Direction.DESCENDING).limit(10).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshot) {
                        if (!snapshot.isEmpty()) {
                            Log.i("NUMIMAGE", Integer.toString(snapshot.size()));
                            List<DocumentSnapshot> list = snapshot.getDocuments();
                            trendingAdapter = new _MainpageAdapter(getContext(), list);
                            rvTrending.setAdapter(trendingAdapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        gv.getFirestoreDB().collection("images").orderBy("timeadded", Query.Direction.DESCENDING).limit(10).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshot) {
                        if (!snapshot.isEmpty()) {
                            List<DocumentSnapshot> list = snapshot.getDocuments();
                            discoverAdapter = new _MainpageAdapter(getContext(), list);
                            rvDiscover.setAdapter(discoverAdapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        discoverSeemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_home_page_to_discover_page);
            }
        });
    }
}