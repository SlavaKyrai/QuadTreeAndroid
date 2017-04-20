# QuadTreeAndroid

![alt tag](http://i.imgur.com/9fz1O2O.gif)
![alt tag](http://i.imgur.com/r1wfHfE.gif)

Library that helps to implement the QuadTree in android, by using splitting images

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

Create an instance of the QuadSplitter class. Pass the bitmap and OnQuadTreeSplitComplete callback  in the constructor. The result will be returned as list of QuadTreeRect, in another thread in onSplitComplete().Use the method start() to start splitting image.
```java
QuadTreeSplitter quadTreeSplitter = new QuadTreeSplitter(mutableBitmap, new OnQuadTreeSplitComplete() {
    @Override
    public void onSplitComplete(List<QuadTreeRect> quadTreeRects) {
        Log.d(TAG, "onSplitComplete: " + quadTreeRects.size());
      }
    });
quadTreeSplitter.start();
```
You can customize the work of the QuadSplitter class by using methods setMinQuadAreaSize() and setMinColorDistance().
```java
quadTreeSplitter.setMinQuadAreaSize(50);
quadTreeSplitter.setMinColorDistance(5);
```
If you want to display the separeting image process, use the QuadTreeImageView class, which is extended by the ImageView. For than you must use the next methods: setImageBitmap() method of the QuadTreeImageView class, and setOnQuadDrawListener() method of the QuadTreeSplitter class. You can enable or disable the outlines of the rectangles borders, when you use the setDrawGreed() method.
```java
QuadTreeImageView imageView = (QuadTreeImageView) findViewById(R.id.qtImgView);
imageView.setImageBitmap(mutableBitmap);
quadTreeSplitter.setOnQuadDrawListener(imageView);
imageView.setDrawGreed(true);
```
Look [sample] for more information

### License 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


[sample]: https://github.com/SlavaKyrai/QuadTreeAndroid/tree/master/sample
