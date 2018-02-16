package theagency.vn.support;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator PC on 3/4/2015.
 */
public class ResizeAnimation extends Animation {
    private View mView;
    private float mToHeight;
    private float mFromHeight;

    private float mToWidth;
    private float mFromWidth;

    public ResizeAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight) {
        mToHeight = toHeight;
        mToWidth = toWidth;
        mFromHeight = fromHeight;
        mFromWidth = fromWidth;
        mView = v;
        setDuration(1000);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
     //   interpolatedTime = interpolatedTime+600;
        float height =
                (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
        float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
        ViewGroup.LayoutParams p = mView.getLayoutParams();
        if (mFromWidth!=mToWidth){
            p.width = (int) width;
        }
        if (mFromHeight!=mToHeight){
            p.height = (int) height;
        }


        mView.requestLayout();
    }
}