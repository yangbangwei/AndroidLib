package com.android.common.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.android.common.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Glide图片加载工具类
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class ImageUtils {

    /**
     * 加载网络图片，可设置错误图片
     *
     * @param imageView
     * @param url
     * @param placeholder
     * @param error
     */
    public static void display(ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param imageView
     * @param url
     */
    public static void display(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param imageView
     * @param url
     */
    public static void display(ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture).into(imageView);
    }

    /**
     * 通过URL加载小图
     *
     * @param imageView
     * @param url
     */
    public static void displaySmallPhoto(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .thumbnail(0.5f)
                .into(imageView);
    }

    /**
     * 通过URL加载大图
     *
     * @param imageView
     * @param url
     */
    public static void displayBigPhoto(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext()).load(url)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void display(ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext()).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture).into(imageView);
    }


    /**
     * 加载网络图片
     *
     * @param imageView
     * @param url
     */
    public static void displayGifOrPic(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (url.toLowerCase().endsWith("gif")) {
            Glide.with(imageView.getContext())
                    .asGif()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(imageView);
        }
    }
}
