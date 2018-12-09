package com.otikev.codecensus.widget

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet


/**
 * Created by kevin on 08/12/18 at 19:37
 */

class SquareCardView(context: Context, attrs: AttributeSet) : CardView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, ignoredHeightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}