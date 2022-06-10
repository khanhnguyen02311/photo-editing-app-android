package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class ExposureFilter extends _ParentFilter {
    GPUImageExposureFilter filter;

    public ExposureFilter(ArrayList<_AdjustConfig> listCfg) {
        filterName = "Exposure";
        filter = new GPUImageExposureFilter();
        listParameter = new ArrayList<>(Collections.singletonList("Strength"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        filter.setExposure(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (_AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        filter.setExposure(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
