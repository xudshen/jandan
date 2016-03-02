package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.VideoItemDao;
import info.xudshen.jandan.databinding.FragmentVideoListBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.internal.di.components.VideoComponent;
import info.xudshen.jandan.presenter.VideoListPresenter;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataListView;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.widget.RefreshDirection;
import rx.Observable;

public class VideoListFragment extends BaseFragment implements DataListView {
    private static final Logger logger = LoggerFactory.getLogger(VideoListFragment.class);

    public static VideoListFragment newInstance() {
        Bundle args = new Bundle();

        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    VideoListPresenter videoListPresenter;
    @Inject
    VideoItemDao videoItemDao;
    DDBindableCursorLoaderRVHeaderAdapter videoListAdapter;

    private boolean isDataLoaded = false;
    private FragmentVideoListBinding binding;

    public VideoListFragment() {
    }

    @Override
    protected void inject() {
        this.getComponent(VideoComponent.class).inject(this);
    }

    private void initAdapter() {
        videoListAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(getContext(), VideoItemDao.CONTENT_URI, null, null, null, VideoItemDao.Properties.Date.columnName + " desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    FloatingActionButton button = (FloatingActionButton) viewDataBinding.getRoot().findViewById(R.id.play_buttom);
                    button.setImageDrawable(new IconicsDrawable(getContext())
                            .icon(GoogleMaterial.Icon.gmd_play_circle_filled)
                            .color(getContext().getResources().getColor(R.color.md_white_1000))
                            .sizeDp(20)
                            .paddingDp(2));
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> R.layout.video_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    VideoItem videoItem = videoItemDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, videoItem);
                })
                .build();

        videoListAdapter.setOnItemClickListener((itemView, position) -> {
            logger.info("position={}", position);
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    itemView, position, ReaderItemType.SimpleVideo);
        });

        videoListAdapter.addOnItemSubviewClickListener(R.id.comment_vote_oo, (vh, v, position) -> {
            VideoItem comment = videoItemDao.loadEntity(videoListAdapter.getItemCursor(position));
            VideoListFragment.this.videoListPresenter.voteComment(comment.getVideoId(), VoteType.OO);
            logger.info("{}", v);
        });
        videoListAdapter.addOnItemSubviewClickListener(R.id.comment_vote_xx, (vh, v, position) -> {
            VideoItem comment = videoItemDao.loadEntity(videoListAdapter.getItemCursor(position));
            VideoListFragment.this.videoListPresenter.voteComment(comment.getVideoId(), VoteType.XX);
            logger.info("{}", v);
        });
        videoListAdapter.addOnItemSubviewClickListener(R.id.play_buttom, (vh, v, position) -> {
            VideoItem comment = videoItemDao.loadEntity(videoListAdapter.getItemCursor(position));
            HtmlHelper.openInBrowser(getActivity(), comment.getVideoLink());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false);
        initAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.videoListView.setLayoutManager(linearLayoutManager);
        binding.videoListView.setAdapter(videoListAdapter);
        binding.videoListView.setOnLoadMoreListener(() -> {
            logger.info("OnLoadMoreListener");
            this.videoListPresenter.swipeUpStart();
        });

        binding.videoListLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.videoListLayout.setOnRefreshListener(() -> {
            logger.info("OnRefreshListener");
            this.videoListPresenter.swipeDownStart();
        });

        //init for MaterialViewPager
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), binding.videoListView, null);
        //start load data in db
        getLoaderManager().initLoader(0, null, videoListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.videoListPresenter.setView(this);
        this.videoListPresenter.setVoteCommentView(new ActionView() {
            @Override
            public void showSuccess() {
                showSnackbar(binding.videoListView, getString(R.string.vote_success));
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
                showSnackbar(binding.videoListView, message);
            }

            @Override
            public Context context() {
                return getActivity().getApplicationContext();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return VideoListFragment.this.bindToLifecycle();
            }
        });
        if (!isDataLoaded && getUserVisibleHint()) {
            this.videoListPresenter.initialize();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.videoListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.videoListPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.videoListPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                this.videoListPresenter.initialize();
            }
        }
    }

    //<editor-fold desc="Called by Presenter">
    @Override
    public void showLoading() {
        binding.videoListLayout.setRefreshing(true, RefreshDirection.TOP);
    }

    @Override
    public void hideLoading() {
        binding.videoListLayout.setRefreshing(false, RefreshDirection.TOP);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.videoListView, message);
    }

    @Override
    public void showLoadingMore() {
        binding.videoListLayout.setRefreshing(true, RefreshDirection.BOTTOM);
        binding.videoListView.setLoading(true);
    }

    @Override
    public void hideLoadingMore() {
        binding.videoListLayout.setRefreshing(false, RefreshDirection.BOTTOM);
        binding.videoListView.setLoading(false);
    }

    @Override
    public void renderDataList() {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
    //</editor-fold>
}
