package com.example.photoeditingapp_main.Activity_Design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.ContrastFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.HighlightShadowFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.RGBFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.SaturationFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.VibranceFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.VignetteFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.WhiteBalanceFilter;
import com.example.photoeditingapp_main.Activity_Design.ControllerView.SliderSimple;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.ExposureFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter._ParentFilter;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.AdjustItem;
import com.example.photoeditingapp_main._Classes._AdjustAdapter;
import com.example.photoeditingapp_main._Classes._RecyclerTouchListener;
import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;

public class DesignActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")

    public float sliderMin = -50f, sliderMax = 50f, sliderHalf = 0f;

    Uri image_uri = null;
    GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup(null);
    GPUImageView imageView;
    AdjustConfig activeConfig;
    Slider slider;

    RecyclerView recyclerView;
    _AdjustAdapter adjustAdapter;
    TabLayout tabLayout;

    ConstraintLayout parentLayout;
    FrameLayout gpuImageLayout;
    ConstraintSet constraintSet = new ConstraintSet();

    FragmentContainerView controllerFragment;
    SliderSimple currentController;

    ArrayList<_ParentFilter> imageFilterList;

    ArrayList<Integer> imageFilterLayerList = new ArrayList<>();
    ArrayList<GPUImageFilter> imageFilterLayer = new ArrayList<>();

    public GPUImageView getImageView() { return imageView; }
    public GPUImageFilterGroup getFilterGroup() { return gpuImageFilterGroup; }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        imageFilterList = new ArrayList<>(Arrays.asList(
                null,
                new ExposureFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(-0.6f, 0f, 0.6f, sliderHalf, sliderMin, sliderMax)))),     //brightness
                new WhiteBalanceFilter(new ArrayList<>(Arrays.asList(
                        new AdjustConfig(4000f, 5550f, 10000f, sliderHalf, sliderMin, sliderMax),   //temperature
                        new AdjustConfig(-100f, 0f, 100f, sliderHalf, sliderMin, sliderMax)))),     //tint
                new ContrastFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(0.5f, 1f, 1.7f, sliderHalf, sliderMin, sliderMax)))),      //contrast
                new SaturationFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(0f, 1f, 1.7f, sliderHalf, sliderMin, sliderMax)))),        //saturation
                new VibranceFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(0f, 1f, 2f, sliderHalf, sliderMin, sliderMax)))),          //vibrance
                new HighlightShadowFilter(new ArrayList<>(Arrays.asList(
                        new AdjustConfig(1f, 1f, -0.5f, sliderMin, sliderMin, sliderMax),           //highlight
                        new AdjustConfig(0f, 0f, 1.5f, sliderMin, sliderMin, sliderMax)))),         //shadow
                new RGBFilter(new ArrayList<>(Arrays.asList(
                        new AdjustConfig(0f, 1f, 2f, sliderHalf, sliderMin, sliderMax),             //r
                        new AdjustConfig(0f, 1f, 2f, sliderHalf, sliderMin, sliderMax),             //g
                        new AdjustConfig(0f, 1f, 2f, sliderHalf, sliderMin, sliderMax)))),          //b
                null,
                new VignetteFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(0f, 1f, 1.7f, sliderHalf, sliderMin, sliderMax))))         //vignette
                ));

        ArrayList<AdjustItem> listAdjust = new ArrayList<AdjustItem>(Arrays.asList(
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_adjust_size), "Transform"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_exposure), "Exposure"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_temperature), "White Balance"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_contrast), "Contrast"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_saturation), "Saturation"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_vibrance), "Vibrance"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_highlight_shadow), "Highlight Shadow"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_rgb), "RGB"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_sharpness), "Sharpness"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_vignette), "Vignette")
        ));

        parentLayout = findViewById(R.id.parent_ConstraintLayout);
        gpuImageLayout = findViewById(R.id.gpuImageFrame);
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.recyclerViewDesign);
        imageView = findViewById(R.id.gpuimageview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adjustAdapter = new _AdjustAdapter(listAdjust);

        controllerFragment = findViewById(R.id.controllerFragment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) image_uri = (Uri) bundle.get("image_uri");
        try {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            imageView.setRatio((float)imageBitmap.getWidth() / imageBitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        imageView.setImage(image_uri);

        constraintSet.clone(parentLayout);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 50);
        constraintSet.applyTo(parentLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        recyclerView.setAdapter(null);
                        break;

                    case 1:
                        recyclerView.setAdapter(adjustAdapter);
                        recyclerView.addOnItemTouchListener(new _RecyclerTouchListener(DesignActivity.this, recyclerView, new _RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                currentController = SliderSimple.newInstance(position, listAdjust.get(position).getText(), imageFilterList.get(position));
                                constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, controllerFragment.getId(), ConstraintSet.TOP, 20);
                                constraintSet.applyTo(parentLayout);
                                getSupportFragmentManager().beginTransaction().replace(R.id.controllerFragment, currentController).commit();
                                recyclerView.setVisibility(View.GONE);
                                tabLayout.setClickable(false);
                            }

                            @Override  public void onLongClick(View view, int position) { }

                        }));
                        break;

                    case 2:
                        recyclerView.setAdapter(null);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + tab.getId());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }


    //METHODS:
    public void onCloseControllerFragment(_ParentFilter currentFilter) {
        updateFilterGroup(currentFilter);
        tabLayout.setClickable(true);
        recyclerView.setVisibility(View.VISIBLE);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, recyclerView.getId(), ConstraintSet.TOP, 20);
        constraintSet.applyTo(parentLayout);
        getSupportFragmentManager().beginTransaction().remove(currentController).commit();
        //Log.i(currentFilter.getFilterName(), Float.toString(imageFilterList.get(1).getListConfig().get(0).intensity));
    }

    public void updateFilterGroup(_ParentFilter currentFilter) {
        for (int i=0; i<imageFilterLayerList.size(); ++i) {
            if (imageFilterLayerList.get(i) == currentFilter.getFilterIndex()) {
                imageFilterLayer.set(i, currentFilter.getFilter());
                gpuImageFilterGroup = new GPUImageFilterGroup(imageFilterLayer);
                imageView.setFilter(gpuImageFilterGroup);
                return;
            }
        }
        imageFilterLayerList.add(currentFilter.getFilterIndex());
        imageFilterLayer.add(currentFilter.getFilter());
        gpuImageFilterGroup = new GPUImageFilterGroup(imageFilterLayer);
        imageView.setFilter(gpuImageFilterGroup);
    }
}
