package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.view.adapter.PostHubFragmentPagerAdapter;

public class HomeFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(HomeFragment.class);
    @Bind(R.id.materialViewPager)
    MaterialViewPager viewPager;
    private int previousSelection = -999;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
    }

    @Override
    public void inject() {
        getComponent(PostComponent.class).inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setActionBarDrawerToggle(viewPager.getToolbar());

        initializeViewPager();
        return view;
    }

    void initializeViewPager() {
        viewPager.getViewPager().setAdapter(new PostHubFragmentPagerAdapter(getChildFragmentManager(),
                getActivity()));

        viewPager.setMaterialViewPagerListener(page -> {
            switch (page) {
                case 0:
                    return HeaderDesign.fromColorAndDrawable(
                            getResources().getColor(R.color.md_amber_500),
                            getActivity().getResources().getDrawable(R.drawable.header));
                case 1:
                    return HeaderDesign.fromColorAndDrawable(
                            getResources().getColor(R.color.md_amber_500),
                            getActivity().getResources().getDrawable(R.drawable.header));
                case 2:
                    return HeaderDesign.fromColorAndDrawable(
                            getResources().getColor(R.color.md_amber_500),
                            getActivity().getResources().getDrawable(R.drawable.header));
                case 3:
                    return HeaderDesign.fromColorAndDrawable(
                            getResources().getColor(R.color.md_amber_500),
                            getActivity().getResources().getDrawable(R.drawable.header));
                case 4:
                    return HeaderDesign.fromColorAndDrawable(
                            getResources().getColor(R.color.md_amber_500),
                            getActivity().getResources().getDrawable(R.drawable.header));
            }
            //execute others actions if needed (ex : modify your header logo)
            return null;
        });

        viewPager.getViewPager().setOffscreenPageLimit(viewPager.getViewPager().getAdapter().getCount());
        //After set an adapter to the ViewPager
        viewPager.getPagerTitleStrip().setViewPager(viewPager.getViewPager());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (previousSelection < 0) {
            //set default
            viewPager.getViewPager().setCurrentItem(0);
        }
    }

    @Override
    public void onDestroy() {
        viewPager.getViewPager().getAdapter().setPrimaryItem(null, 0, null);

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
