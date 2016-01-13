package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.mikepenz.materialdrawer.Drawer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.view.adapter.PostHubFragmentPagerAdapter;

public class PostHubFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(PostHubFragment.class);

    @Bind(R.id.materialViewPager)
    MaterialViewPager viewPager;

    public PostHubFragment() {
        logger.info("new");
    }

    public static PostHubFragment newInstance() {
        return new PostHubFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logger.info("onCreateView");
        getComponent(PostComponent.class).inject(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_hub, container, false);
        ButterKnife.bind(this, view);
        setActionBarDrawerToggle(viewPager.getToolbar());


        viewPager.getViewPager().setAdapter(new PostHubFragmentPagerAdapter(getFragmentManager(),
                getActivity()));

        viewPager.setMaterialViewPagerListener(page -> {
            switch (page) {
                case 0:
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.green,
                            "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                case 1:
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.blue,
                            "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                case 2:
                    return HeaderDesign.fromColorResAndUrl(
                            R.color.cyan,
                            "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
            }
            //execute others actions if needed (ex : modify your header logo)
            return null;
        });

        viewPager.getViewPager().setOffscreenPageLimit(viewPager.getViewPager().getAdapter().getCount());
        //After set an adapter to the ViewPager
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
//        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.sliding_tabs);
//        tabLayout.removeAllTabs();
//        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
