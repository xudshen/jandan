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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.databinding.FragmentPicDetailBinding;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.internal.di.components.PicComponent;
import info.xudshen.jandan.presenter.PicDetailPresenter;
import info.xudshen.jandan.view.DataDetailView;

/**
 * Created by xudshen on 16/2/21.
 */
public class PicDetailFragment extends BaseFragment implements DataDetailView<PicItem> {
    private static final Logger logger = LoggerFactory.getLogger(PicDetailFragment.class);
    public static final String ARG_PIC_ID = "ARG_PIC_ID";

    public static PicDetailFragment newInstance(Long picId) {
        Bundle args = new Bundle();
        args.putLong(ARG_PIC_ID, picId);
        PicDetailFragment fragment = new PicDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    PicDetailPresenter picDetailPresenter;

    private Long picId;
    private FragmentPicDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picId = getArguments().getLong(ARG_PIC_ID);
    }

    @Override
    protected void inject() {
        this.getComponent(PicComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inject();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pic_detail, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.itemWithCommentList.setLayoutManager(linearLayoutManager);

        binding.itemWithCommentLayout.setOnRefreshListener(direction -> {
            switch (direction) {
                case BOTTOM: {
                    PicDetailFragment.this.picDetailPresenter.refreshComment(picId);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.picDetailPresenter.setView(this);
        this.picDetailPresenter.initialize(picId);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.picDetailPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.picDetailPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.picDetailPresenter.destroy();
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void renderItemDetail(PicItem item) {
        if (binding.itemWithCommentList.getAdapter() == null) {
            DDBindableCursorLoaderRVHeaderAdapter picCommentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), CommentDao.CONTENT_URI, null, "post_id = ?", new String[]{picId.toString()}, null)
                    .headerViewHolderCreator((inflater, viewType, parent) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.header_pic_detail, parent, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    })
                    .headerViewDataBindingVariableAction(viewDataBinding -> {
                        viewDataBinding.setVariable(BR.picItem, item);
                    })
                    .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    }))
                    .itemLayoutSelector(position -> R.layout.post_comment_item)
                    .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    })
                    .build();
            binding.itemWithCommentList.setAdapter(picCommentAdapter);

            getLoaderManager().initLoader(0, null, picCommentAdapter);
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
