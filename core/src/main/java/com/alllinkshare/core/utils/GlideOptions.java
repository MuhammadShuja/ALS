package com.alllinkshare.core.utils;

import com.alllinkshare.core.R;
import com.bumptech.glide.request.RequestOptions;

public class GlideOptions {

    public static RequestOptions getDefault(){
        return new RequestOptions()
                .timeout(10000)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.image_placeholder)
                .dontAnimate()
                .dontTransform();
    }

    public static RequestOptions getProductOptions(){
        return new RequestOptions()
                .timeout(10000)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.product_image_placeholder)
                .dontAnimate()
                .dontTransform();
    }
}