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

import com.google.common.base.Splitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.model.observable.PicItemObservable;
import info.xudshen.jandan.databinding.FragmentPicDetailBinding;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.internal.di.components.PicComponent;
import info.xudshen.jandan.presenter.PicDetailPresenter;
import info.xudshen.jandan.utils.ClipboardHelper;
import info.xudshen.jandan.utils.LayoutHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataDetailView;
import info.xudshen.jandan.view.activity.JandanSettingActivity;
import info.xudshen.jandan.view.model.DuoshuoCommentDialogModel;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by xudshen on 16/2/21.
 */
public class PicDetailFragment extends BaseFragment implements DataDetailView<PicItemObservable> {
    private static final Logger logger = LoggerFactory.getLogger(PicDetailFragment.class);
    public static final String ARG_PIC_ID = "ARG_PIC_ID";
    public static final String ARG_FAVO_ITEM = "ARG_FAVO_ITEM";

    public static PicDetailFragment newInstance(Long picId) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, picId);
        PicDetailFragment fragment = new PicDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PicDetailFragment newInstance(Long picId, FavoItem favoItem) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, picId);
        args.putSerializable(ARG_FAVO_ITEM, favoItem);
        PicDetailFragment fragment = new PicDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    PicDetailPresenter picDetailPresenter;
    @Inject
    DuoshuoCommentDao duoshuoCommentDao;
    @Inject
    PublishSubject<CommentAction> commentActionPublishSubject;

    private boolean isDataLoaded = false;
    private Long picId;
    private FavoItem favoItem;
    private FragmentPicDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picId = getArguments().getLong(ARG_PIC_ID);
        if (getArguments().containsKey(ARG_FAVO_ITEM)) {
            favoItem = (FavoItem) getArguments().getSerializable(ARG_FAVO_ITEM);
        }
    }

    @Override
    protected void inject() {
        this.getComponent(PicComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inject();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pic_detail, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.itemWithCommentList.setLayoutManager(linearLayoutManager);

        binding.itemWithCommentLayout.setOnRefreshListener(direction -> {
            switch (direction) {
                case BOTTOM: {
                    PicDetailFragment.this.picDetailPresenter.refreshComment(picId);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.picDetailPresenter.setView(this);
        this.picDetailPresenter.setVoteCommentView(new ActionView() {
            @Override
            public void showSuccess() {
                showSnackbar(binding.itemWithCommentList, getString(R.string.vote_success));
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
                showSnackbar(binding.itemWithCommentList, message);
            }

            @Override
            public Context context() {
                return getActivity().getApplicationContext();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return PicDetailFragment.this.bindToLifecycle();
            }
        });
        if (!isDataLoaded && getUserVisibleHint()) {
            if (favoItem == null) {
                this.picDetailPresenter.initialize(picId);
            } else {
                this.picDetailPresenter.initialize(favoItem);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.picDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.picDetailPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.picDetailPresenter.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                if (favoItem == null) {
                    this.picDetailPresenter.initialize(picId);
                } else {
                    this.picDetailPresenter.initialize(favoItem);
                }
            }
        }
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void renderDataDetail(PicItemObservable item) {
        if (binding.itemWithCommentList.getAdapter() == null) {
            boolean filterXXgtOO = JandanSettingActivity.getSettingFilterXXgtOO(getActivity());
            final int filterXXgt = JandanSettingActivity.getSettingFilterXXgt(getActivity());

            List<String> urlList = Splitter.on(",").splitToList(item.getPics());
            DDBindableCursorLoaderRVHeaderAdapter commentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), DuoshuoCommentDao.CONTENT_URI, null, DuoshuoCommentDao.Properties.ThreadKey.columnName + " = ?", new String[]{Constants.THREAD_PREFIX + picId}, null)
                    .headerViewHolderCreator((inflater, viewType, parent) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, headerPicDetailSelector(urlList.size()), parent, false);
                        Button refreshButton = (Button) viewDataBinding.getRoot().findViewById(R.id.refresh_comment_button);
                        refreshButton.setOnClickListener(v -> picDetailPresenter.refreshComment(picId));
                        //set for vote
                        viewDataBinding.getRoot().findViewById(R.id.comment_vote_oo).setOnClickListener(v -> {
                            PicDetailFragment.this.picDetailPresenter.voteComment(item.getPicId(), VoteType.OO);
                        });
                        viewDataBinding.getRoot().findViewById(R.id.comment_vote_xx).setOnClickListener(v -> {
                            PicDetailFragment.this.picDetailPresenter.voteComment(item.getPicId(), VoteType.XX);
                        });
                        viewDataBinding.getRoot().findViewById(R.id.toggle_item_detail).setOnClickListener(v -> {
                            View itemDetail = viewDataBinding.getRoot().findViewById(R.id.item_detail);
                            if (itemDetail.getVisibility() == View.VISIBLE) {
                                ((Button) v).setText(getString(R.string.pic_filter_show_again));
                                LayoutHelper.collapse(itemDetail);
                            } else {
                                ((Button) v).setText(getString(R.string.pic_filter_hide));
                                LayoutHelper.expand(itemDetail);
                            }
                        });

                        return new DDBindableViewHolder(viewDataBinding);
                    })
                    .headerViewDataBindingVariableAction(viewDataBinding -> {
                        viewDataBinding.setVariable(BR.urls, urlList);
                        viewDataBinding.setVariable(BR.picItem, item);
                        boolean hideItem = (filterXXgtOO && item.getVoteNegative() > item.getVotePositive()) || item.getVoteNegative() > filterXXgt;
                        viewDataBinding.setVariable(BR.hideItem, hideItem);
                    })
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
                                        DuoshuoCommentDao.Properties.ThreadKey.eq(Constants.THREAD_PREFIX + picId))
                                .build().forCurrentThread().unique();

                        dialogModel = new DuoshuoCommentDialogModel(commentTo);
                    } else {
                        dialogModel = new DuoshuoCommentDialogModel();
                    }

                    Context mContext = PicDetailFragment.this.getContext();
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

            binding.itemWithCommentList.setAdapter(commentAdapter);

            getLoaderManager().initLoader(0, null, commentAdapter);

            this.picDetailPresenter.refreshComment(picId);
        }
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.itemWithCommentList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.itemWithCommentList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.itemWithCommentList, message);
    }

    @Override
    public void showLoadingMore() {
        binding.itemWithCommentLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingMore(int count) {
        binding.itemWithCommentLayout.setRefreshing(false);
        if (count > 0) {
            showSnackbar(binding.itemWithCommentList,
                    String.format(getString(R.string.loaded_numbers_comments), count));
        } else if (count == 0) {
            showSnackbar(binding.itemWithCommentList, getString(R.string.post_detail_no_more_comments));
        }
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
    //</editor-fold>

    private int headerPicDetailSelector(int size) {
        switch (size) {
            case 1:
                return R.layout.header_pic_detail_1;
            case 2:
                return R.layout.header_pic_detail_2;
            case 3:
                return R.layout.header_pic_detail_3;
            case 4:
                return R.layout.header_pic_detail_4;
            case 5:
                return R.layout.header_pic_detail_5;
            case 6:
                return R.layout.header_pic_detail_6;
            case 7:
                return R.layout.header_pic_detail_7;
            case 8:
                return R.layout.header_pic_detail_8;
            case 9:
                return R.layout.header_pic_detail_9;
            case 10:
                return R.layout.header_pic_detail_10;
            case 11:
                return R.layout.header_pic_detail_11;
            default:
                return R.layout.header_pic_detail_1;
        }
    }
}
