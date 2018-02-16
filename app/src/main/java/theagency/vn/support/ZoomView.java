package theagency.vn.support;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Zooming view.
 */
public class ZoomView extends FrameLayout implements ScaleGestureDetector.OnScaleGestureListener {

private enum Mode {
    NONE,
    DRAG,
    ZOOM
}

private static final String TAG = "ZoomLayout";
private static final float MIN_ZOOM = 1.0f;
private static final float MAX_ZOOM = 4.0f;

private Mode mode = Mode.NONE;
private float scale = 1.0f;
private float lastScaleFactor = 0f;

// Where the finger first  touches the screen
private float startX = 0f;
private float startY = 0f;

// How much to translate the canvas
private float dx = 0f;
private float dy = 0f;
private float prevDx = 0f;
private float prevDy = 0f;
View listView;
Communicator comm;
    public ZoomView(Context context) {
        super(context);
        init(context);
        comm = (Communicator)context;
    }

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        comm = (Communicator)context;
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        comm = (Communicator)context;
    }

    private void init(final Context context) {
        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        comm.respond(1000);
                        if (scale > MIN_ZOOM) {
                            mode = Mode.DRAG;
                            startX = motionEvent.getX() - prevDx;
                            startY = motionEvent.getY() - prevDy;

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == Mode.DRAG) {
                            dx = motionEvent.getX() - startX;
                            dy = motionEvent.getY() - startY;
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mode = Mode.ZOOM;


                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = Mode.DRAG;

                        break;
                    case MotionEvent.ACTION_UP:
                        if (scale==1){
                            comm.respond(2000);
                        }

                        mode = Mode.NONE;
                        prevDx = dx;
                        prevDy = dy;
                        break;
                }
                scaleDetector.onTouchEvent(motionEvent);

                if ((mode == Mode.DRAG && scale >= MIN_ZOOM) || mode == Mode.ZOOM) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float maxDx = (child().getWidth() - (child().getWidth() / scale)) / 2 * scale;
                    float maxDy = (child().getHeight() - (child().getHeight() / scale))/ 2 * scale;
                    dx = Math.min(Math.max(dx, -maxDx), maxDx);
                    dy = Math.min(Math.max(dy, -maxDy), maxDy);

                    applyScaleAndTranslation();
                }





                return true;
            }
        });
    }

    private List<View> getAllViews(View v) {
        if (!(v instanceof ViewGroup) || ((ViewGroup) v).getChildCount() == 0) // It's a leaf
        { List<View> r = new ArrayList<View>(); r.add(v); return r; }
        else {
            List<View> list = new ArrayList<View>(); list.add(v); // If it's an internal node add itself
            int children = ((ViewGroup) v).getChildCount();
            for (int i=0;i<children;++i) {
                list.addAll(getAllViews(((ViewGroup) v).getChildAt(i)));
            }
            return list;
        }
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleDetector) {

        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleDetector) {
        float scaleFactor = scaleDetector.getScaleFactor();

        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
        } else {

            lastScaleFactor = 0;
        }
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleDetector) {

    }

    private void applyScaleAndTranslation() {
        child().setScaleX(scale);
        child().setScaleY(scale);
        child().setTranslationX(dx);
        child().setTranslationY(dy);

    }

    private View child() {
        return getChildAt(0);
    }
}