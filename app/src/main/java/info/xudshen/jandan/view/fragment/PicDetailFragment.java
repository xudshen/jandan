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
import info.xudshen.jandan.databinding.FragmentPicDetailBinding;
import info.xudshen.jandan.domain.model.DuoshuoComment;
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
    @Inject
    DuoshuoCommentDao duoshuoCommentDao;

    private boolean isDataLoaded = false;
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
        if (!isDataLoaded && getUserVisibleHint()) {
            this.picDetailPresenter.initialize(picId);
        }
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null && !isDataLoaded) {
                isDataLoaded = true;
                this.picDetailPresenter.initialize(picId);
            }
        }
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void renderItemDetail(PicItem item) {
        if (binding.itemWithCommentList.getAdapter() == null) {
            List<String> urlList = Splitter.on(",").splitToList(item.getPics());
            DDBindableCursorLoaderRVHeaderAdapter commentAdapter = new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                    .cursorLoader(getActivity(), DuoshuoCommentDao.CONTENT_URI, null, DuoshuoCommentDao.Properties.ThreadKey.columnName + " = ?", new String[]{Constants.THREAD_PREFIX + picId}, null)
                    .headerViewHolderCreator((inflater, viewType, parent) -> {
                        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, headerPicDetailSelector(urlList.size()), parent, false);
                        return new DDBindableViewHolder(viewDataBinding);
                    })
                    .headerViewDataBindingVariableAction(viewDataBinding -> {
                        viewDataBinding.setVariable(BR.urls, urlList);
                        viewDataBinding.setVariable(BR.picItem, item);
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

    private int headerPicDetailSelector(int size) {
        switch (size) {
            case 1:
                return R.layout.header_pic_detail_1;
            case 2:
                return R.layout.header_pic_detail_2;
            case 3:
                return R.layout.header_pic_detail_3;
            case 4:
                return R.layout.header_pic_detail_4;
            case 5:
                return R.layout.header_pic_detail_5;
            case 6:
                return R.layout.header_pic_detail_6;
            case 7:
                return R.layout.header_pic_detail_7;
            case 8:
                return R.layout.header_pic_detail_8;
            case 9:
                return R.layout.header_pic_detail_9;
            case 10:
                return R.layout.header_pic_detail_10;
            case 11:
                return R.layout.header_pic_detail_11;
            default:
                return R.layout.header_pic_detail_1;
        }
    }
}
