package ua.com.kurai.quadtreeandroid;

import java.util.List;


public interface OnQuadTreeSplitComplete {
    /**
     * @param quadTreeRects result list
     */
    void onSplitComplete(List<QuadTreeRect> quadTreeRects);
}
