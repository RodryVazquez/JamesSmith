/**
 * @author
 * @version
 * @see
 */

package com.example.rodrigovazquez.jamessmithproject.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageHelper {

    /**
     *
     * @param url
     * @return
     */
    public static Bitmap GetBitmap(String url){

        try {

            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return  myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
