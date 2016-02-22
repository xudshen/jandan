package info.xudshen.jandan.data.dao;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.droiddata.dao.IModelTrans;
import info.xudshen.droiddata.dao.IModelObservable;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.model.SimplePost;
import info.xudshen.jandan.domain.model.Author;
import info.xudshen.jandan.domain.model.Category;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.domain.model.PicComment;
import info.xudshen.jandan.data.model.observable.MetaObservable;
import info.xudshen.jandan.data.model.observable.PostObservable;
import info.xudshen.jandan.data.model.observable.SimplePostObservable;
import info.xudshen.jandan.data.model.observable.AuthorObservable;
import info.xudshen.jandan.data.model.observable.CategoryObservable;
import info.xudshen.jandan.data.model.observable.CommentObservable;
import info.xudshen.jandan.data.model.observable.PicItemObservable;
import info.xudshen.jandan.data.model.observable.PicCommentObservable;

public class ModelTrans {

    private static final IModelTrans<Meta, MetaObservable> META_TRANS =
            new IModelTrans<Meta, MetaObservable>() {
                @Override
                public MetaObservable to(Meta entity) {
                    return new MetaObservable(entity);
                }
            };
    private static final IModelTrans<Post, PostObservable> POST_TRANS =
            new IModelTrans<Post, PostObservable>() {
                @Override
                public PostObservable to(Post entity) {
                    return new PostObservable(entity);
                }
            };
    private static final IModelTrans<SimplePost, SimplePostObservable> SIMPLE_POST_TRANS =
            new IModelTrans<SimplePost, SimplePostObservable>() {
                @Override
                public SimplePostObservable to(SimplePost entity) {
                    return new SimplePostObservable(entity);
                }
            };
    private static final IModelTrans<Author, AuthorObservable> AUTHOR_TRANS =
            new IModelTrans<Author, AuthorObservable>() {
                @Override
                public AuthorObservable to(Author entity) {
                    return new AuthorObservable(entity);
                }
            };
    private static final IModelTrans<Category, CategoryObservable> CATEGORY_TRANS =
            new IModelTrans<Category, CategoryObservable>() {
                @Override
                public CategoryObservable to(Category entity) {
                    return new CategoryObservable(entity);
                }
            };
    private static final IModelTrans<Comment, CommentObservable> COMMENT_TRANS =
            new IModelTrans<Comment, CommentObservable>() {
                @Override
                public CommentObservable to(Comment entity) {
                    return new CommentObservable(entity);
                }
            };
    private static final IModelTrans<PicItem, PicItemObservable> PIC_ITEM_TRANS =
            new IModelTrans<PicItem, PicItemObservable>() {
                @Override
                public PicItemObservable to(PicItem entity) {
                    return new PicItemObservable(entity);
                }
            };
    private static final IModelTrans<PicComment, PicCommentObservable> PIC_COMMENT_TRANS =
            new IModelTrans<PicComment, PicCommentObservable>() {
                @Override
                public PicCommentObservable to(PicComment entity) {
                    return new PicCommentObservable(entity);
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

    //<editor-fold desc="TransMeta">
    <TO extends IModelObservable> TO transMeta(Meta entity, IModelTrans<Meta, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getMetaDao().registerExtraOb(entityOb);
        return entityOb;
    }

    MetaObservable transMeta(Meta entity) {
        return transMeta(entity, META_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transMeta(Iterable<Meta> entities, IModelTrans<Meta, TO> trans) {
        List<TO> list = new ArrayList<>();
        MetaDao dao = this.daoSession.getMetaDao();
        for (Meta entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<MetaObservable> transMeta(Iterable<Meta> entities) {
        return transMeta(entities, META_TRANS);
    }
    //</editor-fold>

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

    //<editor-fold desc="TransSimplePost">
    <TO extends IModelObservable> TO transSimplePost(SimplePost entity, IModelTrans<SimplePost, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getSimplePostDao().registerExtraOb(entityOb);
        return entityOb;
    }

    SimplePostObservable transSimplePost(SimplePost entity) {
        return transSimplePost(entity, SIMPLE_POST_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transSimplePost(Iterable<SimplePost> entities, IModelTrans<SimplePost, TO> trans) {
        List<TO> list = new ArrayList<>();
        SimplePostDao dao = this.daoSession.getSimplePostDao();
        for (SimplePost entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<SimplePostObservable> transSimplePost(Iterable<SimplePost> entities) {
        return transSimplePost(entities, SIMPLE_POST_TRANS);
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

    //<editor-fold desc="TransCategory">
    <TO extends IModelObservable> TO transCategory(Category entity, IModelTrans<Category, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getCategoryDao().registerExtraOb(entityOb);
        return entityOb;
    }

    CategoryObservable transCategory(Category entity) {
        return transCategory(entity, CATEGORY_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transCategory(Iterable<Category> entities, IModelTrans<Category, TO> trans) {
        List<TO> list = new ArrayList<>();
        CategoryDao dao = this.daoSession.getCategoryDao();
        for (Category entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<CategoryObservable> transCategory(Iterable<Category> entities) {
        return transCategory(entities, CATEGORY_TRANS);
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

    //<editor-fold desc="TransPicItem">
    <TO extends IModelObservable> TO transPicItem(PicItem entity, IModelTrans<PicItem, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getPicItemDao().registerExtraOb(entityOb);
        return entityOb;
    }

    PicItemObservable transPicItem(PicItem entity) {
        return transPicItem(entity, PIC_ITEM_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transPicItem(Iterable<PicItem> entities, IModelTrans<PicItem, TO> trans) {
        List<TO> list = new ArrayList<>();
        PicItemDao dao = this.daoSession.getPicItemDao();
        for (PicItem entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<PicItemObservable> transPicItem(Iterable<PicItem> entities) {
        return transPicItem(entities, PIC_ITEM_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransPicComment">
    <TO extends IModelObservable> TO transPicComment(PicComment entity, IModelTrans<PicComment, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getPicCommentDao().registerExtraOb(entityOb);
        return entityOb;
    }

    PicCommentObservable transPicComment(PicComment entity) {
        return transPicComment(entity, PIC_COMMENT_TRANS);
    }

    <TO extends IModelObservable> Iterable<TO> transPicComment(Iterable<PicComment> entities, IModelTrans<PicComment, TO> trans) {
        List<TO> list = new ArrayList<>();
        PicCommentDao dao = this.daoSession.getPicCommentDao();
        for (PicComment entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    Iterable<PicCommentObservable> transPicComment(Iterable<PicComment> entities) {
        return transPicComment(entities, PIC_COMMENT_TRANS);
    }
    //</editor-fold>
}