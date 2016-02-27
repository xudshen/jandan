package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.FavoItemDao;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
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
    DDBindableCursorLoaderRVAdapter providePostListAdapter(Activity activity, FavoItemDao favoItemDao) {
        return new DDBindableCursorLoaderRVAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, FavoItemDao.CONTENT_URI, null, null, null, FavoItemDao.Properties.AddDate.columnName + " desc")
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    switch (viewType1) {
                        case R.layout.video_card_view: {
                            FloatingActionButton button = (FloatingActionButton) viewDataBinding.getRoot().findViewById(R.id.play_buttom);
                            button.setImageDrawable(new IconicsDrawable(activity)
                                    .icon(GoogleMaterial.Icon.gmd_play_circle_filled)
                                    .color(activity.getResources().getColor(R.color.md_white_1000))
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
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                })
                .build();
    }
}
