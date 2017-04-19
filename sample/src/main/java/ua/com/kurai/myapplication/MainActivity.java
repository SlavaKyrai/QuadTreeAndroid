package ua.com.kurai.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import ua.com.kurai.quadtreeandroid.OnQuadTreeSplitComplete;
import ua.com.kurai.quadtreeandroid.QuadTreeImageView;
import ua.com.kurai.quadtreeandroid.QuadTreeRect;
import ua.com.kurai.quadtreeandroid.QuadTreeSplitter;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        QuadTreeImageView imageView = (QuadTreeImageView) findViewById(R.id.qtImgView);
        imageView.setDrawGreed(true);

        Bitmap mutableBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.testcolor, options);
        mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(mutableBitmap);

        QuadTreeSplitter initialGrid = new QuadTreeSplitter(mutableBitmap, new OnQuadTreeSplitComplete() {
            @Override
            public void onSplitComplete(List<QuadTreeRect> quadTreeRects) {
                Log.d(TAG, "onSplitComplete: " + quadTreeRects.size());
            }
        });
        initialGrid.setMinQuadAreaSize(100);
        initialGrid.setMinColorDistance(10);
        initialGrid.setOnQuadDrawListener(imageView);
        initialGrid.start();

    }
}
