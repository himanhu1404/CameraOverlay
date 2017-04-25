package com.example.customoverlay;

import android.content.Intent;
import android.os.Build;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;

/**
 * Created by shimans on 4/25/17.
 */

public class ShareIntent {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings( "deprecation" )
    public static Intent shareImage(Context context, String pathToImage) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        else
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

        shareIntent.setType("image/*");

        // For a file in shared storage.  For data in private storage, use a ContentProvider.
        Uri uri = Uri.fromFile(context.getFileStreamPath(pathToImage));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        return shareIntent;
    }
}
