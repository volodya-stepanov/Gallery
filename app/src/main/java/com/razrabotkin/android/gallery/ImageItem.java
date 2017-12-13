package com.razrabotkin.android.gallery;

import android.graphics.Bitmap;

/**
 * Created by PC on 14.11.2017.
 */

public class ImageItem {
    private Bitmap image;   //TODO: Возможно, это поле можно будет полностью удалить
    private String title;
    private int resourceId;

    public ImageItem(Bitmap image, String title, int resourceId) {
        super();
        this.image = image;
        this.title = title;
        this.resourceId = resourceId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
