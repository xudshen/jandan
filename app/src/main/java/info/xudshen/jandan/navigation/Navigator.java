package info.xudshen.jandan.navigation;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Singleton;

import info.xudshen.jandan.R;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.activity.ItemReaderActivity;
import info.xudshen.jandan.view.activity.JandanSettingActivity;
import info.xudshen.jandan.view.activity.TransitionHelper;

/**
 * Created by xudshen on 16/1/6.
 */
@Singleton
public class Navigator {
    @Inject
    public Navigator() {
    }

    /**
     * temporary disable this
     */
    public void launchItemReader(BaseActivity fromActivity, View fromView, int position, ReaderItemType readerItemType) {
        ViewCompat.setTransitionName(fromView, fromActivity.getString(R.string.launch_item_reader_transition));

        Intent intent = new Intent(fromActivity, ItemReaderActivity.class);
        intent.putExtra(ItemReaderActivity.ARG_POSITION, position);
        intent.putExtra(ItemReaderActivity.ARG_READER_TYPE, readerItemType);

        ActivityOptionsCompat options = TransitionHelper.makeOptionsCompat(
                fromActivity, Pair.create(fromView, "profile"));
        ActivityCompat.startActivity(fromActivity, intent, options.toBundle());

//        fromActivity.overridePendingTransition(R.anim.slide_up, R.anim.scale_down);
    }

    public void launchSetting(BaseActivity fromActivity) {
        Intent intent = new Intent(fromActivity, JandanSettingActivity.class);
        ActivityCompat.startActivityForResult(fromActivity, intent, JandanSettingActivity.REQUEST_CODE, null);
    }
}
