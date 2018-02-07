package com.wisn.mainmodule.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Wisn
 * @time 2018/2/5 17:29
 */


public class Image  implements Parcelable {

    private String path;
    private long time;
    private String name;

    public Image(String path, long time, String name) {
        this.path = path;
        this.time = time;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (time != image.time) return false;
        if (path != null ? !path.equals(image.path) : image.path != null) return false;
        return name != null ? name.equals(image.name) : image.name == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.time);
        dest.writeString(this.name);
    }

    protected Image(Parcel in) {
        this.path = in.readString();
        this.time = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}
