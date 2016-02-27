package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import info.xudshen.jandan.data.api.IPostService;
import info.xudshen.jandan.data.api.response.CommonResponse;
import info.xudshen.jandan.data.api.response.PostListResponse;
import info.xudshen.jandan.data.dao.CommentDao;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.data.utils.HtmlUtils;
import info.xudshen.jandan.domain.enums.VoteResult;
import info.xudshen.jandan.domain.enums.VoteType;
import info.xudshen.jandan.domain.model.Comment;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.model.SimplePost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

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
                    Meta postPage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.POST_KEY);
                    postPage.setLongValue(1l);
                    this.metaDao.update(postPage);
                });
    }

    @Override
    public Observable<List<SimplePost>> postListNext() {
        Meta postPage = DataStoreHelper.getMeta(this.metaDao, DataStoreHelper.POST_KEY);
        return this.postService.getPostListAsync(postPage.getLongValue() + 1)
                .map(postListResponse -> {
                    List<SimplePost> simplePosts = new ArrayList<SimplePost>();
                    for (PostListResponse.PostWrapper postWrapper : postListResponse.getPosts()) {
                        simplePosts.add(postWrapper.getSimplePost());
                    }
                    return simplePosts;
                })
                .doOnNext(simplePosts -> {
                    if (postPage.getLongValue() + 1 == 1) {
                        //TODO: not right
                        CloudPostDataStore.this.simplePostDao.deleteAll();
                    }
                    CloudPostDataStore.this.simplePostDao.insertOrReplaceInTx(simplePosts);
                })
                .doOnCompleted(() -> {
                    postPage.setLongValue(postPage.getLongValue() + 1);
                    this.metaDao.update(postPage);
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
                        comment.setContent(HtmlUtils.cleanComment(comment.getContent()));
                        comment.setCommentTo(HtmlUtils.getCommentTo(comment.getContent()));
                    }
                    commentDao.insertOrReplaceInTx(comments);
                });
    }

    @Override
    public Observable<List<Comment>> postCommentListNext(Long postId) {
        return this.postService.getCommentListAsync(postId)
                .map(postResponse -> {
                    return postResponse.getPostWrapper().getComments();
                })
                .map(comments -> {
                    //update post
                    Post post = postDao.queryBuilder().where(PostDao.Properties.PostId.eq(postId))
                            .build().forCurrentThread().unique();
                    post.setCommentCount(Long.valueOf(comments.size()));
                    postDao.update(post);

                    List<Comment> commentsInDb = commentDao.queryBuilder().where(CommentDao.Properties.PostId.eq(postId))
                            .build().forCurrentThread().list();
                    HashSet<Long> localCommentIds = new HashSet<Long>();
                    for (Comment comment : commentsInDb) {
                        localCommentIds.add(comment.getCommentId());
                    }

                    List<Comment> newComments = new ArrayList<Comment>();
                    for (Comment comment : comments) {
                        if (!localCommentIds.contains(comment.getCommentId())) {
                            newComments.add(comment);
                        }
                    }
                    return newComments;
                })
                .doOnNext(comments -> {
                    for (Comment comment : comments) {
                        comment.setPostId(postId);
                        comment.setContent(HtmlUtils.cleanComment(comment.getContent()));
                        comment.setCommentTo(HtmlUtils.getCommentTo(comment.getContent()));
                    }
                    commentDao.insertOrReplaceInTx(comments);
                });
    }

    @Override
    public Observable<Boolean> doPostComment(Long postId, String name, String email, String content) {
        return this.postService.postCommentAsync(postId, name, email, content)
                .map(CommonResponse::isOk);
    }

    @Override
    public Observable<VoteResult> voteComment(Long commentId, VoteType voteType) {
        return Observable.create(new Observable.OnSubscribe<VoteResult>() {
            @Override
            public void call(Subscriber<? super VoteResult> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        String s = CloudPostDataStore.this.postService.voteComment(voteType == VoteType.OO ? 1 : 0, commentId).execute().body().string();
                        if (s.lastIndexOf("|") != -1 && s.lastIndexOf("|") != s.length() - 1) {
                            String result = s.substring(s.lastIndexOf("|") + 1, s.length());
                            switch (voteType) {
                                case OO: {
                                    subscriber.onNext(Integer.valueOf(result) == 1 ? VoteResult.Thanks : VoteResult.Voted);
                                }
                                case XX: {
                                    subscriber.onNext(Integer.valueOf(result) == -1 ? VoteResult.Thanks : VoteResult.Voted);
                                }
                                default: {
                                    subscriber.onNext(VoteResult.Voted);
                                }
                            }
                        } else {
                            subscriber.onNext(VoteResult.Voted);
                        }
                        subscriber.onCompleted();
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                }
            }
        }).doOnNext(voteResult -> {
            if (voteResult == VoteResult.Thanks) {
                Comment comment = CloudPostDataStore.this.commentDao.queryBuilder()
                        .where(CommentDao.Properties.CommentId.eq(commentId))
                        .build().forCurrentThread().unique();
                if (voteType == VoteType.OO) {
                    comment.setVotePositive(comment.getVotePositive() + 1);
                    CloudPostDataStore.this.commentDao.update(comment);
                } else if (voteType == VoteType.XX) {
                    comment.setVoteNegative(comment.getVoteNegative() + 1);
                    CloudPostDataStore.this.commentDao.update(comment);
                }
            }
        });
    }
}
