package com.example.photoeditingapp_main.Activity_Design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.canhub.cropper.CropImageView;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter._AdjustConfig;
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
import com.example.photoeditingapp_main.Activity_Design.ControllerView.SliderController;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter.ExposureFilter;
import com.example.photoeditingapp_main.Activity_Design.AdjustFilter._ParentFilter;
import com.example.photoeditingapp_main.Activity_Design.ControllerView.TransformController;
import com.example.photoeditingapp_main.R;
import com.example.photoeditingapp_main._Classes.DesignGeneralItem;
import com.example.photoeditingapp_main._Classes._DesignGeneralAdapter;
import com.example.photoeditingapp_main._Classes._RecyclerTouchListener;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    TransformFilter transformFilter = null;
    Bitmap imageBitmap = null;

    RecyclerView recyclerView;
    _DesignGeneralAdapter adjustAdapter, optionAdapter;
    TabLayout tabLayout;
    ImageButton beforeAfterBtn;

    ConstraintLayout parentLayout;
    FrameLayout gpuImageLayout;
    ConstraintSet constraintSet = new ConstraintSet();

    FragmentContainerView controllerFragment;
    SliderController simpleController;
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
            imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            imageView.setRatio((float)imageBitmap.getWidth() / imageBitmap.getHeight());
        } catch (IOException e) { e.printStackTrace(); }

        imageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        imageView.setImage(image_uri);

        emptyTempFilter.setBrightness(0f);

        constraintSet.clone(parentLayout);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 50);
        constraintSet.applyTo(parentLayout);

        imageFilterList = new ArrayList<>(Arrays.asList(
                null,                                                                                //custom transform filter
                new ExposureFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(-0.6f, 0f, 0.6f, sliderHalf, sliderMin, sliderMax)))),     //brightness
                new WhiteBalanceFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(3600f, 5550f, 12000f, sliderHalf, sliderMin, sliderMax),   //temperature
                        new _AdjustConfig(-100f, 0f, 100f, sliderHalf, sliderMin, sliderMax)))),     //tint
                new ContrastFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0.5f, 1f, 1.7f, sliderHalf, sliderMin, sliderMax)))),      //contrast
                new SaturationFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0f, 1f, 1.7f, sliderHalf, sliderMin, sliderMax)))),        //saturation
                new VibranceFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(-0.5f, 0f, 1f, sliderHalf, sliderMin, sliderMax)))),       //vibrance
                new HighlightShadowFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(1f, 1f, -0.5f, sliderMin, sliderMin, sliderMax),           //highlight
                        new _AdjustConfig(0f, 0f, 1.5f, sliderMin, sliderMin, sliderMax)))),         //shadow
                new HueFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0f, 0f, 355f, sliderMin, sliderMin, sliderMax)))),         //hue
                new RGBFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(0.1f, 1f, 2f, sliderHalf, sliderMin, sliderMax),           //r
                        new _AdjustConfig(0.1f, 1f, 2f, sliderHalf, sliderMin, sliderMax),           //g
                        new _AdjustConfig(0.1f, 1f, 2f, sliderHalf, sliderMin, sliderMax)))),        //b
                new SharpnessFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0f, 0f, 1.5f, sliderMin, sliderMin, sliderMax)))),         //sharpness
                new VignetteFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(0.5f, 0.5f, 0f, sliderMin, sliderMin, sliderMax),          //vignette start
                        new _AdjustConfig(1.7f, 1.7f, 0.55f, sliderMin, sliderMin, sliderMax))))     //vignette end
                ));

        ArrayList<DesignGeneralItem> listAdjust = new ArrayList<DesignGeneralItem>(Arrays.asList(
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_adjust_size), "Transform"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_exposure), "Exposure"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_temperature), "White Balance"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_contrast), "Contrast"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_saturation), "Saturation"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_vibrance), "Vibrance"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_highlight_shadow), "Highlight Shadow"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_hue), "Hue"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_rgb), "RGB"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_sharpness), "Sharpness"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_vignette), "Vignette")
        ));

        ArrayList<DesignGeneralItem> listOption = new ArrayList<>(Arrays.asList(
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_arrow_down), "Save to Studio"),
                new DesignGeneralItem(AppCompatResources.getDrawable(DesignActivity.this, R.drawable.ic_close_circle), "Discard changes")
        ));

        adjustAdapter = new _DesignGeneralAdapter(listAdjust, R.layout._custom_design_adjust_itemview);
        optionAdapter = new _DesignGeneralAdapter(listOption, R.layout._custom_design_option_itemview);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        recyclerView.setLayoutManager(new LinearLayoutManager(DesignActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(null);
                        break;

                    case 1:
                        recyclerView.setLayoutManager(new LinearLayoutManager(DesignActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(adjustAdapter);
                        recyclerView.addOnItemTouchListener(new _RecyclerTouchListener(DesignActivity.this, recyclerView, new _RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, controllerFragment.getId(), ConstraintSet.TOP, 20);
                                constraintSet.applyTo(parentLayout);
                                if (position == 0) {
                                    beforeAfterBtn.setVisibility(View.GONE);
                                    transformFilter = new TransformFilter(cropImageView, imageBitmap);
                                    transformController = TransformController.newInstance(transformFilter);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.controllerFragment, transformController).commit();
                                } else {
                                    simpleController = SliderController.newInstance(position, listAdjust.get(position).getText(), imageFilterList.get(position));
                                    getSupportFragmentManager().beginTransaction().replace(R.id.controllerFragment, simpleController).commit();
                                }
                                recyclerView.setVisibility(View.GONE);
                                tabLayout.setClickable(false);
                            }
                            @Override  public void onLongClick(View view, int position) { }
                        }));
                        break;

                    case 2:
                        recyclerView.setLayoutManager(new LinearLayoutManager(DesignActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(optionAdapter);
                        recyclerView.addOnItemTouchListener(new _RecyclerTouchListener(DesignActivity.this, recyclerView, new _RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                switch (position) {
                                    case 0: break;
                                    case 1: DesignActivity.this.onBackPressed(); break;
                                }
                            }

                            @Override
                            public void onLongClick(View view, int position) { }
                        }));
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

    @Override
    public void onBackPressed() {
        if (gpuImageFilterGroup.getFilters().size() != 0 || transformFilter != null) {
            showConfirmationDialog();
        } else super.onBackPressed();
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

    public void onCloseTransformFragment(Bitmap croppedImage) {
        if (croppedImage != null) {
            imageView.getGPUImage().deleteImage();
            imageView.setRatio((float)croppedImage.getWidth() / croppedImage.getHeight());
            imageView.setImage(croppedImage);
        }
        tabLayout.setClickable(true);
        recyclerView.setVisibility(View.VISIBLE);
        beforeAfterBtn.setVisibility(View.VISIBLE);
        cropImageView.setVisibility(View.GONE);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.BOTTOM, recyclerView.getId(), ConstraintSet.TOP, 20);
        constraintSet.applyTo(parentLayout);
        getSupportFragmentManager().beginTransaction().remove(transformController).commit();
    }

    private void showConfirmationDialog() {
        final Dialog dialog = new Dialog(DesignActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout._dialog_rename_album);

        Window window = dialog.getWindow();

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        EditText editText = dialog.findViewById(R.id.editTextRenameAlbum);
        editText.setVisibility(View.GONE);
        TextView title = dialog.findViewById(R.id.titleText);
        TextView confirmBtn = dialog.findViewById(R.id.textViewRenameAlbumSave);
        TextView cancelBtn = dialog.findViewById(R.id.textViewRenameAlbumCancel);

        title.setText("Are you sure to discard?");
        confirmBtn.setText("Confirm");
        cancelBtn.setText("Cancel");

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DesignActivity.super.onBackPressed();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
