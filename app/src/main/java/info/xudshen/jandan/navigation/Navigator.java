package info.xudshen.jandan.navigation;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.R;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.activity.ItemReaderActivity;
import info.xudshen.jandan.view.activity.TransitionHelper;

/**
 * Created by xudshen on 16/1/6.
 */
@Singleton
public class Navigator {
    @Inject
    public Navigator() {
    }

    public void launchItemReader(BaseActivity fromActivity, View fromView) {
        ViewCompat.setTransitionName(fromView, "profile");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition changeTransform = TransitionInflater.from(fromActivity).
                    inflateTransition(R.transition.change_image_trans);
            Transition explodeTransform = TransitionInflater.from(fromActivity).
                    inflateTransition(android.R.transition.explode);

            fromActivity.getWindow().setEnterTransition(explodeTransform);
            fromActivity.getWindow().setExitTransition(explodeTransform);

            fromActivity.getWindow().setSharedElementEnterTransition(changeTransform);
            fromActivity.getWindow().setSharedElementExitTransition(changeTransform);
        }

        Intent intent = new Intent(fromActivity, ItemReaderActivity.class);
        ActivityOptionsCompat options = TransitionHelper.makeOptionsCompat(
                fromActivity, Pair.create(fromView, "profile"));
        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());
    }
}
