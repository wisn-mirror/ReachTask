package com.wisn.mainmodule.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.wisn.mainmodule.entity.bean.Image;
import com.wisn.utils.ImageUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/2/7 10:15
 */


public class PreviewImagePageAdapter extends PagerAdapter {
    private final Context context;
    private  int maxCount;
    private ArrayList<Image> images = new ArrayList<>();
    private ArrayList<PhotoView> imagesPhoto = new ArrayList<>(4);
    private final RequestOptions requestOptions;
    private View.OnClickListener onClickListener;
    public PreviewImagePageAdapter(Context context, int maxCount, ArrayList<Image> imageArrayList) {
        this.context = context;
        this.images = imageArrayList;
        this.maxCount = maxCount;
        instantPhotoView();
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    public void instantPhotoView() {
        for (int i = 0; i < 4; i++) {
            PhotoView photoView = new PhotoView(context);
            photoView.setAdjustViewBounds(true);
            imagesPhoto.add(photoView);
        }
    }



    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        final PhotoView currentView = imagesPhoto.remove(0);
        Image image = images.get(position);
        container.addView(currentView);
        Glide.with(context).asBitmap()
                .apply(requestOptions)
                .load(new File(image.getPath()))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        if (width > 8192 || height > 8192) {
                            resource = ImageUtils.scale(resource, 8192, 8192);
                        }
                        setBitmap(currentView, resource);
                    }
                });
        currentView.setOnClickListener(onClickListener);
        return currentView;
    }


    private void setBitmap(PhotoView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        if (bitmap != null) {
            int bw = bitmap.getWidth();
            int bh = bitmap.getHeight();
            int vw = imageView.getWidth();
            int vh = imageView.getHeight();
            if (bw != 0 && bh != 0 && vw != 0 && vh != 0) {
                if (1.0f * bh / bw > 1.0f * vh / vw) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    float offset = (1.0f * bh * vw / bw - vh) / 2;
                    adjustOffset(imageView, offset);
                } else {
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        }
    }

    private void adjustOffset(PhotoView view, float offset) {
        PhotoViewAttacher attacher = view.getAttacher();
        try {
            Field field = PhotoViewAttacher.class.getDeclaredField("mBaseMatrix");
            field.setAccessible(true);
            Matrix matrix = (Matrix) field.get(attacher);
            matrix.postTranslate(0, offset);
            Method method = PhotoViewAttacher.class.getDeclaredMethod("resetMatrix");
            method.setAccessible(true);
            method.invoke(attacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        if (object instanceof PhotoView) {
            PhotoView photoView = (PhotoView) object;
            photoView.setImageDrawable(null);
            imagesPhoto.add(photoView);
            container.removeView(photoView);
        }
    }
}
