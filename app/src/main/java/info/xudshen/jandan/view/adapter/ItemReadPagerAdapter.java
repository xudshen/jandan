package info.xudshen.jandan.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.xudshen.jandan.view.fragment.BlankFragment;
import info.xudshen.jandan.view.fragment.PostDetailFragment;

/**
 * Created by xudshen on 16/1/28.
 */
public class ItemReadPagerAdapter extends FragmentStatePagerAdapter {
    public ItemReadPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return PostDetailFragment.newInstance();
            }
            case 1: {
                return BlankFragment.newInstance(position);
            }
            case 2: {
                return BlankFragment.newInstance(position);
            }
            default: {
                return BlankFragment.newInstance(999);
            }
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
