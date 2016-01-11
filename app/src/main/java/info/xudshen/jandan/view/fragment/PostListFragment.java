package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.PostDao;
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
    DDViewBindingCursorLoaderAdapter postListAdapter;
    @Inject
    PostDao postDao;

    public PostListFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.initialize();

        // Inflate the layout for this fragment
        FragmentPostListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_list, container, false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        postListAdapter.onItemClick((itemView, position) -> {
            Snackbar.make(itemView, position + "clicked", Snackbar.LENGTH_LONG).show();
            //viewAdapter.getItemCursor(position);
        });

        binding.myRecyclerView.setLayoutManager(linearLayoutManager);
        binding.myRecyclerView.setAdapter(postListAdapter);

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.amber_700, R.color.deep_purple_a200);
        binding.swipeRefreshLayout.setOnRefreshListener((direction) -> {
            switch (direction) {
                case TOP: {
                    break;
                }
                case BOTTOM: {
                    break;
                }
            }
        });

//        binding.fab.setOnClickListener(v ->
//                binding.myRecyclerView.smoothScrollToPosition(linearLayoutManager.getItemCount() > 0 ? linearLayoutManager.getItemCount() - 1 : 0));
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

    private void initialize() {
        this.getComponent(PostComponent.class).inject(this);
        this.postListPresenter.setView(this);
    }

//    @OnClick(R.id.insertBtn)
//    public void insert() {
//        Post post = new Post();
//        post.setTitle(System.currentTimeMillis() + "");
//        postDao.insert(post);
//    }

    //<editor-fold desc="Called by Presenter">
    @Override
    public void renderPostList() {

    }
    //</editor-fold>
}
