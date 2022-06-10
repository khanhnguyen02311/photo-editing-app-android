package com.example.photoeditingapp_main.Activity_Design.ControllerView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.TransformFilter;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransformController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransformController extends Fragment {
    TransformFilter currentFilter;

    ImageButton cancelBtn, confirmBtn;
    LinearLayout flipHoriBtn, flipVertiBtn, origRatioBtn, custRatioBtn, rotateBtn;
    TextView adjust_title;

    SliderController.SliderPack packList;

    public TransformController() {}

    public TransformController(TransformFilter f) {
        currentFilter = f;
    }

    public static TransformController newInstance(TransformFilter f) {
        return new TransformController(f);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout._adjust_transform_controller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentFilter.getCropImageView().setVisibility(View.VISIBLE);
        ((DesignActivity) requireActivity()).getImageView().setVisibility(View.GONE);

        cancelBtn = view.findViewById(R.id.cancelBtn);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        adjust_title = view.findViewById(R.id.adjust_title);
        flipHoriBtn = view.findViewById(R.id.flipHorizontalBtn);
        flipVertiBtn = view.findViewById(R.id.flipVerticalBtn);
        origRatioBtn = view.findViewById(R.id.originalRatioBtn);
        custRatioBtn = view.findViewById(R.id.customRatioBtn);
        rotateBtn = view.findViewById(R.id.rotateBtn);

        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {currentFilter.rotateImage();}
        });

        flipHoriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentFilter.flipImage(0); }
        });

        flipVertiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentFilter.flipImage(1); }
        });

        origRatioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentFilter.setCropRatio(0); }
        });

        custRatioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentFilter.setCropRatio(1); }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "confirmBtn");
                ((DesignActivity) requireActivity()).getImageView().setVisibility(View.VISIBLE);
                ((DesignActivity) requireActivity()).onCloseTransformFragment(currentFilter.getCroppedImage());
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "cancelBtn");
                ((DesignActivity) requireActivity()).getImageView().setVisibility(View.VISIBLE);
                ((DesignActivity) requireActivity()).onCloseTransformFragment(null);
            }
        });
    }
}