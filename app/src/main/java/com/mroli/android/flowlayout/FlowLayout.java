package com.mroli.android.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

  public FlowLayout(Context context) {
    super(context);
  }

  public FlowLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = resolveSize(0, widthMeasureSpec);

    int paddingLeft = getPaddingLeft();
    int paddingTop = getPaddingTop();
    int paddingRight = getPaddingRight();
    int paddingBottom = getPaddingBottom();

    int childLeft = paddingLeft;
    int childTop = paddingTop;

    int lineHeight = 0;

    for (int i = 0; i < getChildCount(); i++) {
      View v = getChildAt(i);

      if (v.getVisibility() == GONE) {
        continue;
      } else {
        measureChild(v, widthMeasureSpec, heightMeasureSpec);
      }

      int w = v.getMeasuredWidth();
      int h = v.getMeasuredHeight();

      lineHeight = Math.max(lineHeight, h);

      if (childLeft + w + paddingRight <= width) {
        childLeft += paddingRight + w;
      } else {
        childLeft = paddingLeft;
        childTop += lineHeight + paddingBottom;
        lineHeight = h;
      }
    }

    int targetHeight = childTop + lineHeight + paddingBottom;

    setMeasuredDimension(width, resolveSize(targetHeight, heightMeasureSpec));
  }

  @Override protected void onLayout(boolean bo, int l, int t, int r, int b) {
    int width = r - l;

    int paddingLeft = getPaddingLeft();
    int paddingTop = getPaddingTop();
    int paddingRight = getPaddingRight();
    int paddingBottom = getPaddingBottom();

    int childLeft = paddingLeft;
    int childTop = paddingTop;

    int lineHeight = 0;

    for (int i = 0; i < getChildCount(); i++) {
      View v = getChildAt(i);

      if (v.getVisibility() == GONE) continue;

      int w = v.getMeasuredWidth();
      int h = v.getMeasuredHeight();

      lineHeight = Math.max(lineHeight, h);

      if (childLeft + w + paddingRight > width) {
        childLeft = paddingLeft;
        childTop += lineHeight + paddingBottom;
        lineHeight = h;
      }

      v.layout(childLeft, childTop, childLeft + w, childTop + h);
      childLeft += w + paddingLeft;
    }
  }
}
