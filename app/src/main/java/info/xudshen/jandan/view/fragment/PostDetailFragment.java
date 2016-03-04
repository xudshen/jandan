package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.model.observable.PostObservable;
import info.xudshen.jandan.databinding.FragmentPostDetailBinding;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.presenter.PostDetailPresenter;
import info.xudshen.jandan.utils.ClipboardHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import info.xudshen.jandan.view.model.CommentDialogModel;
import rx.Observable;
import rx.subjects.PublishSubject;

public class PostDetailFragment extends BaseFragment implements DataDetailView<PostObservable> {
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
    @Inject
    PublishSubject<CommentAction> commentActionPublishSubject;

    private boolean isDataLoaded = false;
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

        binding.commentList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commentList.setNestedScrollingEnabled(false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.scrollContent.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.scrollContent.setEnabled(true);
        binding.scrollContent.setOnRefreshListener(
                () -> PostDetailFragment.this.postDetailPresenter.refreshComment(postId));
        binding.refreshCommentButton.setOnClickListener(v -> postDetailPresenter.refreshComment(postId));
    }

    private void unBindView() {
        binding.scrollContent.setOnRefreshListener(null);
        binding.refreshCommentButton.setOnClickListener(null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.postDetailPresenter.setView(this);
        this.postDetailPresenter.setVoteCommentView(new ActionView() {
            @Override
            public void showSuccess() {
                showSnackbar(binding.scrollContent, getString(R.string.vote_success));
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showRetry() {

            }

            @Override
            public void hideRetry() {

            }

            @Override
            public void showError(String message) {
                showSnackbar(binding.scrollContent, message);
            }

            @Override
            public Context context() {
                return getActivity().getApplicationContext();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return PostDetailFragment.this.bindToLifecycle();
            }
        });
        if (!isDataLoaded && getUserVisibleHint()) {
            this.postDetailPresenter.initialize(postId);
        }
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
    public void onDestroy() {
        binding.commentList.setAdapter(null);
        unBindView();

        super.onDestroy();
        this.postDetailPresenter.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                this.postDetailPresenter.initialize(postId);
            }
        }
    }

    @Override
    public void renderDataDetail(PostObservable postObservable) {
        if (binding.commentList.getAdapter() == null) {
            binding.setVariable(BR.post, postObservable);

            DDBindableCursorLoaderRVAdapter postCommentAdapter = new DDBindableCursorLoaderRVAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), CommentDao.CONTENT_URI, null, CommentDao.Properties.PostId.columnName + " = ?", new String[]{postId.toString()}, null)
                    .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    }))
                    .itemLayoutSelector((position, cursor) -> R.layout.post_comment_item)
                    .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                        Comment comment = commentDao.loadEntity(cursor);
                        viewDataBinding.setVariable(BR.comment, comment);
                    })
                    .build();

            postCommentAdapter.addOnItemSubviewClickListener(R.id.comment_vote_oo, (vh, v, position) -> {
                Comment comment = commentDao.loadEntity(postCommentAdapter.getItemCursor(position));
                PostDetailFragment.this.postDetailPresenter.voteComment(comment.getCommentId(), VoteType.OO);
            });
            postCommentAdapter.addOnItemSubviewClickListener(R.id.comment_vote_xx, (vh, v, position) -> {
                Comment comment = commentDao.loadEntity(postCommentAdapter.getItemCursor(position));
                PostDetailFragment.this.postDetailPresenter.voteComment(comment.getCommentId(), VoteType.XX);
            });

            postCommentAdapter.setOnItemClickListener((v, position) -> {
                Comment comment = commentDao.loadEntity(postCommentAdapter.getItemCursor(position));
                if (comment != null) {
                    CommentDialogModel dialogModel = null;
                    if (comment.getCommentTo() != null) {
                        Comment commentTo = commentDao.queryBuilder()
                                .where(CommentDao.Properties.CommentId.eq(comment.getCommentTo()),
                                        CommentDao.Properties.PostId.eq(postId))
                                .build().forCurrentThread().unique();
                        dialogModel = new CommentDialogModel(commentTo);
                    } else {
                        dialogModel = new CommentDialogModel();
                    }

                    Context mContext = PostDetailFragment.this.getContext();
                    LayoutInflater inflater = (LayoutInflater)
                            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.post_comment_item_popup, null, false);
                    viewDataBinding.setVariable(BR.comment, dialogModel);
                    viewDataBinding.executePendingBindings();

                    AlertDialog alertDialog = (new AlertDialog.Builder(mContext))
                            .setView(viewDataBinding.getRoot())
                            .create();

                    viewDataBinding.getRoot().findViewById(R.id.comment_copy_btn).setOnClickListener(v1 -> {
                        ClipboardHelper.copy(getContext(), Html.fromHtml(comment.getContent()).toString());
                        alertDialog.hide();
                    });
                    viewDataBinding.getRoot().findViewById(R.id.comment_reply_btn).setOnClickListener(v1 -> {
                        commentActionPublishSubject.onNext(new CommentAction.Builder()
                                .parentId(Constants.JANDAN_COMMENT_PREFIX + comment.getCommentId())
                                .parentName(comment.getName()).jandan());
                        alertDialog.hide();
                    });
                    alertDialog.show();
                }
            });
            binding.commentList.setAdapter(postCommentAdapter);

            getLoaderManager().initLoader(0, null, postCommentAdapter);

            this.postDetailPresenter.refreshComment(postId);
        }
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.scrollContent.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.scrollContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.scrollContent, message);
    }

    @Override
    public void showLoadingMore() {
        binding.scrollContent.setRefreshing(true);
    }

    @Override
    public void hideLoadingMore(int count) {
        binding.scrollContent.setRefreshing(false);
        if (count > 0) {
            showSnackbar(binding.scrollContent,
                    String.format(getString(R.string.loaded_numbers_comments), count));
        } else if (count == 0) {
            showSnackbar(binding.scrollContent, getString(R.string.post_detail_no_more_comments));
        }
    }

    @Override
    public Context context() {
        return getContext();
    }
}
