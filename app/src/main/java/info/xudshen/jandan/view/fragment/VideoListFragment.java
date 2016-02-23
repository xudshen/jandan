package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.jandan.R;
import info.xudshen.jandan.databinding.FragmentVideoListBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.internal.di.components.VideoComponent;
import info.xudshen.jandan.presenter.VideoListPresenter;
import info.xudshen.jandan.view.DataListView;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.widget.RefreshDirection;

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
    @Named("videoListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter videoListAdapter;

    private FragmentVideoListBinding binding;

    public VideoListFragment() {
    }

    @Override
    protected void inject() {
        this.getComponent(VideoComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_list, container, false);

        videoListAdapter.setOnItemClickListener((itemView, position) -> {
            logger.info("position={}", position);
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    itemView, position, ReaderItemType.SimpleVideo);
        });

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
        this.videoListPresenter.initialize();
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

    }

    @Override
    public void showSwipeUpLoading() {
        binding.videoListLayout.setRefreshing(true, RefreshDirection.BOTTOM);
        binding.videoListView.setLoading(true);
    }

    @Override
    public void hideSwipeUpLoading() {
        binding.videoListLayout.setRefreshing(false, RefreshDirection.BOTTOM);
        binding.videoListView.setLoading(false);
    }

    @Override
    public void renderList() {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
    //</editor-fold>
}
