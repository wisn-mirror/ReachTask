package com.wisn.mainmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.entity.bean.Image;
import com.wisn.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/2/5 15:11
 */


public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ImageViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private int maxSelect;
    private ArrayList<Image> imageList;
    private ArrayList<Image> selectImageList;
    private final RequestOptions requestOptions;
    private SelectImageListener selectImageListener;
    private int lastSelectIndex = 0;

    public SelectImageAdapter(Context context, int maxSelect,ArrayList<Image> imageslist) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.maxSelect = maxSelect;
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
        selectImageList = new ArrayList<>();
        if(imageslist!=null){
            selectImageList.addAll(selectImageList);
        }
    }

    public void setSelectImageList(ArrayList<Image> selectImageList) {
        this.selectImageList = selectImageList;
        notifyDataSetChanged();
    }

    public ArrayList<Image> getSelectImageList() {
        return selectImageList;
    }

    public ArrayList<Image> getImageList() {
        return imageList;
    }

    public void setSelectImageListener(SelectImageListener selectImageListener) {
        this.selectImageListener = selectImageListener;
    }

    public void refresh(ArrayList<Image> fileItems) {
        this.imageList = fileItems;
        notifyDataSetChanged();
    }

    @Override
    public SelectImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_select_image, parent, false);
        return new ImageViewHolder(view);
    }

    public long getFirstVisibleTime(int position){
        if(position<imageList.size()){
            //秒
           return  imageList.get(position).getTime();
        }
        return -1;
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        if (imageList != null) {
            final Image fileItem = imageList.get(position);
            holder.image_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectImageList.contains(fileItem)) {
                        selectImageList.remove(fileItem);
                        notifyItemChanged(position);
                    } else if (maxSelect == 1) {
                        selectImageList.clear();
                        selectImageList.add(fileItem);
                        notifyItemChanged(lastSelectIndex);
                        notifyItemChanged(position);
                        lastSelectIndex = position;
                    } else if (selectImageList.size() < maxSelect) {
                        selectImageList.add(fileItem);
                        notifyItemChanged(position);
                        lastSelectIndex = position;
                    } else {
                        ToastUtils.show("最多选中" + maxSelect + "张");
                    }

                    if (selectImageListener != null) {
                        selectImageListener.imageSelect(selectImageList.size(), maxSelect, selectImageList);
                    }
                }
            });
            holder.mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectImageListener != null) {
                        selectImageListener.imageSelectPreview(maxSelect, selectImageList,imageList);
                    }
                }
            });
            if (selectImageList.contains(fileItem)) {
                holder.mark.setAlpha(0.8f);
                holder.image_select.setImageResource(R.drawable.icon_image_select);
            } else {
                holder.mark.setAlpha(0.2f);
                holder.image_select.setImageResource(R.drawable.icon_image_un_select);
            }

            Glide.with(context).load(new File(fileItem.getPath()))
                    .apply(requestOptions)
                    .into(holder.image_view);

        }

    }

    @Override
    public int getItemCount() {
        if (imageList != null) {
            return imageList.size();
        }
        return 0;
    }

    public interface SelectImageListener {
        void imageSelect(int current, int max, ArrayList<Image> images);
        void imageSelectPreview( int max, ArrayList<Image> selectImages,ArrayList<Image> allImage);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView mark, image_view, image_select;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mark = itemView.findViewById(R.id.mark);
            image_view = itemView.findViewById(R.id.image_view);
            image_select = itemView.findViewById(R.id.image_select);
        }
    }
}
