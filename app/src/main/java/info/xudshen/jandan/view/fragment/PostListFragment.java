package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.jandan.R;
import info.xudshen.jandan.databinding.FragmentPostListBinding;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.presenter.PostListPresenter;
import info.xudshen.jandan.view.PostListView;

public class PostListFragment extends BaseFragment implements PostListView {
    public static PostListFragment newInstance() {
        return new PostListFragment();
    }

    @Inject
    PostListPresenter postListPresenter;
    @Inject
    @Named("postListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter postListAdapter;

    private FragmentPostListBinding binding;

    public PostListFragment() {
        super();
    }

    @Override
    public void inject() {
        this.getComponent(PostComponent.class).inject(this);
        this.postListPresenter.setView(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_list, container, false);

        postListAdapter.setOnItemClickListener((itemView, position) -> {
            Snackbar.make(itemView, position + "clicked", Snackbar.LENGTH_LONG).show();
            //viewAdapter.getItemCursor(position);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.myRecyclerView.setLayoutManager(linearLayoutManager);
        binding.myRecyclerView.setAdapter(postListAdapter);

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        binding.swipeRefreshLayout.setOnRefreshListener((direction) -> {
            switch (direction) {
                case TOP: {
                    this.postListPresenter.swipeDownStart();
                    break;
                }
                case BOTTOM: {
                    this.postListPresenter.swipeUpStart();
                    break;
                }
            }
        });

//        binding.fab.setOnClickListener(v ->
//                binding.myRecyclerView.smoothScrollToPosition(linearLayoutManager.getItemCount() > 0 ? linearLayoutManager.getItemCount() - 1 : 0));

        //init for MaterialViewPager
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), binding.myRecyclerView, null);
        //start load data in db
        getLoaderManager().initLoader(0, null, postListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        ButterKnife.unbind(this);
    }

    //<editor-fold desc="Called by Presenter">
    @Override
    public void swipeDownFinished() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void swipeUpFinished() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }
    //</editor-fold>
}
