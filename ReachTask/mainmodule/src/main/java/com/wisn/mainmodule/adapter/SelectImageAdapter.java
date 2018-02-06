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
import java.util.List;

/**
 * @author Wisn
 * @time 2018/2/5 15:11
 */


public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ImageViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private int maxSelect;
    private List<Image> imageList;
    private List<Image> selectImageList;
    private final RequestOptions requestOptions;
    private SelectImageListener  selectImageListener;
    public SelectImageAdapter(Context context, int maxSelect) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.maxSelect = maxSelect;
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
        selectImageList=new ArrayList<>();
    }

    public void setSelectImageListener(SelectImageListener selectImageListener) {
        this.selectImageListener = selectImageListener;
    }

    public void refresh(List<Image> fileItems) {
        this.imageList = fileItems;
        notifyDataSetChanged();
    }

    @Override
    public SelectImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_select_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        if (imageList != null) {
            final Image fileItem = imageList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectImageList.contains(fileItem)){
                        selectImageList.remove(fileItem);
                        notifyItemChanged(position);
                    }else{
                        if(selectImageList.size()<maxSelect){
                            selectImageList.add(fileItem);
                            notifyItemChanged(position);
                        }else{
                            ToastUtils.show("最多选中"+maxSelect+"张");
                        }
                    }
                    if(selectImageListener!=null){
                        selectImageListener.select(selectImageList.size(),maxSelect,selectImageList);
                    }
                }
            });
            if(selectImageList.contains(fileItem)){
                holder.mark.setAlpha(0.8f);
                holder.image_select.setImageResource(R.drawable.icon_image_select);
            }else{
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

    public interface  SelectImageListener{
        void select(int current, int max, List<Image> images);
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
