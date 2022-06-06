package com.example.photoeditingapp_main.Activity_Design.ControllerView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.photoeditingapp_main.Activity_Design.AdjustFilter._ParentFilter;
import com.example.photoeditingapp_main.Activity_Design.DesignActivity;
import com.example.photoeditingapp_main.R;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;

public class SliderSimple extends Fragment {
    int numControl = 1;
    String title = "NO-TITLE";
    _ParentFilter currentFilter = null;

    ImageButton cancelBtn, confirmBtn;
    TextView adjust_title;

    ArrayList<SliderPack> packList;

    public SliderSimple() {}

    public SliderSimple(int index, String t, _ParentFilter f) {
        numControl = f.getListConfig().size();
        title = t;
        currentFilter = f;
        currentFilter.setFilterIndex(index);
    }

    public static SliderSimple newInstance(int i, String t, _ParentFilter f) {
        return new SliderSimple(i, t, f);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout._adjust_slider_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentFilter.storePreviousFilterIntensity();

        cancelBtn = view.findViewById(R.id.cancelBtn);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        adjust_title = view.findViewById(R.id.adjust_title);
        packList = new ArrayList<>(Arrays.asList(
                new SliderPack(view.findViewById(R.id.slider_pack1), view.findViewById(R.id.slider1), view.findViewById(R.id.slider_text1)),
                new SliderPack(view.findViewById(R.id.slider_pack2), view.findViewById(R.id.slider2), view.findViewById(R.id.slider_text2)),
                new SliderPack(view.findViewById(R.id.slider_pack3), view.findViewById(R.id.slider3), view.findViewById(R.id.slider_text3))
        ));

        for (int i=0; i<numControl; ++i) {
            packList.get(i)._pack.setVisibility(View.VISIBLE);
            packList.get(i)._slider.setValueFrom(((DesignActivity) requireActivity()).sliderMin);
            packList.get(i)._slider.setValueTo(((DesignActivity) requireActivity()).sliderMax);
            packList.get(i)._slider.setValue(currentFilter.getSliderValue(i));
            packList.get(i)._text.setText(currentFilter.getListParameter().get(i));

            int index = i;
            packList.get(i)._slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull Slider slider) {
                    slider.addOnChangeListener(new Slider.OnChangeListener() {
                        @Override
                        public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                            currentFilter.setFilterValue(index, value);
                            ((DesignActivity) requireActivity()).updateFilterGroup(currentFilter);
                        }
                    });
                }
                @Override
                public void onStopTrackingTouch(@NonNull Slider slider) { }
            });
        }
        for (int i=numControl; i<3; ++i) packList.get(i)._pack.setVisibility(View.GONE);

        adjust_title.setText(title);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "confirmBtn");
                ((DesignActivity) requireActivity()).onCloseControllerFragment(currentFilter);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CLICKED", "cancelBtn");
                currentFilter.undoAllFilterValue();
                ((DesignActivity) requireActivity()).onCloseControllerFragment(currentFilter);
            }
        });
    }

    static class SliderPack {
        public FrameLayout _pack;
        public Slider _slider;
        public TextView _text;

        SliderPack(FrameLayout p, Slider s, TextView t) {
            _pack = p;
            _slider = s;
            _text = t;
        }
    }
}