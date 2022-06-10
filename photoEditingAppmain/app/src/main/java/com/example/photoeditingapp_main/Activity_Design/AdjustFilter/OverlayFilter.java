package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOverlayBlendFilter;

public class OverlayFilter extends _ParentFilter{
    GPUImageOverlayBlendFilter overlayFilter;
    GPUImageOpacityFilter opacityFilter;

    GPUImageFilterGroup filterGroup;

    public OverlayFilter(ArrayList<_AdjustConfig> listCfg) {
        filterName = "Overlay";
        overlayFilter = new GPUImageOverlayBlendFilter();
        listParameter = new ArrayList<>(Collections.singletonList("Opacity"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        opacityFilter.setOpacity(listConfig.get(index).setAndReturnIntensity(sliderIntensity));
    }

    @Override
    public void undoAllFilterValue() {
        for (_AdjustConfig cfg: listConfig) {cfg.undoIntensity();}
        opacityFilter.setOpacity(listConfig.get(0).getIntensity());
    }

    @Override
    public GPUImageFilter getFilter() {
        filterGroup.addFilter(overlayFilter);
        filterGroup.addFilter(opacityFilter);
        return filterGroup;
    }
}
