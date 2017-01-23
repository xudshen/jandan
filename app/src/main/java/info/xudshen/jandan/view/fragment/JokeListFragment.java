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
import info.xudshen.jandan.data.dao.JokeItemDao;
import info.xudshen.jandan.databinding.FragmentJokeListBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.internal.di.components.JokeComponent;
import info.xudshen.jandan.presenter.JokeListPresenter;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DataListView;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.widget.RefreshDirection;
import rx.Observable;

public class JokeListFragment extends BaseFragment implements DataListView {
    private static final Logger logger = LoggerFactory.getLogger(JokeListFragment.class);

    public static JokeListFragment newInstance() {
        Bundle args = new Bundle();

        JokeListFragment fragment = new JokeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    JokeListPresenter jokeListPresenter;
    @Inject
    JokeItemDao jokeItemDao;
    DDBindableCursorLoaderRVHeaderAdapter jokeListAdapter;

    private boolean isDataLoaded = false;
    private FragmentJokeListBinding binding;

    public JokeListFragment() {
    }

    @Override
    protected void inject() {
        this.getComponent(JokeComponent.class).inject(this);
    }

    private void initAdapter() {
        jokeListAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(getContext(), JokeItemDao.CONTENT_URI, null, null, null, JokeItemDao.Properties.Date.columnName + " desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> R.layout.joke_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    JokeItem jokeItem = jokeItemDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, jokeItem);
                })
                .build();

        jokeListAdapter.setOnItemClickListener((itemView, position) -> {
            logger.info("position={}", position);
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    itemView, position, ReaderItemType.SimpleJoke);
        });

        jokeListAdapter.addOnItemSubviewClickListener(R.id.comment_vote_oo, (vh, v, position) -> {
            JokeItem comment = jokeItemDao.loadEntity(jokeListAdapter.getItemCursor(position));
            JokeListFragment.this.jokeListPresenter.voteComment(comment.getJokeId(), VoteType.OO);
            logger.info("{}", v);
        });
        jokeListAdapter.addOnItemSubviewClickListener(R.id.comment_vote_xx, (vh, v, position) -> {
            JokeItem comment = jokeItemDao.loadEntity(jokeListAdapter.getItemCursor(position));
            JokeListFragment.this.jokeListPresenter.voteComment(comment.getJokeId(), VoteType.XX);
            logger.info("{}", v);
        });
    }

    private void unBindView() {
        binding.jokeListView.setOnLoadMoreListener(null);
        binding.jokeListLayout.setOnRefreshListener(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_list, container, false);
        initAdapter();

        binding.jokeListView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.jokeListView.setAdapter(jokeListAdapter);
        binding.jokeListView.setOnLoadMoreListener(() -> {
            this.jokeListPresenter.swipeUpStart();
        });

        binding.jokeListLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.jokeListLayout.setOnRefreshListener(() -> {
            this.jokeListPresenter.swipeDownStart();
        });

        //init for MaterialViewPager
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), binding.jokeListView);
        //start load data in db
        getLoaderManager().initLoader(0, null, jokeListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.jokeListPresenter.setView(this);
        this.jokeListPresenter.setVoteCommentView(new ActionView() {
            @Override
            public void showSuccess() {
                showSnackbar(binding.jokeListView, getString(R.string.vote_success));
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
                showSnackbar(binding.jokeListView, message);
            }

            @Override
            public Context context() {
                return JokeListFragment.this.getContext();
            }

            @Override
            public <T> Observable.Transformer<T, T> bindToLifecycle() {
                return JokeListFragment.this.bindToLifecycle();
            }
        });
        if (!isDataLoaded && getUserVisibleHint()) {
            this.jokeListPresenter.initialize();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.jokeListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.jokeListPresenter.resume();
    }

    @Override
    public void onDestroy() {
        binding.jokeListView.setAdapter(null);
        unBindView();

        super.onDestroy();
        this.jokeListPresenter.destroy();
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
                this.jokeListPresenter.initialize();
            }
        }
    }

    //<editor-fold desc="Called by Presenter">
    @Override
    public void showLoading() {
        binding.jokeListLayout.setRefreshing(true, RefreshDirection.TOP);
    }

    @Override
    public void hideLoading() {
        binding.jokeListLayout.setRefreshing(false, RefreshDirection.TOP);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(binding.jokeListView, message);
    }

    @Override
    public void showLoadingMore() {
        binding.jokeListLayout.setRefreshing(true, RefreshDirection.BOTTOM);
        binding.jokeListView.setLoading(true);
    }

    @Override
    public void hideLoadingMore() {
        binding.jokeListLayout.setRefreshing(false, RefreshDirection.BOTTOM);
        binding.jokeListView.setLoading(false);
    }

    @Override
    public void renderDataList() {

    }

    @Override
    public Context context() {
        return getContext();
    }
    //</editor-fold>
}
