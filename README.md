# QuadTreeAndroid

![alt tag](http://i.imgur.com/9fz1O2O.gif)
![alt tag](http://i.imgur.com/r1wfHfE.gif)

Library that helps to implement the QuadTree in android, for example, splitting images.

### Download

Download via Gradle:

```gradle
compile 'com.github.slavakyrai:quadtreeandroidimpl:0.1.0'
```
or Maven:
```xml
<dependency>
  <groupId>com.github.slavakyrai</groupId>
  <artifactId>quadtreeandroidimpl</artifactId>
  <version>0.1.0</version>
  <type>pom</type>
</dependency>
```
### Usage

Create an instance of the QuadSplitter class. In the class constructor, pass the bitmap and OnQuadTreeSplitComplete callback.
Result - list of QuadTreeRect will be returned in onSplitComplete in another thread. To start the work, use the method - start.
```java
QuadTreeSplitter quadTreeSplitter = new QuadTreeSplitter(mutableBitmap, new OnQuadTreeSplitComplete() {
    @Override
    public void onSplitComplete(List<QuadTreeRect> quadTreeRects) {
        Log.d(TAG, "onSplitComplete: " + quadTreeRects.size());
      }
    });
quadTreeSplitter.start();
```
You can customize the work of the QuadSplitter class by using the setMinQuadAreaSize and setMinColorDistance methods.
```java
quadTreeSplitter.setMinQuadAreaSize(50);
quadTreeSplitter.setMinColorDistance(5);
```
If you want to display the process of dividing squares, use the QuadTreeImageView class, which is extended by the ImageView. In doing so, you must use the setImageBitmap() method, the QuadTreeImageView class, and the setOnQuadDrawListener() method of the QuadTreeSplitter class. Using the imageView.setDrawGreed (true) method, you can enable or disable the drawing of rectangle borders
```java
QuadTreeImageView imageView = (QuadTreeImageView) findViewById(R.id.qtImgView);
imageView.setImageBitmap(mutableBitmap);
quadTreeSplitter.setOnQuadDrawListener(imageView);
imageView.setDrawGreed(true);
```
