package ua.com.kurai.quadtreeandroid;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;


public class QuadTreeRect {

   private Rect mainRect;
   private QuadTreeRect leftTop;
   private QuadTreeRect rightTop;
   private QuadTreeRect leftBottom;
   private QuadTreeRect rightBottom;
   private int colorDistance;

   QuadTreeRect(Rect mainRect) {
       this.mainRect = mainRect;
   }


   void setColorDistance(int colorDistance) {
       this.colorDistance = colorDistance;
   }

   private QuadTreeRect(int left, int top, int right, int bottom) {
       this.mainRect = new Rect(left, top, right, bottom);
   }

   int getColorDistance() {
       return colorDistance;
   }

    /**
     * @return ArrayList of rect childs
     */
   List<QuadTreeRect> getChilds() {
       List<QuadTreeRect> childs = new ArrayList<>();
       childs.add(this.getLeftTop());
       childs.add(this.getLeftBottom());
       childs.add(this.getRightBottom());
       childs.add(this.getRightTop());
       return childs;
   }

    /**
     * Create 4 Childs of rect
     */
   private void createDescendants() {
       leftTop = new QuadTreeRect(mainRect.left, mainRect.top, mainRect.centerX(), mainRect.centerY());
       rightTop = new QuadTreeRect(mainRect.centerX(), mainRect.top, mainRect.right, mainRect.centerY());
       leftBottom = new QuadTreeRect(mainRect.left, mainRect.centerY(), mainRect.centerX(), mainRect.bottom);
       rightBottom = new QuadTreeRect(mainRect.centerX(), mainRect.centerY(), mainRect.right, mainRect.bottom);

   }

    /**
     * @return graphic rect;
     */
   Rect getGraphicRect() {
       return mainRect;
   }

   private QuadTreeRect getLeftTop() {
       if (leftTop == null) createDescendants();
       return leftTop;
   }

   private QuadTreeRect getRightTop() {
       if (rightTop == null) createDescendants();
       return rightTop;
   }

   private QuadTreeRect getLeftBottom() {
       if (leftBottom == null) createDescendants();
       return leftBottom;
   }

   private QuadTreeRect getRightBottom() {
       if (rightBottom == null) createDescendants();
       return rightBottom;
   }
}
