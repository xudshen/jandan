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
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.databinding.FragmentPicListBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.internal.di.components.PicComponent;
import info.xudshen.jandan.presenter.PicListPresenter;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataListView;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.widget.RefreshDirection;
import rx.Observable;

public class PicListFragment extends BaseFragment implements DataListView {
    private static final Logger logger = LoggerFactory.getLogger(PicListFragment.class);

    public static PicListFragment newInstance() {
        Bundle args = new Bundle();

        PicListFragment fragment = new PicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    PicListPresenter picListPresenter;
    @Inject
    PicItemDao picItemDao;
    @Inject
    @Named("picListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter picListAdapter;

    private boolean isDataLoaded = false;
    private FragmentPicListBinding binding;

    public PicListFragment() {
    }

    @Override
    protected void inject() {
        this.getComponent(PicComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pic_list, container, false);

        picListAdapter.setOnItemClickListener((itemView, position) -> {
            logger.info("position={}", position);
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    itemView, position, ReaderItemType.SimplePic);
        });

        picListAdapter.addOnItemSubviewClickListener(R.id.comment_vote_oo, (vh, v, position) -> {
            PicItem comment = picItemDao.loadEntity(picListAdapter.getItemCursor(position));
            PicListFragment.this.picListPresenter.voteComment(comment.getPicId(), VoteType.OO);
            logger.info("{}", v);
        });
        picListAdapter.addOnItemSubviewClickListener(R.id.comment_vote_xx, (vh, v, position) -> {
            PicItem comment = picItemDao.loadEntity(picListAdapter.getItemCursor(position));
            PicListFragment.this.picListPresenter.voteComment(comment.getPicId(), VoteType.XX);
            logger.info("{}", v);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.picListView.setLayoutManager(linearLayoutManager);
        binding.picListView.setAdapter(picListAdapter);
        binding.picListView.setOnLoadMoreListener(() -> {
            logger.info("OnLoadMoreListener");
            this.picListPresenter.swipeUpStart();
        });

        binding.picListLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.picListLayout.setOnRefreshListener(() -> {
            logger.info("OnRefreshListener");
            this.picListPresenter.swipeDownStart();
        });

        //init for MaterialViewPager
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), binding.picListView, null);
        //start load data in db
        getLoaderManager().initLoader(0, null, picListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.picListPresenter.setView(this);
        this.picListPresenter.setVoteCommentView(new ActionView() {
            @Override
            public void showSuccess() {
                showSnackbar(binding.picListView, getString(R.string.vote_success));
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
                showSnackbar(binding.picListView, message);
            }

            @Override
            public Context context() {
                return getActivity().getApplicationContext();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return PicListFragment.this.bindToLifecycle();
            }
        });
        if (!isDataLoaded && getUserVisibleHint()) {
            this.picListPresenter.initialize();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.picListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.picListPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.picListPresenter.destroy();
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
                this.picListPresenter.initialize();
            }
        }
    }

    //<editor-fold desc="Called by Presenter">
    @Override
    public void showLoading() {
        binding.picListLayout.setRefreshing(true, RefreshDirection.TOP);
    }

    @Override
    public void hideLoading() {
        binding.picListLayout.setRefreshing(false, RefreshDirection.TOP);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.picListView, message);
    }

    @Override
    public void showLoadingMore() {
        binding.picListLayout.setRefreshing(true, RefreshDirection.BOTTOM);
        binding.picListView.setLoading(true);
    }

    @Override
    public void hideLoadingMore() {
        binding.picListLayout.setRefreshing(false, RefreshDirection.BOTTOM);
        binding.picListView.setLoading(false);
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
