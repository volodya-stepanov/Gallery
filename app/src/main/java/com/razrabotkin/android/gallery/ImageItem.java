package com.razrabotkin.android.gallery;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 14.11.2017.
 */

public class ImageItem implements Parcelable {
    private Bitmap image;   //TODO: Возможно, это поле можно будет полностью удалить
    private String title;
    private int imageId;

    public ImageItem(int imageId, String title) {
        super();
        this.title = title;
        this.imageId = imageId;
    }

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;;
    }

    protected ImageItem(Parcel in) {
        image = in.readParcelable(Bitmap.class.getClassLoader());
        title = in.readString();
        imageId = in.readInt();
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(image, i);
        parcel.writeString(title);
        parcel.writeInt(imageId);
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
