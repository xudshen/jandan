package info.xudshen.jandan.data.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import info.xudshen.droiddata.dao.IModelObservable;
import info.xudshen.droiddata.dao.IModelTrans;
import info.xudshen.jandan.data.dao.ArticleDao;
import info.xudshen.jandan.data.dao.DaoSession;
import info.xudshen.jandan.data.model.observable.ArticleObservable;
import info.xudshen.jandan.domain.model.Article;

/**
 * Created by xudshen on 15/10/27.
 */
public class ModelTrans {
    private static final IModelTrans<Article, ArticleObservable> ARTICLE_TRANS =
            new IModelTrans<Article, ArticleObservable>() {
                @Override
                public ArticleObservable to(Article entity) {
                    return new ArticleObservable(entity);
                }
            };

    private DaoSession daoSession;

    @Inject
    public ModelTrans(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    <TO extends IModelObservable> TO trans(Article entity, IModelTrans<Article, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getArticleDao().registerExtraOb(entityOb);
        return entityOb;
    }

    <TO extends IModelObservable> Iterable<TO> trans(Iterable<Article> entities, IModelTrans<Article, TO> trans) {
        List<TO> list = new ArrayList<>();
        ArticleDao dao = this.daoSession.getArticleDao();
        for (Article entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }
}
