package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.IPostService;
import info.xudshen.jandan.data.api.response.PostListResponse;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.model.SimplePost;
import rx.Observable;

/**
 * Created by xudshen on 16/2/16.
 */
public class CloudPostDataStore implements PostDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudPostDataStore.class);

    private final IPostService postService;

    private final PostDao postDao;
    private final SimplePostDao simplePostDao;
    private final MetaDao metaDao;
    private final CommentDao commentDao;

    public CloudPostDataStore(IPostService postService, PostDao postDao, SimplePostDao simplePostDao, MetaDao metaDao, CommentDao commentDao) {
        this.postService = postService;
        this.postDao = postDao;
        this.simplePostDao = simplePostDao;
        this.metaDao = metaDao;
        this.commentDao = commentDao;
    }

    @Override
    public Observable<Post> post(Long postId) {
        return this.postService.getPostAsync(postId)
                .map(postResponse -> postResponse.getPostWrapper().getPost())
                .doOnNext(post -> {
                    if (post != null) {
                        CloudPostDataStore.this.postDao.insertOrReplace(post);
                    }
                });
    }

    private Meta getMeta() {
        Meta meta = null;
        if (this.metaDao.count() == 0) {
            meta = new Meta(1l);
            meta.setPostPage(0l);
            this.metaDao.insertInTx(meta);
        } else {
            meta = this.metaDao.queryBuilder().where(MetaDao.Properties.Id.eq(1l))
                    .build().forCurrentThread().unique();
        }
        return meta;
    }

    @Override
    public Observable<List<SimplePost>> postList() {
        return this.postService.getPostListAsync(1l)
                .map(postListResponse -> {
                    List<SimplePost> simplePosts = new ArrayList<SimplePost>();
                    for (PostListResponse.PostWrapper postWrapper : postListResponse.getPosts()) {
                        simplePosts.add(postWrapper.getSimplePost());
                    }
                    return simplePosts;
                })
                .doOnNext(simplePosts -> {
                    //TODO: not right
                    CloudPostDataStore.this.simplePostDao.deleteAll();
                    CloudPostDataStore.this.simplePostDao.insertOrReplaceInTx(simplePosts);
                })
                .doOnCompleted(() -> {
                    Meta meta = getMeta();
                    meta.setPostPage(1l);
                    this.metaDao.updateInTx(meta);
                });
    }

    @Override
    public Observable<List<SimplePost>> postListNext() {
        Meta meta = getMeta();
        return this.postService.getPostListAsync(meta.getPostPage() + 1)
                .map(postListResponse -> {
                    List<SimplePost> simplePosts = new ArrayList<SimplePost>();
                    for (PostListResponse.PostWrapper postWrapper : postListResponse.getPosts()) {
                        simplePosts.add(postWrapper.getSimplePost());
                    }
                    return simplePosts;
                })
                .doOnNext(simplePosts -> {
                    if (meta.getPostPage() + 1 == 1) {
                        //TODO: not right
                        CloudPostDataStore.this.simplePostDao.deleteAll();
                    }
                    CloudPostDataStore.this.simplePostDao.insertOrReplaceInTx(simplePosts);
                })
                .doOnCompleted(() -> {
                    meta.setPostPage(meta.getPostPage() + 1);
                    this.metaDao.updateInTx(meta);
                });
    }

    @Override
    public Observable<List<Comment>> postCommentList(Long postId) {
        return this.postService.getCommentListAsync(postId)
                .map(postResponse -> {
                    return postResponse.getPostWrapper().getComments();
                })
                .doOnNext(comments -> {
                    for (Comment comment : comments) {
                        comment.setPostId(postId);
                    }
                    commentDao.insertOrReplaceInTx(comments);
                });
    }
}
