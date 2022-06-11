package com.example.photoeditingapp_main._Classes;

public class ConfigParameters {
    private float[] configs;
    //exposure, temperature, tint, contrast, saturation, vibrance, highlight, shadow, hue, rgb_r, rgb_g, rgb_b, sharpness, vignette_start, vignette_end;

    public ConfigParameters() {
        configs = new float[] {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };
    }

    public ConfigParameters(float[] _configs) {
        configs = _configs;
    }

    public void setConfig(int position, float value) {
        configs[position] = value;
    }

    public float getConfig(int position) {
        return configs[position];
    }

    public float[] getConfigs() {return configs;}
}
