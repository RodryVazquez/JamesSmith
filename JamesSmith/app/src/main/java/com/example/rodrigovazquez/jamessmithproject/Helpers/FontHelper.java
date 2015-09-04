/**
 * @author
 * @version
 * @see
 */

package com.example.rodrigovazquez.jamessmithproject.Helpers;
import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {

    /**
     *
     * @param fromAsset
     * @param context
     * @return
     */
    public static Typeface getTypeFace(Typeface fromAsset,Context context){

        if(fromAsset == null){

            fromAsset = Typeface.createFromAsset(context.getAssets(),"font/RobotoCondensed-Regular.ttf");
        }

        return fromAsset;
    }

}
