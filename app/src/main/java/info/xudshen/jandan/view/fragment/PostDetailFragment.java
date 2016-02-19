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

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.databinding.FragmentPostDetailBinding;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.presenter.PostDetailPresenter;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.view.PostDetailView;

public class PostDetailFragment extends BaseFragment implements PostDetailView {
    private static final Logger logger = LoggerFactory.getLogger(PostDetailFragment.class);
    public static final String ARG_POST_ID = "ARG_POST_ID";

    public static PostDetailFragment newInstance(Long postId) {
        Bundle args = new Bundle();
        args.putLong(ARG_POST_ID, postId);
        PostDetailFragment fragment = new PostDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    PostDetailPresenter postDetailPresenter;
    @Inject
    CommentDao commentDao;

    private Long postId;
    private FragmentPostDetailBinding binding;

    public PostDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId = getArguments().getLong(ARG_POST_ID);
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
        binding.postWithCommentList.setLayoutManager(linearLayoutManager);

        binding.postWithCommentLayout.setOnRefreshListener(direction -> {
            switch (direction) {
                case BOTTOM: {
                    PostDetailFragment.this.postDetailPresenter.refreshComment(postId);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.postDetailPresenter.setView(this);
        this.postDetailPresenter.initialize(postId);
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

    @Override
    public void renderPostDetail(Post post) {
        DDBindableCursorLoaderRVHeaderAdapter postCommentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(getActivity(), CommentDao.CONTENT_URI, null, "post_id = ?", new String[]{postId.toString()}, null)
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.header_post_detail, parent, false);
                    return new DDBindableViewHolder(viewDataBinding);
                })
                .headerViewDataBindingVariableAction(viewDataBinding1 -> {
                    TextView textView = (TextView) viewDataBinding1.getRoot().findViewById(R.id.post_detail_title);
                    textView.setText(post.getTitle());
                    WebView postDetailBody = (WebView) viewDataBinding1.getRoot().findViewById(R.id.post_detail_body);
                    String summary = post.getContent();
                    summary = HtmlHelper.formBody(summary);
                    postDetailBody.loadDataWithBaseURL(null, summary, "text/html; charset=UTF-8", null, null);
                    postDetailBody.setOnLongClickListener(v -> true);
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.post_comment_item)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    Comment comment = commentDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.comment, comment);
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
        binding.postWithCommentLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeUpLoading() {
        binding.postWithCommentLayout.setRefreshing(false);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    int toPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
