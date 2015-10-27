package info.xudshen.jandan.data.dao;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.droiddata.dao.IModelTrans;
import info.xudshen.droiddata.dao.IModelObservable;
import info.xudshen.jandan.domain.model.Article;
import info.xudshen.jandan.domain.model.Joke;
import info.xudshen.jandan.data.model.observable.ArticleObservable;
import info.xudshen.jandan.data.model.observable.JokeObservable;

public class ModelTrans {

    private static final IModelTrans<Article, ArticleObservable> ARTICLE_TRANS =
            new IModelTrans<Article, ArticleObservable>() {
                @Override
                public ArticleObservable to(Article entity) {
                    return new ArticleObservable(entity);
                }
            };
    private static final IModelTrans<Joke, JokeObservable> JOKE_TRANS =
            new IModelTrans<Joke, JokeObservable>() {
                @Override
                public JokeObservable to(Joke entity) {
                    return new JokeObservable(entity);
                }
            };

    /**
     * This must be set from outside, it's recommended to do this inside your Application object.
     * Subject to change (static isn't nice).
     */
    public DaoSession daoSession;

    public ModelTrans(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    <TO extends IModelObservable> TO transArticle(Article entity, IModelTrans<Article, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getArticleDao().registerExtraOb(entityOb);
        return entityOb;
    }

    <TO extends IModelObservable> Iterable<TO> transArticle(Iterable<Article> entities, IModelTrans<Article, TO> trans) {
        List<TO> list = new ArrayList<>();
        ArticleDao dao = this.daoSession.getArticleDao();
        for (Article entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    <TO extends IModelObservable> TO transJoke(Joke entity, IModelTrans<Joke, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getJokeDao().registerExtraOb(entityOb);
        return entityOb;
    }

    <TO extends IModelObservable> Iterable<TO> transJoke(Iterable<Joke> entities, IModelTrans<Joke, TO> trans) {
        List<TO> list = new ArrayList<>();
        JokeDao dao = this.daoSession.getJokeDao();
        for (Joke entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }
}