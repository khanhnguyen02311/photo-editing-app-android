package com.example.photoeditingapp_main.Activity_Design.ControllerView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.TransformFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter._ParentFilter;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.AdjustItem;
import com.example.photoeditingapp_main._Classes._AdjustAdapter;
import com.example.photoeditingapp_main._Classes._RecyclerTouchListener;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransformController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransformController extends Fragment {
    TransformFilter currentFilter;

    ImageButton cancelBtn, confirmBtn;
    TextView adjust_title;
    RecyclerView cropRatiosView;
    _AdjustAdapter adjustAdapter;

    SliderSimple.SliderPack packList;

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

        cropRatiosView = view.findViewById(R.id.cropRatioRecyclerView);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        adjust_title = view.findViewById(R.id.adjust_title);
        packList = new SliderSimple.SliderPack(view.findViewById(R.id.slider_pack1), view.findViewById(R.id.slider1), view.findViewById(R.id.slider_text1));

        ArrayList<AdjustItem> listAdjust = new ArrayList<AdjustItem>(Arrays.asList(
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "Square"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "16:9"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "9:16"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "3:2"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "2:3"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "4:3"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_ratio), "3:4"),
                new AdjustItem(AppCompatResources.getDrawable(view.getContext(), R.drawable.ic_customratio), "Custom")));

        cropRatiosView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        adjustAdapter = new _AdjustAdapter(listAdjust);
        cropRatiosView.setAdapter(adjustAdapter);

        cropRatiosView.addOnItemTouchListener(new _RecyclerTouchListener(view.getContext(), cropRatiosView, new _RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 0: currentFilter.setCropRatio(1, 1);
                    case 1: currentFilter.setCropRatio(16, 9);
                    case 2: currentFilter.setCropRatio(9, 16);
                    case 3: currentFilter.setCropRatio(3, 2);
                    case 4: currentFilter.setCropRatio(2, 3);
                    case 5: currentFilter.setCropRatio(4, 3);
                    case 6: currentFilter.setCropRatio(3, 4);
                    case 7: currentFilter.setCropRatio(0, 0);
                }
            }

            @Override
            public void onLongClick(View view, int position) { }
        }));

        packList._slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                slider.addOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                    }
                });
            }
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) { }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "confirmBtn");
                ((DesignActivity) requireActivity()).onCloseTransformFragment();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "cancelBtn");
                ((DesignActivity) requireActivity()).onCloseTransformFragment();
            }
        });
    }
}