package info.xudshen.jandan.view.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.databinding.FragmentFavoBinding;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.VideoItem;
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

    @Inject
    @Named("favoListAdapter")
    DDBindableCursorLoaderRVAdapter favoListAdapter;
    @Inject
    FavoListPresenter favoListPresenter;
    @Inject
    FavoItemDao favoItemDao;

    private FragmentFavoBinding binding;

    @Override
    public void inject() {
        getComponent(FavoComponent.class).inject(this);

        favoListPresenter.setDeleteDataView(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favo, container, false);

        favoListAdapter.setOnItemClickListener((v, position) -> {
            getNavigator().launchItemReader((BaseActivity) getActivity(),
                    v, position, ReaderItemType.Multi);
        });

        favoListAdapter.addOnItemSubviewClickListener(R.id.play_buttom, (v, position) -> {
            FavoItem comment = favoItemDao.loadEntity(favoListAdapter.getItemCursor(position));
            HtmlHelper.openInBrowser(getActivity(), comment.getVideoLink());
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.favoList.setLayoutManager(linearLayoutManager);
        binding.favoList.setAdapter(favoListAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
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