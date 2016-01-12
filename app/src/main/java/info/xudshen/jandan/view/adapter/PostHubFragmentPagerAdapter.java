package info.xudshen.jandan.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.R;
import info.xudshen.jandan.view.fragment.IndicatorFragment;
import info.xudshen.jandan.view.fragment.PostListFragment;

/**
 * Created by xudshen on 16/1/11.
 */
public class PostHubFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(PostHubFragmentPagerAdapter.class);
    final int PAGE_COUNT = 3;
    private Context context;

    public PostHubFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        logger.info(position + "");
        switch (position) {
            case 0: {
                return PostListFragment.newInstance();
            }
            case 1: {
                return IndicatorFragment.newInstance(position);
            }
            case 2: {
                return IndicatorFragment.newInstance(position);
            }
            default: {
                return IndicatorFragment.newInstance(999);
            }
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0: {
                return context.getText(R.string.post_hub_tab_new);
            }
            case 1: {
                return context.getText(R.string.post_hub_tab_ranking);
            }
            case 2: {
                return context.getText(R.string.post_hub_tab_topic);
            }
            default: {
                return context.getText(R.string.post_hub_tab_new);
            }
        }
    }
}
