package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.IPostService;
import info.xudshen.jandan.data.api.response.PostListResponse;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.data.dao.SimplePostDao;
import info.xudshen.jandan.data.repository.datasource.PostDataStore;
import info.xudshen.jandan.domain.model.Comment;
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

    public CloudPostDataStore(IPostService postService, PostDao postDao, SimplePostDao simplePostDao) {
        this.postService = postService;
        this.postDao = postDao;
        this.simplePostDao = simplePostDao;
    }

    @Override
    public Observable<Post> post(Long postId) {
        return this.postService.getPostAsync(postId, "")
                .doOnNext(postResponse -> {
                    logger.info("author {}", postResponse.getPostWrapper().getAuthor().getName());
                    for (Comment comment : postResponse.getPostWrapper().getComments()) {
                        logger.info("comment-{}:{}", comment.getName(), comment.getContent());
                    }
                })
                .map(postResponse -> (Post) postResponse.getPostWrapper())
                .doOnNext(post -> {
                    if (post != null) {
                        CloudPostDataStore.this.postDao.insertOrReplace(post);
                    }
                });
    }

    @Override
    public Observable<List<SimplePost>> postList(Long page) {
        return this.postService.getPostListAsync(page)
                .map(postListResponse -> {
                    List<SimplePost> simplePosts = new ArrayList<SimplePost>();
                    for (PostListResponse.PostWrapper postWrapper : postListResponse.getPosts()) {
                        simplePosts.add(postWrapper.getSimplePost());
                    }
                    return simplePosts;
                })
                .doOnNext(simplePosts -> {
                    if (page == 1l) {
                        //TODO: not right
                        CloudPostDataStore.this.simplePostDao.deleteAll();
                    }
                    CloudPostDataStore.this.simplePostDao.insertOrReplaceInTx(simplePosts);
                });
    }
}
