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
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.canhub.cropper.CropImageView;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.ContrastFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.HighlightShadowFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.HueFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.RGBFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.SaturationFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.SharpnessFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.TransformFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.VibranceFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.VignetteFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.WhiteBalanceFilter;
import com.example.photoeditingapp_main.Activity_Design.ControllerView.SliderSimple;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.ExposureFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter._ParentFilter;
import com.example.photoeditingapp_main.Activity_Design.ControllerView.TransformController;
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
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;

public class DesignActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")

    public float sliderMin = -50f, sliderMax = 50f, sliderHalf = 0f;

    Uri image_uri = null;
    GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup(null);
    GPUImageView imageView;
    GPUImageBrightnessFilter emptyTempFilter = new GPUImageBrightnessFilter();
    CropImageView cropImageView;
    TransformFilter transformFilter;

    RecyclerView recyclerView;
    _AdjustAdapter adjustAdapter;
    TabLayout tabLayout;
    ImageButton beforeAfterBtn;

    ConstraintLayout parentLayout;
    FrameLayout gpuImageLayout;
    ConstraintSet constraintSet = new ConstraintSet();

    FragmentContainerView controllerFragment;
    SliderSimple simpleController;
    TransformController transformController;

    ArrayList<_ParentFilter> imageFilterList;

    ArrayList<Integer> imageFilterLayerList = new ArrayList<>();
    ArrayList<GPUImageFilter> imageFilterLayer = new ArrayList<>();

    public GPUImageView getImageView() { return imageView; }
    public GPUImageFilterGroup getFilterGroup() { return gpuImageFilterGroup; }

    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        parentLayout = findViewById(R.id.parent_ConstraintLayout);
        gpuImageLayout = findViewById(R.id.gpuImageFrame);
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.recyclerViewDesign);
        imageView = findViewById(R.id.gpuimageview);
        cropImageView = findViewById(R.id.cropimageview);
        beforeAfterBtn = findViewById(R.id.beforeAfterBtn);

        controllerFragment = findViewById(R.id.controllerFragment);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) image_uri = (Uri) bundle.get("image_uri");
        try {
            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            imageView.setRatio((float)imageBitmap.getWidth() / imageBitmap.getHeight());
        } catch (IOException e) { e.printStackTrace(); }

        imageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        imageView.setImage(image_uri);

        emptyTempFilter.setBrightness(0f);

        constraintSet.clone(parentLayout);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 50);
        constraintSet.applyTo(parentLayout);

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
                        new AdjustConfig(-0.5f, 0f, 1f, sliderHalf, sliderMin, sliderMax)))),       //vibrance
                new HighlightShadowFilter(new ArrayList<>(Arrays.asList(
                        new AdjustConfig(1f, 1f, -0.5f, sliderMin, sliderMin, sliderMax),           //highlight
                        new AdjustConfig(0f, 0f, 1.5f, sliderMin, sliderMin, sliderMax)))),         //shadow
                new HueFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(0f, 0f, 355f, sliderMin, sliderMin, sliderMax)))),         //hue
                new RGBFilter(new ArrayList<>(Arrays.asList(
                        new AdjustConfig(0.1f, 1f, 2f, sliderHalf, sliderMin, sliderMax),           //r
                        new AdjustConfig(0.1f, 1f, 2f, sliderHalf, sliderMin, sliderMax),           //g
                        new AdjustConfig(0.1f, 1f, 2f, sliderHalf, sliderMin, sliderMax)))),        //b
                new SharpnessFilter(new ArrayList<>(Collections.singletonList(
                        new AdjustConfig(0f, 0f, 1.5f, sliderMin, sliderMin, sliderMax)))),         //sharpness
                new VignetteFilter(new ArrayList<>(Arrays.asList(
                        new AdjustConfig(0.5f, 0.5f, 0f, sliderMin, sliderMin, sliderMax),          //vignette start
                        new AdjustConfig(1.7f, 1.7f, 0.55f, sliderMin, sliderMin, sliderMax))))     //vignette end
                ));

        ArrayList<AdjustItem> listAdjust = new ArrayList<AdjustItem>(Arrays.asList(
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_adjust_size), "Transform"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_exposure), "Exposure"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_temperature), "White Balance"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_contrast), "Contrast"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_saturation), "Saturation"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_vibrance), "Vibrance"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_highlight_shadow), "Highlight Shadow"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_hue), "Hue"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_rgb), "RGB"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_sharpness), "Sharpness"),
                new AdjustItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_vignette), "Vignette")
        ));

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adjustAdapter = new _AdjustAdapter(listAdjust);

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
                                constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, controllerFragment.getId(), ConstraintSet.TOP, 20);
                                constraintSet.applyTo(parentLayout);
                                if (position == 0) {
                                    beforeAfterBtn.setVisibility(View.GONE);
                                    transformFilter = new TransformFilter(image_uri, cropImageView, imageView.getGPUImage().getBitmapWithFilterApplied());
                                    transformController = TransformController.newInstance(transformFilter);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.controllerFragment, transformController).commit();
                                } else {
                                    simpleController = SliderSimple.newInstance(position, listAdjust.get(position).getText(), imageFilterList.get(position));
                                    getSupportFragmentManager().beginTransaction().replace(R.id.controllerFragment, simpleController).commit();
                                }
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

        beforeAfterBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (gpuImageFilterGroup.getFilters().size() != 0) imageView.setFilter(emptyTempFilter);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (gpuImageFilterGroup.getFilters().size() != 0) imageView.setFilter(gpuImageFilterGroup);
                        break;
                }
                return false;
            }
        });
    }


    //METHODS:
    public void onCloseSimpleFragment(_ParentFilter currentFilter) {
        updateFilterGroup(currentFilter);
        tabLayout.setClickable(true);
        recyclerView.setVisibility(View.VISIBLE);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, recyclerView.getId(), ConstraintSet.TOP, 20);
        constraintSet.applyTo(parentLayout);
        getSupportFragmentManager().beginTransaction().remove(simpleController).commit();
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

    public void onCloseTransformFragment() {
        tabLayout.setClickable(true);
        recyclerView.setVisibility(View.VISIBLE);
        beforeAfterBtn.setVisibility(View.VISIBLE);
        cropImageView.setVisibility(View.GONE);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, recyclerView.getId(), ConstraintSet.TOP, 20);
        constraintSet.applyTo(parentLayout);
        getSupportFragmentManager().beginTransaction().remove(transformController).commit();
    }
}
