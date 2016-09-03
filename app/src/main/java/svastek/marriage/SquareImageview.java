package svastek.marriage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Tatson on 08-01-2016.
 */
public class SquareImageview extends ImageView {
    public SquareImageview(Context context) {
        super(context);
    }

    public SquareImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
