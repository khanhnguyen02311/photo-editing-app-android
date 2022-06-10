package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter;

public class HighlightShadowFilter extends _ParentFilter{
    GPUImageHighlightShadowFilter filter;

    public HighlightShadowFilter(ArrayList<_AdjustConfig> listCfg) {
        filterName = "Highlight Shadow";
        filter = new GPUImageHighlightShadowFilter();
        listParameter = new ArrayList<>(Arrays.asList("Highlight", "Shadow"));
        listConfig = listCfg;
    }

    @Override
    public void setFilterValue(int index, float sliderIntensity) {
        float temp = listConfig.get(index).setAndReturnIntensity(sliderIntensity);
        switch (index) {
            case 0: filter.setHighlights(temp); break;
            case 1: filter.setShadows(temp); break;
        }
    }

    @Override
    public void undoAllFilterValue() {
        for (int i=0; i<listConfig.size(); ++i) {
            listConfig.get(i).undoIntensity();
            float temp = listConfig.get(i).getIntensity();
            switch (i) {
                case 0: filter.setHighlights(temp); break;
                case 1: filter.setShadows(temp); break;
            }
        }
    }

    @Override
    public GPUImageFilter getFilter() {
        return filter;
    }
}
