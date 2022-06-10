package com.example.photoeditingapp_main.Activity_Design.AdjustFilter;

import java.util.ArrayList;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public abstract class _ParentFilter {
    String filterName;
    int filterIndex;
    ArrayList<_AdjustConfig> listConfig;
    ArrayList<String> listParameter;

    public String getFilterName() {return filterName;}
    public void setFilterName(String filterName) {this.filterName = filterName;}

    public int getFilterIndex() {return filterIndex;}
    public void setFilterIndex(int filterIndex) {this.filterIndex = filterIndex;}

    public ArrayList<_AdjustConfig> getListConfig() {return listConfig;}
    public void setListConfig(ArrayList<_AdjustConfig> config) {this.listConfig = config;}

    public ArrayList<String> getListParameter() {return listParameter;}
    public void setListParameter(ArrayList<String> listParameter) {this.listParameter = listParameter;}

    public float getSliderValue(int index) {
        return listConfig.get(index).getSliderIntensity();
    }

    public _ParentFilter() {
        filterName = null;
        filterIndex = -1;
        listConfig = new ArrayList<>();
        listParameter = new ArrayList<>();
    }

    public abstract void setFilterValue(int index, float intensity);
    public abstract void undoAllFilterValue();
    public abstract GPUImageFilter getFilter();

    public void storePreviousFilterIntensity() {
        for (_AdjustConfig cfg: listConfig) {
            cfg.rewritePreviousIntensity();
        }
    }
}
