package info.xudshen.jandan.adapter;

import android.app.Activity;

import info.xudshen.droiddata.adapter.impl.DDViewBindingCursorLoaderAdapter;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.ArticleDao;
import info.xudshen.jandan.domain.model.Article;

/**
 * Created by xudshen on 15/10/9.
 */
public class RecyclerViewAdapterFactory {
    public static DDViewBindingCursorLoaderAdapter getArticleListAdapter(Activity activity) {
        return (new DDViewBindingCursorLoaderAdapter(
                activity, ArticleDao.CONTENT_URI, null, null, null, "article_id desc limit 20"))
                .layoutSelector(position -> R.layout.my_text_view)
                .bindingVariableAction((viewDataBinding, cursor) -> {
                    Article article = null;
                    viewDataBinding.setVariable(BR.item, article);
                });
    }
}
