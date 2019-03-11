package com.nagainfo.mobiremit.model.News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joy on 04/07/17.
 */

public class NewsResultItem {
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Matter")
    @Expose
    private String matter;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
