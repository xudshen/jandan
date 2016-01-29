package info.xudshen.jandan.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.xudshen.jandan.R;

public class PostDetailFragment extends BaseFragment {
    public static PostDetailFragment newInstance() {
        PostDetailFragment fragment = new PostDetailFragment();
        return fragment;
    }

    public PostDetailFragment() {
        // Required empty public constructor
    }

    @Override
    protected void inject() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_detail, container, false);
    }
}
