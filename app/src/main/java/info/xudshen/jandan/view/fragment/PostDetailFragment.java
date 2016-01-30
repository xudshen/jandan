package info.xudshen.jandan.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.jandan.R;
import info.xudshen.jandan.databinding.FragmentPostDetailBinding;
import info.xudshen.jandan.internal.di.components.PostComponent;

public class PostDetailFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(PostDetailFragment.class);

    public static PostDetailFragment newInstance() {
        return new PostDetailFragment();
    }

    @Inject
    @Named("postCommentAdapter")
    DDBindableCursorLoaderRVHeaderAdapter postCommentAdapter;

    private FragmentPostDetailBinding binding;

    public PostDetailFragment() {
    }

    @Override
    protected void inject() {
        this.getComponent(PostComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.postCommentList.setLayoutManager(linearLayoutManager);
        binding.postCommentList.setAdapter(postCommentAdapter);

        getLoaderManager().initLoader(0, null, postCommentAdapter);
        return binding.getRoot();
    }

    int toPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
