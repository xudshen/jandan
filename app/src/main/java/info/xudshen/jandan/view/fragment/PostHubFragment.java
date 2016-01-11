package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.view.adapter.PostHubFragmentPagerAdapter;

public class PostHubFragment extends BaseFragment {
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    public PostHubFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_hub, container, false);
        ButterKnife.bind(this, view);

        viewPager.setAdapter(new PostHubFragmentPagerAdapter(getFragmentManager(),
                getActivity()));

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
