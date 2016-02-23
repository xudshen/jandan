package info.xudshen.jandan.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Splitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.data.dao.DuoshuoCommentDao;
import info.xudshen.jandan.data.model.observable.JokeItemObservable;
import info.xudshen.jandan.databinding.FragmentJokeDetailBinding;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.internal.di.components.JokeComponent;
import info.xudshen.jandan.presenter.JokeDetailPresenter;
import info.xudshen.jandan.view.DataDetailView;

/**
 * Created by xudshen on 16/2/21.
 */
public class JokeDetailFragment extends BaseFragment implements DataDetailView<JokeItemObservable> {
    private static final Logger logger = LoggerFactory.getLogger(JokeDetailFragment.class);
    public static final String ARG_PIC_ID = "ARG_PIC_ID";

    public static JokeDetailFragment newInstance(Long jokeId) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, jokeId);
        JokeDetailFragment fragment = new JokeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    JokeDetailPresenter jokeDetailPresenter;
    @Inject
    DuoshuoCommentDao duoshuoCommentDao;

    private boolean isDataLoaded = false;
    private Long jokeId;
    private FragmentJokeDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jokeId = getArguments().getLong(ARG_PIC_ID);
    }

    @Override
    protected void inject() {
        this.getComponent(JokeComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inject();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_joke_detail, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.itemWithCommentList.setLayoutManager(linearLayoutManager);

        binding.itemWithCommentLayout.setOnRefreshListener(direction -> {
            switch (direction) {
                case BOTTOM: {
                    JokeDetailFragment.this.jokeDetailPresenter.refreshComment(jokeId);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.jokeDetailPresenter.setView(this);
        if (!isDataLoaded && getUserVisibleHint()) {
            this.jokeDetailPresenter.initialize(jokeId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.jokeDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.jokeDetailPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.jokeDetailPresenter.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                this.jokeDetailPresenter.initialize(jokeId);
            }
        }
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void renderItemDetail(JokeItemObservable item) {
        if (binding.itemWithCommentList.getAdapter() == null) {
            DDBindableCursorLoaderRVHeaderAdapter commentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), DuoshuoCommentDao.CONTENT_URI, null, DuoshuoCommentDao.Properties.ThreadKey.columnName + " = ?", new String[]{Constants.THREAD_PREFIX + jokeId}, null)
                    .headerViewHolderCreator((inflater, viewType, parent) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.header_joke_detail, parent, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    })
                    .headerViewDataBindingVariableAction(viewDataBinding -> {
                        viewDataBinding.setVariable(BR.jokeItem, item);
                    })
                    .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    }))
                    .itemLayoutSelector(position -> R.layout.pic_comment_item)
                    .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                        DuoshuoComment duoshuoComment = duoshuoCommentDao.loadEntity(cursor);
                        viewDataBinding.setVariable(BR.comment, duoshuoComment);
                    })
                    .build();
            binding.itemWithCommentList.setAdapter(commentAdapter);

            getLoaderManager().initLoader(0, null, commentAdapter);
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
    public void showSwipeUpLoading() {
        binding.itemWithCommentLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeUpLoading() {
        binding.itemWithCommentLayout.setRefreshing(false);
    }

    @Override
    public void noMoreComments() {
        showSnackbar(binding.itemWithCommentList, getString(R.string.post_detail_no_more_comments));
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
    //</editor-fold>
}
