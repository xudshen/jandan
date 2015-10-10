package info.xudshen.jandan.adapter;

import android.app.Activity;

import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.R;
import info.xudshen.jandan.model.Article;
import info.xudshen.jandan.model.ArticleDao;

/**
 * Created by xudshen on 15/10/9.
 */
public class RecyclerViewAdapterFactory {
    public static DDViewBindingCursorLoaderAdapter getArticleListAdapter(Activity activity) {
        return (new DDViewBindingCursorLoaderAdapter(
                activity, ArticleDao.CONTENT_URI, null, null, null, null))
                .layoutSelector(position -> R.layout.my_text_view)
                .bindingVariableAction((viewDataBinding, cursor) -> {
                    Article article = JandanApp.daoSession.getArticleDao().readEntity(cursor, 0);
                    viewDataBinding.setVariable(BR.item, article);
                });
    }
}
