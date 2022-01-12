package com.mountisome.aquareminder.utils;

import android.content.Context;

public class DensityUtils {

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxTodip(Context context, double pxValue) {
        final double density =
                context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

}
