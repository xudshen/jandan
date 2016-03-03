package info.xudshen.jandan.view.fragment;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.databinding.FragmentFavoBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
import info.xudshen.jandan.internal.di.components.FavoComponent;
import info.xudshen.jandan.presenter.FavoListPresenter;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.view.DeleteDataView;
import info.xudshen.jandan.view.activity.BaseActivity;

public class FavoFragment extends BaseFragment implements DeleteDataView {
    private static final Logger logger = LoggerFactory.getLogger(FavoFragment.class);

    public static FavoFragment newInstance() {
        Bundle args = new Bundle();
        FavoFragment fragment = new FavoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    DDBindableCursorLoaderRVAdapter favoListAdapter;
    @Inject
    FavoListPresenter favoListPresenter;
    @Inject
    FavoItemDao favoItemDao;

    private FragmentFavoBinding binding;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public void inject() {
        getComponent(FavoComponent.class).inject(this);

        favoListPresenter.setDeleteDataView(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initAdapter() {
        favoListAdapter = new DDBindableCursorLoaderRVAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(getContext(), FavoItemDao.CONTENT_URI, null, null, null, FavoItemDao.Properties.AddDate.columnName + " desc")
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    switch (viewType1) {
                        case R.layout.video_card_view: {
                            FloatingActionButton button = (FloatingActionButton) viewDataBinding.getRoot().findViewById(R.id.play_buttom);
                            button.setImageDrawable(new IconicsDrawable(getContext())
                                    .icon(GoogleMaterial.Icon.gmd_play_circle_filled)
                                    .color(getContext().getResources().getColor(R.color.md_white_1000))
                                    .sizeDp(20)
                                    .paddingDp(2));
                            break;
                        }
                    }

                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> {
                    FavoItem favoItem = favoItemDao.loadEntity(cursor);
                    switch (favoItem.getType()) {
                        case SimplePost: {
                            return R.layout.post_card_view;
                        }
                        case SimpleJoke: {
                            return R.layout.joke_card_view;
                        }
                        case SimpleVideo: {
                            return R.layout.video_card_view;
                        }
                        case SimplePic: {
                            return R.layout.pic_card_view;
                        }
                        default: {
                            return R.layout.fragment_blank;
                        }
                    }
                })
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    FavoItem favoItem = favoItemDao.loadEntity(cursor);
                    switch (favoItem.getType()) {
                        case SimplePost: {
                            viewDataBinding.setVariable(BR.item, FavoItemTrans.toSimplePost(favoItem));
                            break;
                        }
                        case SimpleJoke: {
                            viewDataBinding.setVariable(BR.item, FavoItemTrans.toJokeItem(favoItem));
                            break;
                        }
                        case SimpleVideo: {
                            viewDataBinding.setVariable(BR.item, FavoItemTrans.toVideoItem(favoItem));
                            break;
                        }
                        case SimplePic: {
                            viewDataBinding.setVariable(BR.item, FavoItemTrans.toPicItem(favoItem));
                            ImageView imageView = (ImageView) viewDataBinding.getRoot().findViewById(R.id.item_thumb_image);
                            Glide.with(FavoFragment.this)
                                    .load(favoItem.getPicFirst())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.placeholder)
                                    .centerCrop()
                                    .crossFade()
                                    .into(imageView);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                })
                .build();

        favoListAdapter.setOnItemClickListener((v, position) -> {
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    v, position, ReaderItemType.Multi);
        });

        favoListAdapter.addOnItemSubviewClickListener(R.id.play_buttom, (vh, v, position) -> {
            FavoItem comment = favoItemDao.loadEntity(favoListAdapter.getItemCursor(position));
            HtmlHelper.openInBrowser(getActivity(), comment.getVideoLink());
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favo, container, false);
        initAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.favoList.setLayoutManager(linearLayoutManager);
        binding.favoList.setAdapter(favoListAdapter);

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                logger.info("swiped{}", viewHolder.getAdapterPosition());
                FavoItem favoItem = favoItemDao.loadEntity(favoListAdapter.getItemCursor(viewHolder.getAdapterPosition()));
                favoListPresenter.deleteFavoItem(favoItem.getType(), favoItem.getActualId());
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.favoList);

        setActionBarDrawerToggle(binding.toolbar);
        binding.toolbar.setTitle(R.string.drawer_favorites);

        getLoaderManager().initLoader(0, null, favoListAdapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (favoItemDao.queryBuilder().buildCount().count() == 0) {
            binding.favoList.setVisibility(View.GONE);
            binding.blankPlaceholder.setVisibility(View.VISIBLE);
        }
        favoListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        favoListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        itemTouchHelper = null;
        binding.favoList.setAdapter(null);

        super.onDestroy();
        favoListPresenter.destroy();
    }

    @Override
    public void deletingData() {

    }

    @Override
    public void result(boolean success) {
        if (success) {
            showSnackbar(binding.favoList, getString(R.string.favo_deleted));
        }
        if (favoItemDao.queryBuilder().buildCount().count() == 0) {
            binding.favoList.setVisibility(View.GONE);
            binding.blankPlaceholder.setVisibility(View.VISIBLE);
        }
    }
}