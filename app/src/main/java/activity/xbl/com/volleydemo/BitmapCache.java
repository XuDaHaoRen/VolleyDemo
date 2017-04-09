package activity.xbl.com.volleydemo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by April on 2017/4/8.
 * 重新定义ImageCache进行图片缓存
 * 1.定义cache的大小
 * 2.得到Bitmap的大小并返回
 * 3.实现两个抽象方法：getBitmap得到图片
 * putBitmap放入Cache中
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> bitmapLruCache;

    public BitmapCache() {
        //定义LruCache的大小，并返回到用户定义的大小中
        int maxSize = 10 * 1024 * 1024;
        bitmapLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //得到Bitmap的大小
                return value.getRowBytes() * value.getHeight();

            }
        };

    }

    @Override
    public Bitmap getBitmap(String url) {
        //得到图片
        return bitmapLruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //保存到Lru里面
        bitmapLruCache.put(url, bitmap);


    }
}
