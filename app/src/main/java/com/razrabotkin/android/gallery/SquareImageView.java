package com.razrabotkin.android.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {
	
	public SquareImageView(Context context) {
		super(context);
	}

	public SquareImageView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public SquareImageView(Context context, AttributeSet attributeSet, int defStyle) {
		super(context, attributeSet, defStyle);
	}    

	@Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
