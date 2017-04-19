package ua.com.kurai.quadtreeandroid;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.IntRange;

import java.util.ArrayList;
import java.util.List;


public class QuadTreeSplitter extends Thread {

    private int minQuadAreaSize = 100; // default 100, means quad 10x10 pixels
    private int minColorDistance = 10; // minimal color distance for splitting
    private int[][] pixelsBitmap;
    private List<QuadTreeRect> quadsInWorkList;
    private List<QuadTreeRect> resultQuadsList;
    private OnQuadDrawListener onQuadDraw;
    private OnQuadTreeSplitComplete onQuadTreeSplitComplete;
    private Bitmap bitmap;


    public QuadTreeSplitter(Bitmap bitmap, OnQuadTreeSplitComplete onQuadTreeSplitComplete) {
        this.onQuadTreeSplitComplete = onQuadTreeSplitComplete;
        this.quadsInWorkList = new ArrayList<>();
        this.resultQuadsList = new ArrayList<>();
        this.bitmap = bitmap;
        pixelsBitmap = getPixelsFromBitmap(bitmap);
    }

    /**
     * The smaller the rectangle, the better the output image, but longer splitting.
     * recommended value about 100
     */
    public void setMinColorDistance(@IntRange(from = 1, to = Integer.MAX_VALUE) int minColorDistance) {
        this.minColorDistance = minColorDistance;
    }

    /**
     * The smaller color distance, the better the output image, but longer splitting.
     * recommended value about 5-10
     */
    public void setMinQuadAreaSize(@IntRange(from = 1, to = Integer.MAX_VALUE) int minQuadAreaSize) {
        this.minQuadAreaSize = minQuadAreaSize;
    }

    public void setOnQuadDrawListener(OnQuadDrawListener onQuadDraw) {
        this.onQuadDraw = onQuadDraw;
    }

    /**
     * get pixel from bitmap and convert it in to two-dimensional array
     */
    private int[][] getPixelsFromBitmap(Bitmap bitmap) {
        int[] imagePixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        int[][] pixelsBitmap = new int[bitmap.getWidth()][bitmap.getHeight()];

        bitmap.getPixels(imagePixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                pixelsBitmap[i][j] = imagePixels[(j * bitmap.getWidth()) + i];
            }
        }
        return pixelsBitmap;
    }



    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QuadTreeRect quadTreeRect = new QuadTreeRect(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        // create first 4 childs and add it to work array
        createTreeChilds(quadTreeRect);
        while (quadsInWorkList.size() > 0) {
            findLargestColorDist();
        }
        onQuadTreeSplitComplete.onSplitComplete(resultQuadsList);
    }


    /**
     * Create 4 childs of rect
     */
    private void createTreeChilds(QuadTreeRect quadTreeRect) {
        List<QuadTreeRect> treeChilds = quadTreeRect.getChilds();
        for (int i = 0; i < treeChilds.size(); i++) {
            Rect graphicRect = treeChilds.get(i).getGraphicRect();
            int colorDistance = calcColorDistance(treeChilds.get(i).getGraphicRect());
            int averageColor = averageColor(graphicRect);
            treeChilds.get(i).setColorDistance(colorDistance);
            // check callback and draw rect
            if (onQuadDraw != null) {
                onQuadDraw.onQuadDraw(graphicRect, averageColor);
            }
            if ( (colorDistance <= minColorDistance) || (graphicRect.width() * graphicRect.height() < minQuadAreaSize)) {
                // add rect to result array, which be returned
                resultQuadsList.add(treeChilds.get(i));
            } else {
                // rectangle should be divided into smaller parts
                quadsInWorkList.add(treeChilds.get(i));
            }
        }
    }


    /**
     * @return average color inside rect
     */
    private int averageColor(Rect rect) {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int i = rect.left; i < rect.right; i++) {
            for (int j = rect.top; j < rect.bottom; j++) {
                redSum += Color.red(pixelsBitmap[i][j]);
                greenSum += Color.green(pixelsBitmap[i][j]);
                blueSum += Color.blue(pixelsBitmap[i][j]);
            }
        }
        int area = rect.width() * rect.height();
        return Color.rgb(redSum / area, greenSum / area, blueSum / area);
    }


    /**
     * Calc color distance between average color in rect
     * and each pixel
     */
    private int calcColorDistance(Rect rect) {
        int averageColor = averageColor(rect);
        int red = Color.red(averageColor);
        int green = Color.green(averageColor);
        int blue = Color.blue(averageColor);
        int colorSum = 0;

        // Calculates the distance
        for (int i = rect.left; i < rect.right; i++) {
            for (int j = rect.top; j < rect.bottom; j++) {
                int cellColor = pixelsBitmap[i][j];
                colorSum += Math.abs(red - Color.red(cellColor));
                colorSum += Math.abs(green - Color.green(cellColor));
                colorSum += Math.abs(blue - Color.blue(cellColor));
            }
        }

        // Calculates distance, and returns the result.
        return colorSum / (3 * (rect.width() * rect.height())); //divide by 3, because use R. G. B.
    }


    /**
     * find rect with largest color distance
     * remove it from list and split it.
     */
    private void findLargestColorDist() {
        QuadTreeRect largestQuad = quadsInWorkList.get(0);

        for (int i = 0; i < quadsInWorkList.size(); i++) {
            if (quadsInWorkList.get(i).getColorDistance() >= largestQuad.getColorDistance()) {
                largestQuad = quadsInWorkList.get(i);
            }
        }
        // Removing an element from an array. When the size of the array becomes 0, calculations will stop
        quadsInWorkList.remove(largestQuad);
        createTreeChilds(largestQuad);
    }


}
