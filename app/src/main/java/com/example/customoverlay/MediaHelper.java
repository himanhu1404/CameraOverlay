package com.example.customoverlay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to make file system use in the tutorial a bit more obvious
 * in a production environment you wouldn't make these calls static
 * as you have no way to mock them for testing
 * @author paul.blundell
 *
 */
public class MediaHelper {

    public static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Spike");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +"IMG_"+ timeStamp +".jpg");

        return mediaFile;
    }

    public static boolean saveToFile(Bitmap newBitmap, File file){
        /*boolean saved = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            saved = true;
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e);
        } catch (IOException e) {
            Log.e("IOException", e);
        }
        return saved;*/

        boolean saved = false;
        try {
            FileOutputStream out = new FileOutputStream(file);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            out.flush();
            out.close();
            saved=true;
        } catch (FileNotFoundException e) {
            Log.d("In Saving File", e );
        } catch (IOException e) {
            Log.d("In Saving File", e );
        }

        return saved;
    }

    public Bitmap combineImages(Bitmap frame, Bitmap image) {
        Bitmap cs = null;
        Bitmap rs = null;

        rs = Bitmap.createScaledBitmap(frame, image.getWidth() + 50,
                image.getHeight() + 50, true);

        cs = Bitmap.createBitmap(rs.getWidth(), rs.getHeight(),
                Bitmap.Config.RGB_565);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(image, 25, 25, null);
        comboImage.drawBitmap(rs, 0, 0, null);
        if (rs != null) {
            rs.recycle();
            rs = null;
        }
        Runtime.getRuntime().gc();
        return cs;
    }

}
