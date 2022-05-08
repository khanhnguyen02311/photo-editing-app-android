package com.example.photoeditingapp_main._Classes;

import static java.lang.Math.abs;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class _SliderTransformer implements ViewPager2.PageTransformer {

    private int offscreenPageLimit;

    public _SliderTransformer(int offscreenPageLimit) {
        this.offscreenPageLimit = offscreenPageLimit;
    }

    private final float DEFAULT_TRANSLATION_X = .0f;
    private final float DEFAULT_TRANSLATION_FACTOR = 1.2f;

    private final float SCALE_FACTOR = .14f;
    private final float DEFAULT_SCALE = 1f;

    private final float ALPHA_FACTOR = .3f;
    private final float DEFAULT_ALPHA = 1f;

    @Override
    public void transformPage(@NonNull View page, float position) {

        ViewCompat.setElevation(page, -abs(position));

        float scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE;
        float alphaFactor = -ALPHA_FACTOR * position + DEFAULT_ALPHA;

        if (position < 0f) {
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA+position);
        }
        else if (position <= offscreenPageLimit- 1 ) {
            page.setTranslationX(-(page.getWidth() / DEFAULT_TRANSLATION_FACTOR) * position);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setAlpha(alphaFactor);
        }
        else{
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA);
        }
    }
}
