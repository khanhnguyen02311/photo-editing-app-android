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
import android.content.Intent;
import android.content.UriPermission;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.example.photoeditingapp_main._Classes.ConfigParameters;
import com.example.photoeditingapp_main._Classes.DesignGeneralItem;
import com.example.photoeditingapp_main._Classes.GeneralPictureItem;
import com.example.photoeditingapp_main._Classes._DesignGeneralAdapter;
import com.example.photoeditingapp_main._Classes._GlobalVariables;
import com.example.photoeditingapp_main._Classes._RecyclerTouchListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;

public class DesignActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")

    _GlobalVariables gv;
    Calendar calendar = Calendar.getInstance();

    Uri image_uri = null;
    GPUImageFilterGroup gpuImageFilterGroup = new GPUImageFilterGroup(null);
    GPUImageView imageView;
    GPUImageBrightnessFilter emptyTempFilter = new GPUImageBrightnessFilter();
    CropImageView cropImageView;
    TransformFilter transformFilter = null;
    Bitmap imageBitmap = null;
    ConfigParameters configParameters;

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

        gv = (_GlobalVariables) getApplication();

        parentLayout = findViewById(R.id.parent_ConstraintLayout);
        gpuImageLayout = findViewById(R.id.gpuImageFrame);
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.recyclerViewDesign);
        imageView = findViewById(R.id.gpuimageview);
        cropImageView = findViewById(R.id.cropimageview);
        beforeAfterBtn = findViewById(R.id.beforeAfterBtn);

        controllerFragment = findViewById(R.id.controllerFragment);

        configParameters = new ConfigParameters();

        //image_uri = (Uri) getIntent().getData();
        if (getIntent().getClipData().getItemCount() == 1) image_uri = (Uri) getIntent().getClipData().getItemAt(0).getUri();
        int receivedFlags = getIntent().getFlags();
        if ((receivedFlags & Intent.FLAG_GRANT_READ_URI_PERMISSION) == 0)
        {
            Log.i("FLAGS", "Read URI permission flag not available");
        }
        try {
            List<UriPermission> list = this.getContentResolver().getPersistedUriPermissions();
            list.size();
            imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            imageView.setRatio((float)imageBitmap.getWidth() / imageBitmap.getHeight());
        } catch (IOException e) { e.printStackTrace(); }

        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            image_uri = (Uri) bundle.get("image_uri");
            try {
                *//*InputStream is = getContentResolver().openInputStream(image_uri);
                imageBitmap = BitmapFactory.decodeStream(is);*//*
                List<UriPermission> list = this.getContentResolver().getPersistedUriPermissions();
                list.size();
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                imageView.setRatio((float)imageBitmap.getWidth() / imageBitmap.getHeight());
            } catch (IOException e) { e.printStackTrace(); }

            *//*String filePath = image_uri.getPath();
            imageBitmap  = BitmapFactory.decodeFile(filePath);
            imageView.setRatio((float)imageBitmap.getWidth() / imageBitmap.getHeight());*//*
        }*/

        imageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        imageView.setImage(image_uri);

        emptyTempFilter.setBrightness(0f);

        constraintSet.clone(parentLayout);
        constraintSet.connect(gpuImageLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 50);
        constraintSet.applyTo(parentLayout);

        imageFilterList = new ArrayList<>(Arrays.asList(
                null,                                                                               //custom transform filter
                new ExposureFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(-0.6f, 0f, 0.6f, 0f, -50f, 50f)))),                       //brightness
                new WhiteBalanceFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(3600f, 5550f, 12000f, 0f, -50f, 50f),                     //temperature
                        new _AdjustConfig(-100f, 0f, 100f, 0f, -50f, 50f)))),                       //tint
                new ContrastFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0.5f, 1f, 1.7f, 0f, -50f, 50f)))),                        //contrast
                new SaturationFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0f, 1f, 1.7f, 0f, -50f, 50f)))),                          //saturation
                new VibranceFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(-0.5f, 0f, 1f, 0f, -50f, 50f)))),                         //vibrance
                new HighlightShadowFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(1f, 1f, -0.5f, 0f, 0f, 100f),                             //highlight
                        new _AdjustConfig(0f, 0f, 1.5f, 0f, 0f, 100f)))),                           //shadow
                new HueFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0f, 0f, 355f, 0f, 0f, 100f)))),                           //hue
                new RGBFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(0.1f, 1f, 2f, 0f, -50f, 50f),                             //r
                        new _AdjustConfig(0.1f, 1f, 2f, 0f, -50f, 50f),                             //g
                        new _AdjustConfig(0.1f, 1f, 2f, 0f, -50f, 50f)))),                          //b
                new SharpnessFilter(new ArrayList<>(Collections.singletonList(
                        new _AdjustConfig(0f, 0f, 1.5f, 0f, 0f, 100f)))),                           //sharpness
                new VignetteFilter(new ArrayList<>(Arrays.asList(
                        new _AdjustConfig(0.5f, 0.5f, 0f, 0f, 0f, 100f),                            //vignette start
                        new _AdjustConfig(1.7f, 1.7f, 0.55f, 0f, 0f, 100f))))                       //vignette end
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

        _RecyclerTouchListener listener1 = new _RecyclerTouchListener(DesignActivity.this, recyclerView, new _RecyclerTouchListener.ClickListener() {
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
        });

        _RecyclerTouchListener listener2 = new _RecyclerTouchListener(DesignActivity.this, recyclerView, new _RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 0:
                        onPause();
                        String name = calendar.getTimeInMillis() + ".png";
                        Bitmap exported = imageView.getGPUImage().getBitmapWithFilterApplied();
                        File path = new File(gv.privateLocation, name);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(path);
                            exported.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                Objects.requireNonNull(fos).close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Uri uri = Uri.fromFile(path);

                        Log.i("PATH URI", uri.toString());
                        if (gv.getLocalDB().addImageToStudio(name, uri)) {
                            GeneralPictureItem addedItem = gv.getLocalDB().getLastAddedImage();
                            if (addedItem.getImageName().equals(name) && gv.getLocalDB().updateConfigToImage(addedItem.getId(), getConfigParameters())) {
                                Log.i("SUCCESS", uri + " " + name + " " + addedItem.getImageName());
                                Snackbar snackbar = Snackbar.make(view, "Image saved to Studio.", 1000);
                                snackbar.show();
                                DesignActivity.super.onBackPressed();
                            } else Log.i("FAILED", uri + " " + name + " " + addedItem.getImageName());
                        } else Log.i("FAILED", uri + " " + name);
                        break;

                    case 1: onBackPressed(); break;
                    default: break;
                }
            }

            @Override
            public void onLongClick(View view, int position) { }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(DesignActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adjustAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        recyclerView.setLayoutManager(new LinearLayoutManager(DesignActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setAdapter(adjustAdapter);
                        recyclerView.removeOnItemTouchListener(listener2);
                        recyclerView.addOnItemTouchListener(listener1);
                        break;

                    case 1:
                        recyclerView.setLayoutManager(new LinearLayoutManager(DesignActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(optionAdapter);
                        recyclerView.removeOnItemTouchListener(listener1);
                        recyclerView.addOnItemTouchListener(listener2);
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
            recyclerView.setAdapter(null);
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout._dialog_rename_album);

        Window window = dialog.getWindow();

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
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

    private ConfigParameters getConfigParameters() {
        ConfigParameters cfg = new ConfigParameters();
        cfg.setConfig(0, imageFilterList.get(1).getSliderValue(0));
        cfg.setConfig(1, imageFilterList.get(2).getSliderValue(0));
        cfg.setConfig(2, imageFilterList.get(2).getSliderValue(1));
        cfg.setConfig(3, imageFilterList.get(3).getSliderValue(0));
        cfg.setConfig(4, imageFilterList.get(4).getSliderValue(0));
        cfg.setConfig(5, imageFilterList.get(5).getSliderValue(0));
        cfg.setConfig(6, imageFilterList.get(6).getSliderValue(0));
        cfg.setConfig(7, imageFilterList.get(6).getSliderValue(1));
        cfg.setConfig(8, imageFilterList.get(7).getSliderValue(0));
        cfg.setConfig(9, imageFilterList.get(8).getSliderValue(0));
        cfg.setConfig(10, imageFilterList.get(8).getSliderValue(1));
        cfg.setConfig(11, imageFilterList.get(8).getSliderValue(2));
        cfg.setConfig(12, imageFilterList.get(9).getSliderValue(0));
        cfg.setConfig(13, imageFilterList.get(10).getSliderValue(0));
        cfg.setConfig(14, imageFilterList.get(10).getSliderValue(1));
        return cfg;
    }
}
