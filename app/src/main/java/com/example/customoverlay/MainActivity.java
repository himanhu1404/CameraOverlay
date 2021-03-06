package com.example.customoverlay;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Context;

import com.testfairy.TestFairy;

/**
 * Gives a simple UI that detects if this device has a camera,
 * informing the user if they do or dont
 *
 * This also receives the result of a picture being taken and displays it to the user
 *
 * @author paul.blundell
 *
 */
public class MainActivity extends Activity {

    private static final int REQ_CAMERA_IMAGE = 123;
    private static String imagePath = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String message = "";
        if(cameraNotDetected()){
            message = "No camera detected, clicking the button below will have unexpected behaviour.";
        }
        TextView cameraDescriptionTextView = (TextView) findViewById(R.id.text_view_camera_description);
        cameraDescriptionTextView.setText(message);
    }

    private boolean cameraNotDetected() {
        return !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @FromXML
    public void onUseCameraClick(View button){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, REQ_CAMERA_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_OK){
            String imgPath = data.getStringExtra(CameraActivity.EXTRA_IMAGE_PATH);
            Log.i("Got image path: "+ imgPath);
            displayImage(imgPath);
            dsplayShareIcon();
            this.imagePath = imgPath;
        } else
        if(requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_CANCELED){
            Log.i("User didn't take an image");
        }
    }

    private void displayImage(String path) {
        ImageView imageView = (ImageView) findViewById(R.id.image_view_captured_image);
        imageView.setImageBitmap(BitmapHelper.decodeSampledBitmap(path, 300, 250));
    }

    private void dsplayShareIcon(){
        Button button = (Button)findViewById(R.id.shareButton);
        button.setVisibility(View.VISIBLE);
    }

    @FromXML
    public void onShare(View button){
        if(null==imagePath){
            return;
        }
        Intent shareIntent = ShareIntent.shareImage(MyApplication.getAppContext(), imagePath);
    }
}
