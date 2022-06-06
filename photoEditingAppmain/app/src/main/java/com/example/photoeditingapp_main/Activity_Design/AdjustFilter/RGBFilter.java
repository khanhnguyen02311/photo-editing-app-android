package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import com.example.photoeditingapp_main.Activity_Design.AdjustConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVibranceFilter;

public class RGBFilter extends _ParentFilter{
    GPUImageRGBFilter filter;

    public RGBFilter(ArrayList<AdjustConfig> listCfg) {
        filterName = "RGB";
        filter = new GPUImageRGBFilter();
        listParameter = new ArrayList<>(Arrays.asList("Red", "Green", "Blue"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        float temp = listConfig.get(index).setAndReturnIntensity(sliderIntensity);
        switch (index) {
            case 0: filter.setRed(temp); break;
            case 1: filter.setGreen(temp); break;
            case 2: filter.setBlue(temp); break;
        }
    }

    @Override
    public void undoAllFilterValue() {
        for (int i=0; i<listConfig.size(); ++i) {
            listConfig.get(i).undoIntensity();
            float temp = listConfig.get(i).getIntensity();
            switch (i) {
                case 0: filter.setRed(temp); break;
                case 1: filter.setGreen(temp); break;
                case 2: filter.setBlue(temp); break;
            }
        }
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
