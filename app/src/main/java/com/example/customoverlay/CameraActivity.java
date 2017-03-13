package com.example.customoverlay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.customoverlay.CameraHelper.cameraAvailable;
import static com.example.customoverlay.CameraHelper.getCameraInstance;
import static com.example.customoverlay.CameraHelper.spacing;
import static com.example.customoverlay.MediaHelper.getOutputMediaFile;
import static com.example.customoverlay.MediaHelper.saveToFile;

/**
 * Takes a photo saves it to the SD card and returns the path of this photo to the calling Activity
 * @author
 *
 */
public class CameraActivity extends Activity implements PictureCallback {

    protected static final String EXTRA_IMAGE_PATH = "com.example.customoverlay.EXTRA_IMAGE_PATH";

    private Camera camera;
    private CameraPreview cameraPreview;
    Bitmap mBitmap;
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    private int _xDelta;
    private int _yDelta;
    float oldDist = 1f;
    float MAX_ZOOM = 3.0f;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();

    String savedItemClicked;
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    int currentCameraId;
    int CurrentVisibleViewID;

    List<Integer> viewIdList;
    boolean appLoadedFirstTime = true;

    //------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setResult(RESULT_CANCELED);
        // Camera may be in use by another activity or the system or not available at all
        camera = getCameraInstance();
        //Back camera is starting
        currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        if(cameraAvailable(camera)){
            initCameraPreview();
        } else {
            finish();
        }
        if(appLoadedFirstTime) {
            CurrentVisibleViewID = R.id.image_view;
            viewIdList = new ArrayList<Integer>();
            addViewIdsToList(viewIdList);

            setTouchListnersForASIN(viewIdList);
            appLoadedFirstTime = false;
            ImageView imageview =(ImageView) findViewById(viewIdList.get(0));
            imageview.setVisibility(View.VISIBLE);
        }
        /*this.findViewById(R.id.image_view).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                return onTouchFunction(v, event);
            }
        });*/

    }

    private void setTouchListnersForASIN(List<Integer> viewIdList){
        for(Integer x : viewIdList) {
            this.findViewById(x).setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return onTouchFunction(v, event);
                }
            });
        }
    }
    private void addViewIdsToList(List<Integer> viewIdList){
        viewIdList.add(R.id.image_view);
        viewIdList.add(R.id.image_view2);
        viewIdList.add(R.id.image_view3);
        viewIdList.add(R.id.image_view4);
        viewIdList.add(R.id.image_view5);
        viewIdList.add(R.id.image_view6);
        viewIdList.add(R.id.image_view7);
        viewIdList.add(R.id.image_view8);
        viewIdList.add(R.id.image_view9);
        viewIdList.add(R.id.image_view10);
        viewIdList.add(R.id.image_view11);
    }


    public boolean onTouchFunction(View view, MotionEvent event) {
        view.setBackgroundColor(10);
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                //start.set(event.getX(), event.getY());
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                mode = DRAG;
                android.util.Log.d("1", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                mode = NONE;
                android.util.Log.d("1", "ACTION_UP");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                savedMatrix.set(matrix);
                oldDist = spacing(event);
                //Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 100f) {
                    mode = ZOOM;
                }
                android.util.Log.d("1", "ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                android.util.Log.d("1", "ACTION_POINTER_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG){
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    //if() {
                    view.setLayoutParams(layoutParams);

                    layoutParams.bottomMargin = -250;
                    /*ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);*/

                }else if(mode == ZOOM){
                    float newDist = spacing(event);
                    //Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        ImageView iView = (ImageView) view;
        iView.setImageMatrix(matrix);
        return true;
    }


    // Show the camera view on the activity
    private void initCameraPreview() {
        cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        cameraPreview.init(camera);
    }

    private void camSwitchSetASINvisibility(){

    }

    @FromXML
    public void onCaptureClick(View button){
        // Take a picture with a callback when the photo has been created
        // Here you can add callbacks if you want to give feedback when the picture is being taken
        camera.takePicture(null, null, this);

    }

    @FromXML
    public void onClickSwitchASIN(View button){
        int i;
        for(i=0;i<viewIdList.size();++i){
            if(viewIdList.get(i)==CurrentVisibleViewID){
                break;
            }
        }
        ImageView imageview =(ImageView) findViewById(viewIdList.get(i));
        imageview.setVisibility(View.INVISIBLE);
        imageview =(ImageView) findViewById(viewIdList.get((i+1)%viewIdList.size()));
        imageview.setVisibility(View.VISIBLE);
        CurrentVisibleViewID = viewIdList.get((i+1)%viewIdList.size());
    }

    @FromXML
    public void onSwitchCamClick(View button){
        if(camera!=null){
            //NB: if you don't release the current camera before switching, you app will crash
            camera.release();
        }
        setContentView(R.layout.activity_camera);
        //Switching between cameras
        if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        camera = CameraHelper.getCameraInstance(currentCameraId);
        initCameraPreview();
        setTouchListnersForASIN(viewIdList);
        setASINVisibility();
    }

    private void setASINVisibility(){
        ImageView imageview =(ImageView) findViewById(CurrentVisibleViewID);
        imageview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d("Picture taken");
        String path = savePictureToFileSystem(data);
        setResult(path);
        finish();
    }

    private static String savePictureToFileSystem(byte[] data) {
        File file = getOutputMediaFile();
        saveToFile(data, file);
        return file.getAbsolutePath();
    }

    private void setResult(String path) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_PATH, path);
        setResult(RESULT_OK, intent);
    }

    // ALWAYS remember to release the camera when you are finished
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if(camera != null){
            camera.release();
            camera = null;
        }
    }
}