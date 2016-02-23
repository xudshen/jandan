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
import info.xudshen.jandan.databinding.FragmentJokeListBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.internal.di.components.JokeComponent;
import info.xudshen.jandan.presenter.JokeListPresenter;
import info.xudshen.jandan.view.DataListView;
import info.xudshen.jandan.view.activity.BaseActivity;
import info.xudshen.jandan.view.widget.RefreshDirection;

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
    @Named("jokeListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter jokeListAdapter;

    private FragmentJokeListBinding binding;

    public JokeListFragment() {
    }

    @Override
    protected void inject() {
        this.getComponent(JokeComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_list, container, false);

        jokeListAdapter.setOnItemClickListener((itemView, position) -> {
            logger.info("position={}", position);
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    itemView, position, ReaderItemType.SimpleJoke);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.jokeListView.setLayoutManager(linearLayoutManager);
        binding.jokeListView.setAdapter(jokeListAdapter);
        binding.jokeListView.setOnLoadMoreListener(() -> {
            logger.info("OnLoadMoreListener");
            this.jokeListPresenter.swipeUpStart();
        });

        binding.jokeListLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.jokeListLayout.setOnRefreshListener(() -> {
            logger.info("OnRefreshListener");
            this.jokeListPresenter.swipeDownStart();
        });

        //init for MaterialViewPager
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), binding.jokeListView, null);
        //start load data in db
        getLoaderManager().initLoader(0, null, jokeListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.jokeListPresenter.setView(this);
        this.jokeListPresenter.initialize();
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
        super.onDestroy();
        this.jokeListPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    }

    @Override
    public void showSwipeUpLoading() {
        binding.jokeListLayout.setRefreshing(true, RefreshDirection.BOTTOM);
        binding.jokeListView.setLoading(true);
    }

    @Override
    public void hideSwipeUpLoading() {
        binding.jokeListLayout.setRefreshing(false, RefreshDirection.BOTTOM);
        binding.jokeListView.setLoading(false);
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
