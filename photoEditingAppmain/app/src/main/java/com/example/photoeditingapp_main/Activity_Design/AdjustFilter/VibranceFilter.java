package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVibranceFilter;

public class VibranceFilter extends _ParentFilter{
    GPUImageVibranceFilter filter;

    public VibranceFilter(ArrayList<_AdjustConfig> listCfg) {
        filterName = "Vibrance";
        filter = new GPUImageVibranceFilter();
        listParameter = new ArrayList<>(Collections.singletonList("Strength"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        filter.setVibrance(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (_AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        filter.setVibrance(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
