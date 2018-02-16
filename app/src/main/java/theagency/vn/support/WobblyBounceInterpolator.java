package theagency.vn.support;

import android.animation.TimeInterpolator;

/**
 * Created by Administrator PC on 3/17/2015.
 */
public class WobblyBounceInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        double s = (input>1 ? 1 : input)+(Math.sin(2*input*Math.PI)/Math.exp(Math.PI*input));
        return (float) s;
    }
}
