package com.wisn.mainmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.entity.bean.Folder;
import com.wisn.mainmodule.entity.bean.Image;

import java.io.File;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/2/5 15:11
 */


public class SelectImageFolderAdapter extends RecyclerView.Adapter<SelectImageFolderAdapter.ImageViewHolder> {

    private  LayoutInflater inflater;
    private Context context;
    private List<Folder> imageFolderList;
    private final RequestOptions requestOptions;
    private int selectIndex=0;
    private SelectImageFolderListener  selectImageFolderListener;
    public SelectImageFolderAdapter(Context context ){
        this.context=context;
        inflater = LayoutInflater.from(context);
        requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE);
    }
    public void refresh(List<Folder> fileItems){
        this.imageFolderList=fileItems;
        notifyDataSetChanged();
    }

    public void setSelectImageFolderListener(SelectImageFolderListener selectImageFolderListener) {
        this.selectImageFolderListener = selectImageFolderListener;
    }

    @Override
    public SelectImageFolderAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view= inflater.inflate(R.layout.item_select_image_folder,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        if(imageFolderList!=null){

            final Folder imgFolderBean = imageFolderList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectIndex==position)return ;
                    int temp=selectIndex;
                    selectIndex=position;
                    notifyItemChanged(temp);
                    notifyItemChanged(selectIndex);
                    selectImageFolderListener.select(imgFolderBean.getName(),imgFolderBean.getImages());
                }
            });
            Glide.with(context).load(new File(imgFolderBean.getImages().get(0).getPath()))
                    .apply(requestOptions)
                    .into(holder.iv_image);
            holder.tv_folder_name.setText(imgFolderBean.getName());
            holder.tv_folder_size.setText(imgFolderBean.getImages().size()+"张");
            if(selectIndex==position){
                holder.iv_select.setImageResource(R.drawable.icon_image_select);
            }else {
                holder.iv_select.setImageResource(R.drawable.icon_image_un_select);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(imageFolderList!=null){
            return imageFolderList.size();
        }
        return 0;
    }
    public interface SelectImageFolderListener{
        void select(String name, List<Image> imageList);
    }
     class ImageViewHolder extends RecyclerView.ViewHolder{

       public  ImageView  iv_image,iv_select;
       public TextView tv_folder_name,tv_folder_size;
        public ImageViewHolder(View itemView) {
            super(itemView);
            iv_image= itemView.findViewById(R.id.iv_image);
            iv_select= itemView.findViewById(R.id.iv_select);
            tv_folder_name= itemView.findViewById(R.id.tv_folder_name);
            tv_folder_size= itemView.findViewById(R.id.tv_folder_size);
        }
    }
}
