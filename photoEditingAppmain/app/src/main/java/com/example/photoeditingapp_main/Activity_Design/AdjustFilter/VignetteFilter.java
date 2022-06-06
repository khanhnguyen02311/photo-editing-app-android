package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import android.graphics.PointF;

import com.example.photoeditingapp_main.Activity_Design.AdjustConfig;

import java.util.ArrayList;
import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

public class VignetteFilter extends _ParentFilter{
    GPUImageVignetteFilter filter;

    public VignetteFilter(ArrayList<AdjustConfig> listCfg) {
        filterName = "Vignette";
        filter = new GPUImageVignetteFilter();
        listParameter = new ArrayList<>(Arrays.asList("Size"));
        listConfig = listCfg;
        //filter.setVignetteCenter();
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        filter.setVignetteStart(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        filter.setVignetteStart(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
