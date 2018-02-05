package com.wisn.mainmodule.model;

import android.graphics.Bitmap;

import com.wisn.mainmodule.entity.bean.AppInfo;
import com.wisn.mainmodule.entity.bean.ImgFolderBean;
import com.wisn.mainmodule.entity.bean.Music;
import com.wisn.mainmodule.entity.bean.Video;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/2/5 11:53
 */


public interface IMediaModel  {
    /**
     * 获取手机图片的文件夹
     * @return
     */
    List<ImgFolderBean> getImageFolders();

    /**
     * 获取每个图片文件夹中的图片路径
     * @param dir
     * @return
     */
    List<String> getImgListByDir(String dir);

    /**
     * 获取所有安装过的apkinfo
     * @return
     */
    List<AppInfo> getAppInfos();

    /**
     * 获取音乐列表
     * @return
     */
    List<Music> getMusics();

    /**
     * 获取所有视频
     * @return
     */
    List<Video> getVideos();

    /**
     * 视频缩略图
     * @param id
     * @return
     */
    Bitmap getVideoThumbnail(int id);
}
