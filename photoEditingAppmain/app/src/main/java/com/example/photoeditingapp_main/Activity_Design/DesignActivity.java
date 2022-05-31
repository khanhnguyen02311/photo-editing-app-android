package com.example.photoeditingapp_main.Activity_Design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.photoeditingapp_main.R;
import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayout;

import java.net.URI;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;

public class DesignActivity extends AppCompatActivity {

    String[] ADJUSTS_NAME = {"Exposure", "Contrast", "Saturation"};
    float sliderMin, sliderMax, sliderHalf;
    Uri image_uri = null;
    GPUImage gpuImage;
    GPUImageView imageView;
    FilterConfig activeConfig;
    RecyclerView placeholder;
    Slider slider;
    TabLayout tabLayout;

    public class FilterConfig {
        public int index;
        public float intensity, sliderIntensity;
        public float minValue, originValue, maxValue;

        public FilterConfig(int _index, float _minValue, float _originValue, float _maxValue, float sliderPos) {
            index = _index;
            minValue = _minValue;
            originValue = _originValue;
            maxValue = _maxValue;
            intensity = _originValue;
            sliderIntensity = sliderPos;
        }

        public float calcIntensity(float _intensity) {
            float result;
            if (_intensity <= sliderMin) {
                result = minValue;
            } else if (_intensity >= sliderMax) {
                result = maxValue;
            } else if (_intensity <= sliderHalf) {
                result = minValue + (_intensity - sliderMin) / (sliderHalf - sliderMin) * (originValue - minValue);
            } else {
                result = originValue + (_intensity - sliderHalf) / (sliderMax - sliderHalf) * (maxValue - originValue);
            }
            return result;
        }

        public void setIntensity(float _intensity, int _index) {
            sliderIntensity = _intensity;
            intensity = calcIntensity(_intensity);
        }
    }

    public static class ConfigButton extends AppCompatButton implements View.OnClickListener {

        FilterConfig fConfig;
        DesignActivity activity;

        public ConfigButton(DesignActivity context, FilterConfig config) {
            super(context);
            activity = context;
            fConfig = config;
            setOnClickListener(this);
            setAllCaps(false);
        }

        @Override
        public void onClick(View v) {
            activity.setActiveConfig(fConfig);
        }
    }

    public void setActiveConfig(FilterConfig config) {
        activeConfig = config;
        slider.setValue(config.sliderIntensity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        tabLayout = findViewById(R.id.tabLayout);
        placeholder = findViewById(R.id.placeholder);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) image_uri = (Uri) bundle.get("image_uri");

        gpuImage = new GPUImage(this);

        imageView = findViewById(R.id.gpuimageview);
        imageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        imageView.setImage(image_uri);

        FilterConfig[] listConfig = {
                new FilterConfig(0, -0.5f, 0f, 0.6f, sliderHalf), //exposure
                new FilterConfig(1, 0.4f, 1.0f, 1.8f, sliderHalf), //contrast
                new FilterConfig(2, 0.0f, 1.0f, 2.0f, sliderHalf), //saturation
        };
    }
}