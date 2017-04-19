package ua.com.kurai.quadtreeandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;


public class QuadTreeImageView extends android.support.v7.widget.AppCompatImageView implements OnQuadDrawListener {

    private Canvas canvas;
    private Paint paint;
    private boolean drawGreed;

    public void setDrawGreed(boolean drawGreed) {
        this.drawGreed = drawGreed;
    }

    public QuadTreeImageView(Context context) {
        super(context);
        setPaintConfig();
    }

    public QuadTreeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPaintConfig();
    }

    public QuadTreeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPaintConfig();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        canvas = new Canvas(bm);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postInvalidate();

    }

    @Override
    public void onQuadDraw(Rect rect, int averageColor) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(averageColor);
        canvas.drawRect(rect, paint);

        if (drawGreed) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawRect(rect, paint);
        }

        postInvalidate();
    }

    private void setPaintConfig() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(1);
    }

}
