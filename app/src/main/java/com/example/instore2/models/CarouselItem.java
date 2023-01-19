package com.example.instore2.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CarouselItem  implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("media_type")
    private int media_type;
    @SerializedName("pk")
    private long pk;

    @SerializedName("video_versions")
    private List<VideoVersionModel> videoversions;
    @SerializedName("image_versions2")
    private ImageVersionModel imageversions2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public List<VideoVersionModel> getVideoversions() {
        return videoversions;
    }

    public void setVideoversions(List<VideoVersionModel> videoversions) {
        this.videoversions = videoversions;
    }

    public ImageVersionModel getImageversions2() {
        return imageversions2;
    }

    public void setImageversions2(ImageVersionModel imageversions2) {
        this.imageversions2 = imageversions2;
    }
}
