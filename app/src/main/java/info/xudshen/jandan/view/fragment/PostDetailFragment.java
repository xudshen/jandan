package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.databinding.FragmentPostDetailBinding;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.presenter.PostDetailPresenter;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.view.PostDetailView;

public class PostDetailFragment extends BaseFragment implements PostDetailView {
    private static final Logger logger = LoggerFactory.getLogger(PostDetailFragment.class);

    public static PostDetailFragment newInstance() {
        return new PostDetailFragment();
    }

    @Inject
    PostDetailPresenter postDetailPresenter;

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
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.postDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.postDetailPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.postDetailPresenter.destroy();
    }

    private void initialize() {
        this.getComponent(PostComponent.class).inject(this);
        this.postDetailPresenter.setView(this);
        this.postDetailPresenter.initialize(null);
    }

    @Override
    public void renderPostDetail(Post post) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.postWithCommentList.setLayoutManager(linearLayoutManager);

        DDBindableCursorLoaderRVHeaderAdapter postCommentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(getActivity(), PostDao.CONTENT_URI, null, null, null, "post_id desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.header_post_detail, parent, false);
                    return new DDBindableViewHolder(viewDataBinding);
                })
                .headerViewDataBindingVariableAction(viewDataBinding1 -> {
                    TextView textView = (TextView) viewDataBinding1.getRoot().findViewById(R.id.post_detail_title);
                    textView.setText(post.getTitle());
//                    WebView postDetailBody = (WebView) viewDataBinding1.getRoot().findViewById(R.id.post_detail_body);
//                    String summary = post.getTitle();
//                    summary = HtmlHelper.formBody(summary);
//                    postDetailBody.loadDataWithBaseURL(null, summary, "text/html; charset=UTF-8", null, null);
//                    postDetailBody.setOnLongClickListener(v -> true);
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.post_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                })
                .build();

        binding.postWithCommentList.setAdapter(postCommentAdapter);

        getLoaderManager().initLoader(0, null, postCommentAdapter);
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.postWithCommentList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.postWithCommentList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showSwipeUpLoading() {

    }

    @Override
    public void hideSwipeUpLoading() {

    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    int toPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
