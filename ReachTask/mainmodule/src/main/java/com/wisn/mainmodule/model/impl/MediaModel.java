package com.wisn.mainmodule.model.impl;

import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;

import com.wisn.mainmodule.app.MApplication;
import com.wisn.mainmodule.entity.bean.AppInfo;
import com.wisn.mainmodule.entity.bean.ImgFolderBean;
import com.wisn.mainmodule.entity.bean.Music;
import com.wisn.mainmodule.entity.bean.Video;
import com.wisn.mainmodule.model.IMediaModel;
import com.wisn.utils.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/2/5 11:56
 */


public class MediaModel implements IMediaModel {
    public ContentResolver mContentResolver;

    public MediaModel() {
        mContentResolver = MApplication.getInstance().getContentResolver();
    }

    public List<ImgFolderBean> getImageFolders() {

        List<ImgFolderBean> folders = new ArrayList<ImgFolderBean>();
        // 扫描图片
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Images.Media.MIME_TYPE + "= ? or " + MediaStore.Images.Media.MIME_TYPE + "= ?",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            List<String> mDirs = new ArrayList<String>();//用于保存已经添加过的文件夹目录
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));// 路径
                File parentFile = new File(path).getParentFile();
                if (parentFile == null)
                    continue;

                String dir = parentFile.getAbsolutePath();
                if (mDirs.contains(dir))//如果已经添加过
                    continue;

                mDirs.add(dir);//添加到保存目录的集合中
                ImgFolderBean folderBean = new ImgFolderBean();
                folderBean.setDir(dir);
                folderBean.setFistImgPath(path);
                if (parentFile.list() == null)
                    continue;
                int count = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png")) {
                            return true;
                        }
                        return false;
                    }
                }).length;

                folderBean.setCount(count);
                folders.add(folderBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return folders;
    }

    @Override
    public List<String> getImgListByDir(String dir) {
        ArrayList<String> imgPaths = new ArrayList<>();
        File directory = new File(dir);
        if (directory == null || !directory.exists()) {
            return imgPaths;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            String path = file.getAbsolutePath();
            if (FileUtils.isPicFile(path)) {
                imgPaths.add(path);
            }
        }
        return imgPaths;
    }

    @Override
    public List<AppInfo> getAppInfos() {

        ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
        //获取到包的管理者
        PackageManager packageManager = MApplication.getInstance().getPackageManager();
        //获得所有的安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        //遍历每个安装包，获取对应的信息
        for (PackageInfo packageInfo : installedPackages) {

            AppInfo appInfo = new AppInfo();

            appInfo.setApplicationInfo(packageInfo.applicationInfo);
            appInfo.setVersionCode(packageInfo.versionCode);

            //得到icon
            Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
            appInfo.setIcon(drawable);

            //得到程序的名字
            String apkName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setApkName(apkName);

            //得到程序的包名
            String packageName = packageInfo.packageName;
            appInfo.setApkPackageName(packageName);

            //得到程序的资源文件夹
            String sourceDir = packageInfo.applicationInfo.sourceDir;
            File file = new File(sourceDir);
            //得到apk的大小
            long size = file.length();
            appInfo.setApkSize(size);

            System.out.println("---------------------------");
            System.out.println("程序的名字:" + apkName);
            System.out.println("程序的包名:" + packageName);
            System.out.println("程序的大小:" + size);


            //获取到安装应用程序的标记
            int flags = packageInfo.applicationInfo.flags;

            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //表示系统app
                appInfo.setUserApp(false);
            } else {
                //表示用户app
                appInfo.setUserApp(true);
            }

            if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                //表示在sd卡
                appInfo.setRom(false);
            } else {
                //表示内存
                appInfo.setRom(true);
            }
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    @Override
    public List<Music> getMusics() {
        ArrayList<Music> musics = new ArrayList<>();
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径

                if (!new File(path).exists()) {
                    continue;
                }

                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                String album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)); // 专辑
                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)); // 作者
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
                int time = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 歌曲的id
                // int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                Music music = new Music(name, path, album, artist, size, duration);
                musics.add(music);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return musics;
    }

    @Override
    public List<Video> getVideos() {
        List<Video> videos = new ArrayList<Video>();

        Cursor c = null;
        try {
            // String[] mediaColumns = { "_id", "_data", "_display_name",
            // "_size", "date_modified", "duration", "resolution" };
            c = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                if (!new File(path).exists()) {
                    continue;
                }

                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                String resolution = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                long date = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));//修改时间

                Video video = new Video(id, path, name, resolution, size, date, duration);
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return videos;
    }

    @Override
    public Bitmap getVideoThumbnail(int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
        return bitmap;
    }
}
