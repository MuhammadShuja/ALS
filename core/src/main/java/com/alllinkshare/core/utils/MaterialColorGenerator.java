package com.alllinkshare.core.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MaterialColorGenerator {
    public static MaterialColorGenerator DEFAULT;

    public static MaterialColorGenerator MATERIAL;

    static {
        DEFAULT = create(Arrays.asList(
                0xfff16364,
                0xfff58559,
                0xfff9a43e,
                0xffe4c62e,
                0xff67bf74,
                0xff59a2be,
                0xff2093cd,
                0xffad62a7,
                0xff805781
        ));
        MATERIAL = create(Arrays.asList(
                0xff729B34,
                0xffD03030,
                0xff455967,
                0xff4F339A,
                0xffE28C1B,
                0xff3B7ADE
        ));
    }

    private final List<Integer> mColors;
    private final Random mRandom;

    public static MaterialColorGenerator create(List<Integer> colorList) {
        return new MaterialColorGenerator(colorList);
    }

    private MaterialColorGenerator(List<Integer> colorList) {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
    }

    public int getRandomColor() {
        return mColors.get(mRandom.nextInt(mColors.size()));
    }

    public int getColor(Object key) {
        return mColors.get(Math.abs(key.hashCode()) % mColors.size());
    }
}