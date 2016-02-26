package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.internal.di.PerActivity;

/**
 * Created by xudshen on 16/2/26.
 */
@Module
public class FavoModule {
    private static final Logger logger = LoggerFactory.getLogger(FavoModule.class);

    public FavoModule() {
    }

    @Provides
    @PerActivity
    @Named("favoListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, PicItemDao picItemDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, FavoItemDao.CONTENT_URI, null, null, null, FavoItemDao.Properties.AddDate.columnName + " desc")
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector((position, cursor) -> R.layout.pic_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    PicItem picItem = picItemDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, picItem);
                })
                .build();
    }
}
