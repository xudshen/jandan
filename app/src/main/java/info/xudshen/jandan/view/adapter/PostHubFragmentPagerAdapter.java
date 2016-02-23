package info.xudshen.jandan.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.R;
import info.xudshen.jandan.presenter.PicListPresenter;
import info.xudshen.jandan.view.fragment.BlankFragment;
import info.xudshen.jandan.view.fragment.JokeListFragment;
import info.xudshen.jandan.view.fragment.PicListFragment;
import info.xudshen.jandan.view.fragment.PostListFragment;
import info.xudshen.jandan.view.fragment.VideoListFragment;

/**
 * Created by xudshen on 16/1/11.
 */
public class PostHubFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(PostHubFragmentPagerAdapter.class);
    final int PAGE_COUNT = 4;
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
        switch (position) {
            case 0: {
                return PostListFragment.newInstance();
            }
            case 1: {
                return PicListFragment.newInstance();
            }
            case 2: {
                return JokeListFragment.newInstance();
            }
            case 3: {
                return VideoListFragment.newInstance();
            }
            default: {
                return BlankFragment.newInstance(999);
            }
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0: {
                return context.getText(R.string.tab_item_posts);
            }
            case 1: {
                return context.getText(R.string.tab_item_pics);
            }
            case 2: {
                return context.getText(R.string.tab_item_jokes);
            }
            case 3: {
                return context.getText(R.string.tab_item_movies);
            }
            default: {
                return context.getText(R.string.tab_item_posts);
            }
        }
    }
}
