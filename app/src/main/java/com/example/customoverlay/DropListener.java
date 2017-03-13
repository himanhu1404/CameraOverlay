package com.example.customoverlay;

import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;

/**
 * Created by shimans on 2/22/17.
 */

public class DropListener implements OnDragListener {

    View draggedView;
    ImageView dropped;

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Log.d("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                draggedView = (View) event.getLocalState();
                dropped = (ImageView) draggedView;
                draggedView.setVisibility(View.INVISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:

                float X = event.getX();
                float Y = event.getY();

                //Log.d(LOGCAT, "X " + (int) X + "Y " + (int) Y);
                View view = (View) event.getLocalState();
                view.setX(X-(view.getWidth()/2));
                view.setY(Y-(view.getHeight()/2));
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }

}

