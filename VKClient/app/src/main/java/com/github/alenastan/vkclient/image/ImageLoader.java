package com.github.alenastan.vkclient.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.os.Handler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by lena on 19.12.2014.
 */
public class ImageLoader {

    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    ExecutorService executorService;
    Handler handler = new Handler();

    private Map<ImageView, String> imageViews = Collections.synchronizedMap(
            new WeakHashMap<ImageView, String>());


    public ImageLoader(Context context){
        fileCache = new FileCache(context);
        executorService=Executors.newFixedThreadPool(5);

    }

    public void DisplayImage(String url, ImageView imageView){
        //Put image and url in Map
        imageViews.put(imageView, url);
        //Check if image is already saved
        Bitmap bitmap = memoryCache.get(url);
        if(bitmap!=null){
            // if image is saved
            imageView.setImageBitmap(bitmap);
        }else{

            queuePhoto(url, imageView);

        }
    }

    private void queuePhoto(String url, ImageView imageView){

        PhotoToLoad p = new PhotoToLoad(url, imageView);
            executorService.submit(new PhotosLoader(p));
    }


    private class PhotoToLoad {

        public String url;
        public ImageView imageView;
        public PhotoToLoad(String url, ImageView imageView){
            this.url=url;
            this.imageView=imageView;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }

        @Override
        public void run() {
            try{
                //if image is already downloaded
                if(imageViewReused(photoToLoad)) {
                    return;
                }
                Bitmap bmp = getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);

                if(imageViewReused(photoToLoad)) {
                    return;
                }
                BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);

            }catch(Throwable e){
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmap(String url){
        File f=fileCache.getFile(url);

        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        try {

            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
//            conn.setConnectTimeout(30000);
//            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();

            bitmap = decodeFile(f);

            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    private Bitmap decodeFile(File f){

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,options);
            stream1.close();

            final int REQUIRED_SIZE=85;

            int width_tmp=options.outWidth, height_tmp=options.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2 < REQUIRED_SIZE || height_tmp/2 < REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, options2);
            stream2.close();
            return bitmap;

        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){

        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))//if url exist
            return true;
        return false;
    }

    //display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;

             if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);

        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
}
