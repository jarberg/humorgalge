package main.galgeleg.animation;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import main.galgeleg.R;


public class AnimBtnUtil {
    public static void bounce(View btn, Activity activity) {
        final Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 10);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }

    public static void bounceSlow(View btn, Activity activity) {
        final Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.4, 1);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
}
