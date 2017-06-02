package com.example.android.joyin.Tool;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Mr yin on 2017/4/17.
 */

public class SpecialTextView extends TextView {
    public SpecialTextView(Context context, AttributeSet attrs){
        super(context, attrs);

        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Black.ttf"));
    }
}
