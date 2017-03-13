package com.example.customoverlay;

import android.hardware.Camera;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Used to make camera use in the tutorial a bit more obvious
 * in a production environment you wouldn't make these calls static
 * as you have no way to mock them for testing
 * @author paul.blundell
 *
 */
public class CameraHelper {

    public static boolean cameraAvailable(Camera camera) {
        return camera != null;
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            // Camera is not available or doesn't exist
            Log.d("getCamera failed", String.valueOf(e));
        }
        return c;
    }

    public static Camera getCameraInstance(int currentCameraId) {
        Camera c = null;
        try {
            c = Camera.open(currentCameraId);
        } catch (Exception e) {
            // Camera is not available or doesn't exist
            Log.d("getCamera failed", String.valueOf(e));
        }
        return c;
    }


    public static float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float tempx = x*x;
        float tempy = y*y;
        float temp = tempx + tempy;
        return (float) Math.sqrt(temp);
    }

    public boolean onDragFunction(View v, DragEvent e) {
        /*if (e.getAction()==DragEvent.ACTION_DROP) {
            View view = (View) e.getLocalState();
            if(view.getId()==R.id.squareImage1 && v.getId()==R.id.squareImage)
            {
                ViewGroup from = (ViewGroup) view.getParent();
                from.removeView(view);
                v.setBackground(@drawable/dragsquare1);//TODO: change this pseudo code.
                return true;
            } else if(view.getId()==R.id.circleImage1 && v.getId()==R.id.circleImage){
                ViewGroup from = (ViewGroup) view.getParent();
                from.removeView(view);
                v.setBackground(@drawable/dragcircle1);//TODO: change this pseudo code.
                return true;
            } else if(view.getId()==R.id.triangleImage1 && v.getId()==R.id.triangleImage){
                ViewGroup from = (ViewGroup) view.getParent();
                from.removeView(view);
                v.setBackground(@drawable/dragtriangle1);//TODO: change this pseudo code.
                return true;
            } else {
                return false;
            }

        }*/
        com.example.customoverlay.Log.d("+++++++++++++++++++++++++++++++++++++++++++++");
        return true;

    }

}

