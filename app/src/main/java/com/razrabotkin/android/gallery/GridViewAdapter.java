package com.razrabotkin.android.gallery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.media.ThumbnailUtils.extractThumbnail;

/**
 * Created by PC on 14.11.2017.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            //holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (SquareImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = (ImageItem) data.get(position);
        //holder.imageTitle.setText(item.getTitle());

        //Bitmap thumbnail = ThumbnailUtils.extractThumbnail(item.getImage(), 100, 100);

        //holder.image.setImageBitmap(item.getImage());
        holder.image.setImageResource(item.getImageId());
        //Glide.with(context).load(item.getImageId()).into(holder.image);
        return row;
    }

    static class ViewHolder {
        //TextView imageTitle;
        SquareImageView image;
    }
}
