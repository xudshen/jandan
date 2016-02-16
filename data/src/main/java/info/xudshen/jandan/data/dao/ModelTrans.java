package info.xudshen.jandan.data.dao;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.droiddata.dao.IModelTrans;
import info.xudshen.droiddata.dao.IModelObservable;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.model.Author;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.data.model.observable.PostObservable;
import info.xudshen.jandan.data.model.observable.AuthorObservable;
import info.xudshen.jandan.data.model.observable.CommentObservable;

public class ModelTrans {

    private static final IModelTrans<Post, PostObservable> POST_TRANS =
            new IModelTrans<Post, PostObservable>() {
                @Override
                public PostObservable to(Post entity) {
                    return new PostObservable(entity);
                }
            };
    private static final IModelTrans<Author, AuthorObservable> AUTHOR_TRANS =
            new IModelTrans<Author, AuthorObservable>() {
                @Override
                public AuthorObservable to(Author entity) {
                    return new AuthorObservable(entity);
                }
            };
    private static final IModelTrans<Comment, CommentObservable> COMMENT_TRANS =
            new IModelTrans<Comment, CommentObservable>() {
                @Override
                public CommentObservable to(Comment entity) {
                    return new CommentObservable(entity);
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

    //<editor-fold desc="TransPost">
    <TO extends IModelObservable> TO transPost(Post entity, IModelTrans<Post, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getPostDao().registerExtraOb(entityOb);
        return entityOb;
    }

    PostObservable transPost(Post entity) {
        return transPost(entity, POST_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transPost(Iterable<Post> entities, IModelTrans<Post, TO> trans) {
        List<TO> list = new ArrayList<>();
        PostDao dao = this.daoSession.getPostDao();
        for (Post entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<PostObservable> transPost(Iterable<Post> entities) {
        return transPost(entities, POST_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransAuthor">
    <TO extends IModelObservable> TO transAuthor(Author entity, IModelTrans<Author, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getAuthorDao().registerExtraOb(entityOb);
        return entityOb;
    }

    AuthorObservable transAuthor(Author entity) {
        return transAuthor(entity, AUTHOR_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transAuthor(Iterable<Author> entities, IModelTrans<Author, TO> trans) {
        List<TO> list = new ArrayList<>();
        AuthorDao dao = this.daoSession.getAuthorDao();
        for (Author entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<AuthorObservable> transAuthor(Iterable<Author> entities) {
        return transAuthor(entities, AUTHOR_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransComment">
    <TO extends IModelObservable> TO transComment(Comment entity, IModelTrans<Comment, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getCommentDao().registerExtraOb(entityOb);
        return entityOb;
    }

    CommentObservable transComment(Comment entity) {
        return transComment(entity, COMMENT_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transComment(Iterable<Comment> entities, IModelTrans<Comment, TO> trans) {
        List<TO> list = new ArrayList<>();
        CommentDao dao = this.daoSession.getCommentDao();
        for (Comment entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<CommentObservable> transComment(Iterable<Comment> entities) {
        return transComment(entities, COMMENT_TRANS);
    }
    //</editor-fold>
}