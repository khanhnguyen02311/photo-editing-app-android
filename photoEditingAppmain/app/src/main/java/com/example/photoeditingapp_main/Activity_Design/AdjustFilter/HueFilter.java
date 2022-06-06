package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import com.example.photoeditingapp_main.Activity_Design.AdjustConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

public class HueFilter extends _ParentFilter{
    GPUImageHueFilter filter;

    public HueFilter(ArrayList<AdjustConfig> listCfg) {
        filterName = "Hue";
        filter = new GPUImageHueFilter();
        listParameter = new ArrayList<>(Collections.singletonList("Strength"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        filter.setHue(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        filter.setHue(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
