package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class ContrastFilter extends _ParentFilter {
    GPUImageContrastFilter filter;

    public ContrastFilter(ArrayList<_AdjustConfig> listCfg) {
        filterName = "Contrast";
        filter = new GPUImageContrastFilter();
        listParameter = new ArrayList<>(Collections.singletonList("Strength"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        filter.setContrast(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (_AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        filter.setContrast(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
