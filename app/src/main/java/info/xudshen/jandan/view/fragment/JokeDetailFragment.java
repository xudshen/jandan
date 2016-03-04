package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.model.observable.JokeItemObservable;
import info.xudshen.jandan.databinding.FragmentJokeDetailBinding;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.internal.di.components.JokeComponent;
import info.xudshen.jandan.presenter.JokeDetailPresenter;
import info.xudshen.jandan.utils.ClipboardHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import info.xudshen.jandan.view.model.DuoshuoCommentDialogModel;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by xudshen on 16/2/21.
 */
public class JokeDetailFragment extends BaseFragment implements DataDetailView<JokeItemObservable> {
    private static final Logger logger = LoggerFactory.getLogger(JokeDetailFragment.class);
    public static final String ARG_PIC_ID = "ARG_PIC_ID";
    public static final String ARG_FAVO_ITEM = "ARG_FAVO_ITEM";

    public static JokeDetailFragment newInstance(Long jokeId) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, jokeId);
        JokeDetailFragment fragment = new JokeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JokeDetailFragment newInstance(Long jokeId, FavoItem favoItem) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, jokeId);
        args.putSerializable(ARG_FAVO_ITEM, favoItem);
        JokeDetailFragment fragment = new JokeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    JokeDetailPresenter jokeDetailPresenter;
    @Inject
    DuoshuoCommentDao duoshuoCommentDao;
    @Inject
    PublishSubject<CommentAction> commentActionPublishSubject;

    private boolean isDataLoaded = false;
    private Long jokeId;
    private FavoItem favoItem;
    private FragmentJokeDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jokeId = getArguments().getLong(ARG_PIC_ID);
        if (getArguments().containsKey(ARG_FAVO_ITEM)) {
            favoItem = (FavoItem) getArguments().getSerializable(ARG_FAVO_ITEM);
        }
    }

    @Override
    protected void inject() {
        this.getComponent(JokeComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inject();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_detail, container, false);

        binding.commentList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commentList.setNestedScrollingEnabled(false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.scrollContent.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.scrollContent.setEnabled(true);
        binding.scrollContent.setOnRefreshListener(
                () -> JokeDetailFragment.this.jokeDetailPresenter.refreshComment(jokeId));
        binding.refreshCommentButton.setOnClickListener(v -> jokeDetailPresenter.refreshComment(jokeId));
        //set for vote
        binding.commentVoteOo.setOnClickListener(v -> {
            JokeDetailFragment.this.jokeDetailPresenter.voteComment(jokeId, VoteType.OO);
        });
        binding.commentVoteXx.setOnClickListener(v -> {
            JokeDetailFragment.this.jokeDetailPresenter.voteComment(jokeId, VoteType.XX);
        });
    }

    private void unBindView() {
        binding.scrollContent.setOnRefreshListener(null);
        binding.refreshCommentButton.setOnClickListener(null);
        binding.commentVoteOo.setOnClickListener(null);
        binding.commentVoteXx.setOnClickListener(null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.jokeDetailPresenter.setView(this);
        this.jokeDetailPresenter.setVoteCommentView(new ActionView() {
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
                return JokeDetailFragment.this.getContext();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return JokeDetailFragment.this.bindToLifecycle();
            }
        });
        if (!isDataLoaded && getUserVisibleHint()) {
            if (favoItem == null) {
                this.jokeDetailPresenter.initialize(jokeId);
            } else {
                this.jokeDetailPresenter.initialize(favoItem);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.jokeDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.jokeDetailPresenter.pause();
    }

    @Override
    public void onDestroy() {
        binding.commentList.setAdapter(null);
        unBindView();

        super.onDestroy();
        this.jokeDetailPresenter.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                if (favoItem == null) {
                    this.jokeDetailPresenter.initialize(jokeId);
                } else {
                    this.jokeDetailPresenter.initialize(favoItem);
                }
            }
        }
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void renderDataDetail(JokeItemObservable item) {
        if (binding.commentList.getAdapter() == null) {
            binding.setVariable(BR.jokeItem, item);

            DDBindableCursorLoaderRVAdapter commentAdapter = new DDBindableCursorLoaderRVAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), DuoshuoCommentDao.CONTENT_URI, null, DuoshuoCommentDao.Properties.ThreadKey.columnName + " = ?", new String[]{Constants.THREAD_PREFIX + jokeId}, null)
                    .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    }))
                    .itemLayoutSelector((position, cursor) -> R.layout.pic_comment_item)
                    .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                        DuoshuoComment duoshuoComment = duoshuoCommentDao.loadEntity(cursor);
                        viewDataBinding.setVariable(BR.comment, duoshuoComment);
                    })
                    .build();

            commentAdapter.setOnItemClickListener((v, position) -> {
                DuoshuoComment duoshuoComment = duoshuoCommentDao.loadEntity(commentAdapter.getItemCursor(position));
                if (duoshuoComment != null) {
                    DuoshuoCommentDialogModel dialogModel = null;
                    if (duoshuoComment.getParentCommentId() != null) {
                        DuoshuoComment commentTo = duoshuoCommentDao.queryBuilder()
                                .where(DuoshuoCommentDao.Properties.CommentId.eq(duoshuoComment.getParentCommentId()),
                                        DuoshuoCommentDao.Properties.ThreadKey.eq(Constants.THREAD_PREFIX + jokeId))
                                .build().forCurrentThread().unique();

                        dialogModel = new DuoshuoCommentDialogModel(commentTo);
                    } else {
                        dialogModel = new DuoshuoCommentDialogModel();
                    }

                    Context mContext = JokeDetailFragment.this.getContext();
                    LayoutInflater inflater = (LayoutInflater)
                            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.pic_comment_item_popup, null, false);
                    viewDataBinding.setVariable(BR.comment, dialogModel);
                    viewDataBinding.executePendingBindings();

                    AlertDialog alertDialog = (new AlertDialog.Builder(mContext))
                            .setView(viewDataBinding.getRoot())
                            .create();

                    viewDataBinding.getRoot().findViewById(R.id.comment_copy_btn).setOnClickListener(v1 -> {
                        ClipboardHelper.copy(getContext(), duoshuoComment.getMessage());
                        alertDialog.hide();
                    });
                    viewDataBinding.getRoot().findViewById(R.id.comment_reply_btn).setOnClickListener(v1 -> {
                        commentActionPublishSubject.onNext(new CommentAction.Builder()
                                .parentId(duoshuoComment.getCommentId())
                                .parentName(duoshuoComment.getAuthorName()).duoshuo());
                        alertDialog.hide();
                    });
                    alertDialog.show();
                }
            });

            binding.commentList.setAdapter(commentAdapter);

            getLoaderManager().initLoader(0, null, commentAdapter);

            this.jokeDetailPresenter.refreshComment(jokeId);
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
    //</editor-fold>
}
