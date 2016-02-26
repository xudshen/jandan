package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.model.observable.VideoItemObservable;
import info.xudshen.jandan.databinding.FragmentVideoDetailBinding;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.internal.di.components.VideoComponent;
import info.xudshen.jandan.presenter.VideoDetailPresenter;
import info.xudshen.jandan.view.DataDetailView;
import info.xudshen.jandan.view.model.DuoshuoCommentDialogModel;
import rx.subjects.PublishSubject;

/**
 * Created by xudshen on 16/2/21.
 */
public class VideoDetailFragment extends BaseFragment implements DataDetailView<VideoItemObservable> {
    private static final Logger logger = LoggerFactory.getLogger(VideoDetailFragment.class);
    public static final String ARG_PIC_ID = "ARG_PIC_ID";
    public static final String ARG_FAVO_ITEM = "ARG_FAVO_ITEM";

    public static VideoDetailFragment newInstance(Long videoId) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, videoId);
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static VideoDetailFragment newInstance(Long videoId, FavoItem favoItem) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, videoId);
        args.putSerializable(ARG_FAVO_ITEM, favoItem);
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    VideoDetailPresenter videoDetailPresenter;
    @Inject
    DuoshuoCommentDao duoshuoCommentDao;
    @Inject
    PublishSubject<CommentAction> commentActionPublishSubject;

    private boolean isDataLoaded = false;
    private Long videoId;
    private FavoItem favoItem;
    private FragmentVideoDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoId = getArguments().getLong(ARG_PIC_ID);
        if (getArguments().containsKey(ARG_FAVO_ITEM)) {
            favoItem = (FavoItem) getArguments().getSerializable(ARG_FAVO_ITEM);
        }
    }

    @Override
    protected void inject() {
        this.getComponent(VideoComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inject();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_detail, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.itemWithCommentList.setLayoutManager(linearLayoutManager);

        binding.itemWithCommentLayout.setOnRefreshListener(direction -> {
            switch (direction) {
                case BOTTOM: {
                    VideoDetailFragment.this.videoDetailPresenter.refreshComment(videoId);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.videoDetailPresenter.setView(this);
        if (!isDataLoaded && getUserVisibleHint()) {
            if (favoItem == null) {
                this.videoDetailPresenter.initialize(videoId);
            } else {
                this.videoDetailPresenter.initialize(favoItem);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.videoDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.videoDetailPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.videoDetailPresenter.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                if (favoItem == null) {
                    this.videoDetailPresenter.initialize(videoId);
                } else {
                    this.videoDetailPresenter.initialize(favoItem);
                }
            }
        }
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void renderItemDetail(VideoItemObservable item) {
        if (binding.itemWithCommentList.getAdapter() == null) {
            DDBindableCursorLoaderRVHeaderAdapter commentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), DuoshuoCommentDao.CONTENT_URI, null, DuoshuoCommentDao.Properties.ThreadKey.columnName + " = ?", new String[]{Constants.THREAD_PREFIX + videoId}, null)
                    .headerViewHolderCreator((inflater, viewType, parent) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.header_video_detail, parent, false);
                        FloatingActionButton button = (FloatingActionButton) viewDataBinding.getRoot().findViewById(R.id.play_buttom);
                        button.setImageDrawable(new IconicsDrawable(getActivity())
                                .icon(GoogleMaterial.Icon.gmd_play_circle_filled)
                                .color(getResources().getColor(R.color.md_white_1000))
                                .sizeDp(20)
                                .paddingDp(2));

                        Button refreshButton = (Button) viewDataBinding.getRoot().findViewById(R.id.refresh_comment_button);
                        refreshButton.setOnClickListener(v -> videoDetailPresenter.refreshComment(videoId));
                        return new DDBindableViewHolder(viewDataBinding);
                    })
                    .headerViewDataBindingVariableAction(viewDataBinding -> {
                        viewDataBinding.setVariable(BR.videoItem, item);
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
                                        DuoshuoCommentDao.Properties.ThreadKey.eq(Constants.THREAD_PREFIX + videoId))
                                .build().forCurrentThread().unique();

                        dialogModel = new DuoshuoCommentDialogModel(commentTo);
                    } else {
                        dialogModel = new DuoshuoCommentDialogModel();
                    }

                    Context mContext = VideoDetailFragment.this.getContext();
                    LayoutInflater inflater = (LayoutInflater)
                            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.pic_comment_item_popup, null, false);
                    viewDataBinding.setVariable(BR.comment, dialogModel);
                    viewDataBinding.executePendingBindings();

                    AlertDialog alertDialog = (new AlertDialog.Builder(mContext))
                            .setView(viewDataBinding.getRoot())
                            .create();

                    viewDataBinding.getRoot().findViewById(R.id.comment_copy_btn).setOnClickListener(v1 -> {
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

            this.videoDetailPresenter.refreshComment(videoId);
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
        } else {
            showSnackbar(binding.itemWithCommentList, getString(R.string.post_detail_no_more_comments));
        }
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
    //</editor-fold>
}
