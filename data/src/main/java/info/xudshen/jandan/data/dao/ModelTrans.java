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
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.PicItem;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.domain.model.VideoItem;
import info.xudshen.jandan.domain.model.DuoshuoComment;
import info.xudshen.jandan.data.model.observable.MetaObservable;
import info.xudshen.jandan.data.model.observable.PostObservable;
import info.xudshen.jandan.data.model.observable.SimplePostObservable;
import info.xudshen.jandan.data.model.observable.AuthorObservable;
import info.xudshen.jandan.data.model.observable.CategoryObservable;
import info.xudshen.jandan.data.model.observable.CommentObservable;
import info.xudshen.jandan.data.model.observable.FavoItemObservable;
import info.xudshen.jandan.data.model.observable.PicItemObservable;
import info.xudshen.jandan.data.model.observable.JokeItemObservable;
import info.xudshen.jandan.data.model.observable.VideoItemObservable;
import info.xudshen.jandan.data.model.observable.DuoshuoCommentObservable;

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
    private static final IModelTrans<FavoItem, FavoItemObservable> FAVO_ITEM_TRANS =
            new IModelTrans<FavoItem, FavoItemObservable>() {
                @Override
                public FavoItemObservable to(FavoItem entity) {
                    return new FavoItemObservable(entity);
                }
            };
    private static final IModelTrans<PicItem, PicItemObservable> PIC_ITEM_TRANS =
            new IModelTrans<PicItem, PicItemObservable>() {
                @Override
                public PicItemObservable to(PicItem entity) {
                    return new PicItemObservable(entity);
                }
            };
    private static final IModelTrans<JokeItem, JokeItemObservable> JOKE_ITEM_TRANS =
            new IModelTrans<JokeItem, JokeItemObservable>() {
                @Override
                public JokeItemObservable to(JokeItem entity) {
                    return new JokeItemObservable(entity);
                }
            };
    private static final IModelTrans<VideoItem, VideoItemObservable> VIDEO_ITEM_TRANS =
            new IModelTrans<VideoItem, VideoItemObservable>() {
                @Override
                public VideoItemObservable to(VideoItem entity) {
                    return new VideoItemObservable(entity);
                }
            };
    private static final IModelTrans<DuoshuoComment, DuoshuoCommentObservable> DUOSHUO_COMMENT_TRANS =
            new IModelTrans<DuoshuoComment, DuoshuoCommentObservable>() {
                @Override
                public DuoshuoCommentObservable to(DuoshuoComment entity) {
                    return new DuoshuoCommentObservable(entity);
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
    public <TO extends IModelObservable> TO transMeta(Meta entity, IModelTrans<Meta, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getMetaDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public MetaObservable transMeta(Meta entity) {
        return transMeta(entity, META_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transMeta(Iterable<Meta> entities, IModelTrans<Meta, TO> trans) {
        List<TO> list = new ArrayList<>();
        MetaDao dao = this.daoSession.getMetaDao();
        for (Meta entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<MetaObservable> transMeta(Iterable<Meta> entities) {
        return transMeta(entities, META_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransPost">
    public <TO extends IModelObservable> TO transPost(Post entity, IModelTrans<Post, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getPostDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public PostObservable transPost(Post entity) {
        return transPost(entity, POST_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transPost(Iterable<Post> entities, IModelTrans<Post, TO> trans) {
        List<TO> list = new ArrayList<>();
        PostDao dao = this.daoSession.getPostDao();
        for (Post entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<PostObservable> transPost(Iterable<Post> entities) {
        return transPost(entities, POST_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransSimplePost">
    public <TO extends IModelObservable> TO transSimplePost(SimplePost entity, IModelTrans<SimplePost, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getSimplePostDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public SimplePostObservable transSimplePost(SimplePost entity) {
        return transSimplePost(entity, SIMPLE_POST_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transSimplePost(Iterable<SimplePost> entities, IModelTrans<SimplePost, TO> trans) {
        List<TO> list = new ArrayList<>();
        SimplePostDao dao = this.daoSession.getSimplePostDao();
        for (SimplePost entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<SimplePostObservable> transSimplePost(Iterable<SimplePost> entities) {
        return transSimplePost(entities, SIMPLE_POST_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransAuthor">
    public <TO extends IModelObservable> TO transAuthor(Author entity, IModelTrans<Author, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getAuthorDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public AuthorObservable transAuthor(Author entity) {
        return transAuthor(entity, AUTHOR_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transAuthor(Iterable<Author> entities, IModelTrans<Author, TO> trans) {
        List<TO> list = new ArrayList<>();
        AuthorDao dao = this.daoSession.getAuthorDao();
        for (Author entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<AuthorObservable> transAuthor(Iterable<Author> entities) {
        return transAuthor(entities, AUTHOR_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransCategory">
    public <TO extends IModelObservable> TO transCategory(Category entity, IModelTrans<Category, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getCategoryDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public CategoryObservable transCategory(Category entity) {
        return transCategory(entity, CATEGORY_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transCategory(Iterable<Category> entities, IModelTrans<Category, TO> trans) {
        List<TO> list = new ArrayList<>();
        CategoryDao dao = this.daoSession.getCategoryDao();
        for (Category entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<CategoryObservable> transCategory(Iterable<Category> entities) {
        return transCategory(entities, CATEGORY_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransComment">
    public <TO extends IModelObservable> TO transComment(Comment entity, IModelTrans<Comment, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getCommentDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public CommentObservable transComment(Comment entity) {
        return transComment(entity, COMMENT_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transComment(Iterable<Comment> entities, IModelTrans<Comment, TO> trans) {
        List<TO> list = new ArrayList<>();
        CommentDao dao = this.daoSession.getCommentDao();
        for (Comment entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<CommentObservable> transComment(Iterable<Comment> entities) {
        return transComment(entities, COMMENT_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransFavoItem">
    public <TO extends IModelObservable> TO transFavoItem(FavoItem entity, IModelTrans<FavoItem, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getFavoItemDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public FavoItemObservable transFavoItem(FavoItem entity) {
        return transFavoItem(entity, FAVO_ITEM_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transFavoItem(Iterable<FavoItem> entities, IModelTrans<FavoItem, TO> trans) {
        List<TO> list = new ArrayList<>();
        FavoItemDao dao = this.daoSession.getFavoItemDao();
        for (FavoItem entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<FavoItemObservable> transFavoItem(Iterable<FavoItem> entities) {
        return transFavoItem(entities, FAVO_ITEM_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransPicItem">
    public <TO extends IModelObservable> TO transPicItem(PicItem entity, IModelTrans<PicItem, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getPicItemDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public PicItemObservable transPicItem(PicItem entity) {
        return transPicItem(entity, PIC_ITEM_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transPicItem(Iterable<PicItem> entities, IModelTrans<PicItem, TO> trans) {
        List<TO> list = new ArrayList<>();
        PicItemDao dao = this.daoSession.getPicItemDao();
        for (PicItem entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<PicItemObservable> transPicItem(Iterable<PicItem> entities) {
        return transPicItem(entities, PIC_ITEM_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransJokeItem">
    public <TO extends IModelObservable> TO transJokeItem(JokeItem entity, IModelTrans<JokeItem, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getJokeItemDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public JokeItemObservable transJokeItem(JokeItem entity) {
        return transJokeItem(entity, JOKE_ITEM_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transJokeItem(Iterable<JokeItem> entities, IModelTrans<JokeItem, TO> trans) {
        List<TO> list = new ArrayList<>();
        JokeItemDao dao = this.daoSession.getJokeItemDao();
        for (JokeItem entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<JokeItemObservable> transJokeItem(Iterable<JokeItem> entities) {
        return transJokeItem(entities, JOKE_ITEM_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransVideoItem">
    public <TO extends IModelObservable> TO transVideoItem(VideoItem entity, IModelTrans<VideoItem, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getVideoItemDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public VideoItemObservable transVideoItem(VideoItem entity) {
        return transVideoItem(entity, VIDEO_ITEM_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transVideoItem(Iterable<VideoItem> entities, IModelTrans<VideoItem, TO> trans) {
        List<TO> list = new ArrayList<>();
        VideoItemDao dao = this.daoSession.getVideoItemDao();
        for (VideoItem entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<VideoItemObservable> transVideoItem(Iterable<VideoItem> entities) {
        return transVideoItem(entities, VIDEO_ITEM_TRANS);
    }
    //</editor-fold>

    //<editor-fold desc="TransDuoshuoComment">
    public <TO extends IModelObservable> TO transDuoshuoComment(DuoshuoComment entity, IModelTrans<DuoshuoComment, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.getDuoshuoCommentDao().registerExtraOb(entityOb);
        return entityOb;
    }

    public DuoshuoCommentObservable transDuoshuoComment(DuoshuoComment entity) {
        return transDuoshuoComment(entity, DUOSHUO_COMMENT_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> transDuoshuoComment(Iterable<DuoshuoComment> entities, IModelTrans<DuoshuoComment, TO> trans) {
        List<TO> list = new ArrayList<>();
        DuoshuoCommentDao dao = this.daoSession.getDuoshuoCommentDao();
        for (DuoshuoComment entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<DuoshuoCommentObservable> transDuoshuoComment(Iterable<DuoshuoComment> entities) {
        return transDuoshuoComment(entities, DUOSHUO_COMMENT_TRANS);
    }
    //</editor-fold>
}