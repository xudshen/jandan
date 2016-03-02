package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.databinding.FragmentPostListBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.presenter.PostListPresenter;
import info.xudshen.jandan.view.DataListView;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.widget.RefreshDirection;

public class PostListFragment extends BaseFragment implements DataListView {
    private static final Logger logger = LoggerFactory.getLogger(PostListFragment.class);

    public static PostListFragment newInstance() {
        return new PostListFragment();
    }

    @Inject
    PostListPresenter postListPresenter;
    @Inject
    SimplePostDao simplePostDao;
    DDBindableCursorLoaderRVHeaderAdapter postListAdapter;

    private boolean isDataLoaded = false;
    private FragmentPostListBinding binding;

    public PostListFragment() {
        super();
    }

    @Override
    public void inject() {
        this.getComponent(PostComponent.class).inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initAdapter() {
        postListAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(getContext(), SimplePostDao.CONTENT_URI, null, null, null, SimplePostDao.Properties.Date.columnName + " desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> R.layout.post_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    SimplePost simplePost = simplePostDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, simplePost);
                })
                .build();
        postListAdapter.setOnItemClickListener((itemView, position) -> {
            logger.info("position={}", position);
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    itemView.findViewById(R.id.post_card_author), position, ReaderItemType.SimplePost);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_list, container, false);
        initAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.myRecyclerView.setLayoutManager(linearLayoutManager);
        binding.myRecyclerView.setAdapter(postListAdapter);
        binding.myRecyclerView.setOnLoadMoreListener(() -> {
            this.postListPresenter.swipeUpStart();
        });

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            this.postListPresenter.swipeDownStart();
        });

        //init for MaterialViewPager
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), binding.myRecyclerView, null);
        //start load data in db
        getLoaderManager().initLoader(0, null, postListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.postListPresenter.setView(this);
        if (!isDataLoaded && getUserVisibleHint()) {
            this.postListPresenter.initialize();
        }
    }

    @Override
    public void onAttach(Context context) {
        //Called when the fragment has been associated with the activity
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.postListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.postListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.postListPresenter.destroy();
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
                this.postListPresenter.initialize();
            }
        }
    }

    //<editor-fold desc="Called by Presenter"
    @Override
    public void showLoading() {
        binding.swipeRefreshLayout.setRefreshing(true, RefreshDirection.TOP);
    }

    @Override
    public void hideLoading() {
        binding.swipeRefreshLayout.setRefreshing(false, RefreshDirection.TOP);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.myRecyclerView, message);
    }

    @Override
    public void showLoadingMore() {
        binding.swipeRefreshLayout.setRefreshing(true, RefreshDirection.BOTTOM);
        binding.myRecyclerView.setLoading(true);
    }

    @Override
    public void hideLoadingMore() {
        binding.swipeRefreshLayout.setRefreshing(false, RefreshDirection.BOTTOM);
        binding.myRecyclerView.setLoading(false);
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
