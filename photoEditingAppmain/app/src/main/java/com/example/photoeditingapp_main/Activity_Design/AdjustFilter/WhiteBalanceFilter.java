package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

public class WhiteBalanceFilter extends _ParentFilter{
    GPUImageWhiteBalanceFilter filter;

    public WhiteBalanceFilter(ArrayList<_AdjustConfig> listCfg) {
        filterName = "White Balance";
        filter = new GPUImageWhiteBalanceFilter();
        listParameter = new ArrayList<>(Arrays.asList("Temperature", "Tint"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        float temp = listConfig.get(index).setAndReturnIntensity(sliderIntensity);
        switch (index) {
            case 0: filter.setTemperature(temp); break;
            case 1: filter.setTint(temp); break;
        }
    }

    @Override
    public void undoAllFilterValue() {
        for (int i=0; i<listConfig.size(); ++i) {
            listConfig.get(i).undoIntensity();
            float temp = listConfig.get(i).getIntensity();
            switch (i) {
                case 0: filter.setTemperature(temp); break;
                case 1: filter.setTint(temp); break;
            }
        }
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
