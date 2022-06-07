package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import android.graphics.PointF;
import android.util.Log;

import com.example.photoeditingapp_main.Activity_Design.AdjustConfig;

import java.util.ArrayList;
import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

public class VignetteFilter extends _ParentFilter{
    GPUImageVignetteFilter filter;

    public VignetteFilter(ArrayList<AdjustConfig> listCfg) {
        filterName = "Vignette";
        filter = new GPUImageVignetteFilter();
        listParameter = new ArrayList<>(Arrays.asList("Start Position", "End Position"));
        listConfig = listCfg;
        filter.setVignetteCenter(new PointF(0.5f, 0.5f));
        filter.setVignetteStart(listConfig.get(0).setAndReturnIntensity(listConfig.get(0).getOriginSlider()));
        filter.setVignetteEnd(listConfig.get(1).setAndReturnIntensity(listConfig.get(1).getOriginSlider()));
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        float temp = listConfig.get(index).setAndReturnIntensity(sliderIntensity);
        switch (index) {
            case 0: filter.setVignetteStart(temp); break;
            case 1: filter.setVignetteEnd(temp); break;
        }
    }

    @Override
    public void undoAllFilterValue() {
        for (int i=0; i<listConfig.size(); ++i) {
            listConfig.get(i).undoIntensity();
            float temp = listConfig.get(i).getIntensity();
            switch (i) {
                case 0: filter.setVignetteStart(temp); break;
                case 1: filter.setVignetteEnd(temp); break;
            }
        }
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
