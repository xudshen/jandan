package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.common.base.Splitter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.model.observable.PicItemObservable;
import info.xudshen.jandan.data.utils.HtmlUtils;
import info.xudshen.jandan.databinding.FragmentPicDetailBinding;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.enums.ImageQuality;
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
import info.xudshen.jandan.view.adapter.AppAdapters;
import info.xudshen.jandan.view.component.ProgressImageView;
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

        binding.commentList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commentList.setNestedScrollingEnabled(false);
        binding.itemDetailList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.itemDetailList.setNestedScrollingEnabled(false);

        return binding.getRoot();
    }

    private void initView() {
        binding.refreshCommentButton.setOnClickListener(v -> picDetailPresenter.refreshComment(picId));
        //set for vote
        binding.commentVoteOo.setOnClickListener(v -> {
            PicDetailFragment.this.picDetailPresenter.voteComment(picId, VoteType.OO);
        });
        binding.commentVoteXx.setOnClickListener(v -> {
            PicDetailFragment.this.picDetailPresenter.voteComment(picId, VoteType.XX);
        });
        //set for toggle
        binding.toggleItemDetail.setOnClickListener(v -> {
            if (binding.itemDetailList.getVisibility() == View.VISIBLE) {
                ((Button) v).setText(getString(R.string.pic_filter_show_again));
                LayoutHelper.collapse(binding.itemDetailList);
            } else {
                ((Button) v).setText(getString(R.string.pic_filter_hide));
                LayoutHelper.expand(binding.itemDetailList);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.picDetailPresenter.setView(this);
        this.picDetailPresenter.setVoteCommentView(new ActionView() {
            @Override
            public void showSuccess() {
                showSnackbar(binding.commentList, getString(R.string.vote_success));
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
                showSnackbar(binding.commentList, message);
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
        binding.itemDetailList.setAdapter(null);
        binding.commentList.setAdapter(null);

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
        if (binding.commentList.getAdapter() == null) {
            boolean filterXXgtOO = JandanSettingActivity.getSettingFilterXXgtOO(getActivity());
            final int filterXXgt = JandanSettingActivity.getSettingFilterXXgt(getActivity());

            boolean hideItem = (filterXXgtOO && item.getVoteNegative() > item.getVotePositive()) || item.getVoteNegative() > filterXXgt;
            binding.setVariable(BR.hideItem, hideItem);
            binding.setVariable(BR.picItem, item);


            //set for list
            List<String> urlList = Splitter.on(",").splitToList(item.getPics());
            binding.itemDetailList.setAdapter(new RecyclerView.Adapter() {
                @Override
                public int getItemViewType(int position) {
                    if (urlList.get(position).endsWith("gif")) {
                        return R.layout.header_pic_detail_item;
                    } else {
                        return R.layout.header_pic_detail_item_image;
                    }
                }

                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
                    return new RecyclerView.ViewHolder(v) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                    ProgressImageView itemView = (ProgressImageView) holder.itemView;
                    String url = HtmlUtils.optimizedUrl(urlList.get(position), AppAdapters.IMAGE_QUALITY, ImageQuality.MEDIUM);
                    if (url.endsWith("gif")) {
                        ((ProgressImageView) holder.itemView).load(url,
                                getResources().getDrawable(R.drawable.placeholder), PicDetailFragment.this);
                    } else {
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                                .cacheOnDisk(true).resetViewBeforeLoading(true)
                                .build();
                        imageLoader.displayImage(url, (ImageView) holder.itemView, options);
                    }
                }

                @Override
                public int getItemCount() {
                    return urlList.size();
                }
            });

            //set for comment
            DDBindableCursorLoaderRVAdapter commentAdapter = new DDBindableCursorLoaderRVAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), DuoshuoCommentDao.CONTENT_URI, null, DuoshuoCommentDao.Properties.ThreadKey.columnName + " = ?", new String[]{Constants.THREAD_PREFIX + picId}, null)
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

            binding.commentList.setAdapter(commentAdapter);

            getLoaderManager().initLoader(0, null, commentAdapter);

            this.picDetailPresenter.refreshComment(picId);
        }
    }

    @Override
    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.commentList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
        binding.commentList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.commentList, message);
    }

    @Override
    public void showLoadingMore() {
//        binding.itemWithCommentLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingMore(int count) {
//        binding.itemWithCommentLayout.setRefreshing(false);
        if (count > 0) {
            showSnackbar(binding.commentList,
                    String.format(getString(R.string.loaded_numbers_comments), count));
        } else if (count == 0) {
            showSnackbar(binding.commentList, getString(R.string.post_detail_no_more_comments));
        }
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
    //</editor-fold>
}
