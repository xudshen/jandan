package info.xudshen.jandan.internal.di.modules;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.PopupMenu;
import android.webkit.WebView;
import android.widget.ImageView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import info.xudshen.droiddata.adapter.impl.DDBindableCursorLoaderRVHeaderAdapter;
import info.xudshen.droiddata.adapter.impl.DDBindableViewHolder;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.dao.PostDao;
import info.xudshen.jandan.domain.executor.PostExecutionThread;
import info.xudshen.jandan.domain.executor.ThreadExecutor;
import info.xudshen.jandan.domain.interactor.GetPostDetail;
import info.xudshen.jandan.domain.interactor.UseCase;
import info.xudshen.jandan.domain.model.Post;
import info.xudshen.jandan.domain.repository.PostRepository;
import info.xudshen.jandan.internal.di.PerActivity;
import info.xudshen.jandan.utils.HtmlHelper;

/**
 * Created by xudshen on 16/1/7.
 */
@Module
public class PostModule {
    private static final Logger logger = LoggerFactory.getLogger(PostModule.class);
    private Long postId = -1l;

    public PostModule() {
    }

    public PostModule(Long postId) {
        this.postId = postId;
    }

    @Provides
    @PerActivity
    @Named("postListAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostListAdapter(Activity activity, PostDao postDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, PostDao.CONTENT_URI, null, null, null, "post_id desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    return new DDBindableViewHolder(inflater.inflate(
                            com.github.florent37.materialviewpager.R.layout.material_view_pager_placeholder,
                            parent, false));
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.post_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    Post post = postDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, post);
                })
                .onItemSubViewClickListener(R.id.post_card_more_btn, ((v, position) -> {
                    logger.info("{} more clicked", position);
                    PopupMenu popupMenu = new PopupMenu(activity, v);
                    popupMenu.setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()) {
                            case R.id.post_card_more_menu_favo:
                                logger.info("Favo Clicked");
                                return true;
                            case R.id.post_card_more_menu_later:
                                logger.info("Later Clicked");
                                return true;
                            default:
                                return false;
                        }
                    });
                    popupMenu.inflate(R.menu.post_card_more_menu);
                    popupMenu.show();
                }))
                .build();
    }

    @Provides
    @PerActivity
    @Named("postCommentAdapter")
    DDBindableCursorLoaderRVHeaderAdapter providePostCommentAdapter(Activity activity, PostDao postDao) {
        return new DDBindableCursorLoaderRVHeaderAdapter.Builder<DDBindableViewHolder>()
                .cursorLoader(activity, PostDao.CONTENT_URI, null, null, null, "post_id desc")
                .headerViewHolderCreator((inflater, viewType, parent) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.header_post_detail, parent, false);
                    return new DDBindableViewHolder(viewDataBinding);
                })
                .headerViewDataBindingVariableAction(viewDataBinding1 -> {
                    WebView postDetailBody = (WebView) viewDataBinding1.getRoot().findViewById(R.id.post_detail_body);
                    String summary = "<p>如果你觉得在冬天减肥这件事变得越来越困难，那么解决办法也很简单，你只要买一个屏幕扁平的电视机或者一台超薄笔记本电脑就行。一项新研究发现在你身边摆满苗条的物品能让你觉得自己变瘦了。</p>\\n<p><img src=\"http://tankr.net/s/medium/QJL7.jpg\" alt=\"近瘦者瘦：轻薄物件让你自我感觉更苗条\" /></p>\\n<p>即便体重秤显示你并没有变轻，但使用一台较小的手机、轻量级笔记本电脑或者在家里放优雅的蜡烛和花瓶能让人们相信自己变瘦了。</p>\\n<p>美国威斯康星大学麦迪逊分校和哥伦比亚商学院的研究人员们让185名成年人评估一款470毫升的旅行杯。其中一半人拿到手的是较矮的12.7厘米高的杯子，剩下的人拿到的则是较高的17.8厘米的杯子。接着研究人员们将这两组人分开，有些人被告知最终他们能将杯子带回家，而有些人则被告知他们有其它礼物。</p>\\n<p>每位参与者都必须描述他们拿到的杯子的优缺点并从一个列表中为它挑选一个销售名字。参与者们还需给出他们自己的身高和对自己外表的看法等个人数据。</p>\\n<p>分析显示拿到较矮杯子的人觉得他们自己更矮，且对自己的外表更不自信。与此同时，那些拿到17.8厘米高的杯子之人觉得自己更高更瘦，那些将较矮的杯子交还回去的人也一样。</p>\\n<p><img src=\"http://tankr.net/s/medium/NKRM.jpg\" alt=\"近瘦者瘦：轻薄物件让你自我感觉更苗条\" /></p>\\n<p>这项发表在消费者研究期刊上的研究写道：“我们认为物质环境中的标准的使用，会促使一个人的自我评价与物品的特性发生同化或者出现反差。比如，若人们将MacBook Air的苗条当作瘦的标准，那么这有可能促使人们认为他们自己和这台笔记本电脑一样，觉得自己更瘦更健康；或者与这台苗条设备相反，人们觉得自己更胖更不健康。我们发现人们在购买某种物品的时候，他们不仅会取得该物品的控制权，还有可能将这种控制权交给他们购买的物品，潜意识地允许物品有系统地影响人们的自我评价和行为表现。这说明‘有改革能力’的这一新奇礼物分类非常有市场。消费者、经理和市场销售人员也许会因将这类礼物送给爱人、员工或者消费者而获利，比如轻轻地‘推动’他们的需求方向，让他们变得更诚实、更富有创造力或者更大方。”</p>\\n<p>伦敦室内装修专家Andrea Maflin表示：“这些非凡发现背后蕴藏的心理学非常迷人。研究人员们好像找到了人们如何对家庭环境做出反应的某个元素，似乎仅仅打开一个人家里的橱柜就能看到他们的大脑内部一样。人们天生就对令他们开心的形状着迷，我注意到体型更大的顾客确实更喜欢更重更大的家具。形状、颜色和灯光也能影响一个人的态度，如果我想让一个房间看上去更加行云流水，那么我只需将房间里的笨重家具移出即可。”</p>\\n<p><em>[<a href=\"http://jandan.net/2016/01/18/feel-thinner.html\">桃子</a> via <a target=_blank rel=\"external\" href=\"http://www.dailymail.co.uk/news/article-3402112/Want-feel-thinner-slimmer-phone-Buying-flat-screen-television-ultra-laptop-convince-people-ve-lost-weight.html\">DailyMail</a>]</em></p>\\n<div class=\"share-links\">\\n        <a href=\"http://service.weibo.com/share/share.php?appkey=43259970&searchPic=true&url=http%3A%2F%2Fjandan.net%2F2016%2F01%2F18%2Ffeel-thinner.html&title=%E3%80%90%E8%BF%91%E7%98%A6%E8%80%85%E7%98%A6%EF%BC%9A%E8%BD%BB%E8%96%84%E7%89%A9%E4%BB%B6%E8%AE%A9%E4%BD%A0%E8%87%AA%E6%88%91%E6%84%9F%E8%A7%89%E6%9B%B4%E8%8B%97%E6%9D%A1%E3%80%91%E4%B8%80%E9%A1%B9%E6%96%B0%E7%A0%94%E7%A9%B6%E5%8F%91%E7%8E%B0%E5%9C%A8%E4%BD%A0%E8%BA%AB%E8%BE%B9%E6%91%86%E6%BB%A1%E8%8B%97%E6%9D%A1%E7%9A%84%E7%89%A9%E5%93%81%E8%83%BD%E8%AE%A9%E4%BD%A0%E8%A7%89%E5%BE%97%E8%87%AA%E5%B7%B1%E5%8F%98%E7%98%A6%E4%BA%86%E3%80%82%40%E7%85%8E%E8%9B%8B%E7%BD%91&pic=http%3A%2F%2Fjandan.net%2Fshare%2F2016%2F01%2Fp-74096.gif&style=number&language=zh_cn&button=pubilish\" class=\"share-link share-link-weibo\" target=\"_blank\">分享到微博</a>\\n            <a href=\"javascript:;\" class=\"share-link share-link-weixin\"><img src=\"http://jandan.net/share/2016/01/qr-74096.png\">分享到微信</a>\\n    </div>\\n";
                    summary = HtmlHelper.formBody(summary);
                    postDetailBody.loadDataWithBaseURL(null, summary, "text/html; charset=UTF-8", null, null);
                    postDetailBody.setOnLongClickListener(v -> true);
                })
                .itemViewHolderCreator(((inflater1, viewType1, parent1) -> {
                    ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater1, viewType1, parent1, false);
                    return new DDBindableViewHolder(viewDataBinding);
                }))
                .itemLayoutSelector(position -> R.layout.post_card_view)
                .itemViewDataBindingVariableAction((viewDataBinding, cursor) -> {
                    Post post = postDao.loadEntity(cursor);
                    viewDataBinding.setVariable(BR.item, post);
                })
                .build();
    }

    @Provides
    @PerActivity
    @Named("postDetail")
    UseCase provideGetPostDetailUseCase(PostRepository postRepository,
                                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetPostDetail(postId, postRepository, threadExecutor, postExecutionThread);
    }
}
