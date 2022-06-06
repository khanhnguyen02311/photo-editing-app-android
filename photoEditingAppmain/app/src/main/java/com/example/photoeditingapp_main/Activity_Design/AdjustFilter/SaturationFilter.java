package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import com.example.photoeditingapp_main.Activity_Design.AdjustConfig;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;

public class SaturationFilter extends _ParentFilter {
    GPUImageSaturationFilter filter;

    public SaturationFilter(ArrayList<AdjustConfig> listCfg) {
        filterName = "Saturation";
        filter = new GPUImageSaturationFilter();
        listParameter = new ArrayList<>(Collections.singletonList("Strength"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        filter.setSaturation(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        filter.setSaturation(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
