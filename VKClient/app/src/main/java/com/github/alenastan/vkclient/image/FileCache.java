package com.github.alenastan.vkclient.image;

import android.content.Context;

import java.io.File;

/**
 * Created by lena on 18.12.2014.
 */
public class FileCache {
    private File cacheDir;

    public FileCache(Context context){

        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)){

            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),"ImageList");
        }else{
            cacheDir=context.getCacheDir();
        }

        if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url){
        //Identify images by hashcode or encode by URLEncoder.encode.
        String filename=String.valueOf(url.hashCode());

        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear(){
        // list all files inside cache directory
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        //delete all cache directory files
        for(File f:files)
            f.delete();
    }
}
