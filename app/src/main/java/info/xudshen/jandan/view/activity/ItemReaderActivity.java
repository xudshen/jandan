package info.xudshen.jandan.view.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.common.base.Strings;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.constants.Constants;
import info.xudshen.jandan.domain.enums.CommentAction;
import info.xudshen.jandan.domain.enums.ReaderItemType;
import info.xudshen.jandan.domain.model.FavoItem;
import info.xudshen.jandan.domain.model.FavoItemTrans;
import info.xudshen.jandan.domain.model.JokeItem;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.internal.di.components.ActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerJokeComponent;
import info.xudshen.jandan.internal.di.components.DaggerPicComponent;
import info.xudshen.jandan.internal.di.components.DaggerPostComponent;
import info.xudshen.jandan.internal.di.components.DaggerVideoComponent;
import info.xudshen.jandan.internal.di.components.JokeComponent;
import info.xudshen.jandan.internal.di.components.PicComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.internal.di.components.VideoComponent;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.presenter.DoCommentPresenter;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.view.ActionView;
import info.xudshen.jandan.view.DeleteDataView;
import info.xudshen.jandan.view.SaveDataView;
import info.xudshen.jandan.view.adapter.IItemInfo;
import info.xudshen.jandan.view.adapter.JokeReaderPagerAdapter;
import info.xudshen.jandan.view.adapter.PicReaderPagerAdapter;
import info.xudshen.jandan.view.adapter.PostReaderPagerAdapter;
import info.xudshen.jandan.view.adapter.VideoReaderPagerAdapter;
import info.xudshen.jandan.view.transition.StackPageTransformer;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

public class ItemReaderActivity extends BaseActivity implements HasComponents, ActionView {
    private static final Logger logger = LoggerFactory.getLogger(ItemReaderActivity.class);
    public static final String ARG_POSITION = "ARG_POSITION";
    public static final String ARG_READER_TYPE = "ARG_READER_TYPE";

    @Bind(R.id.item_reader_view_pager)
    ViewPager viewPager;
    @Bind(R.id.comment_mask)
    View commentMask;
    @Bind(R.id.comment_area)
    View commentArea;
    @Bind(R.id.comment_area_name)
    EditText commentAreaName;
    @Bind(R.id.comment_area_name_layout)
    TextInputLayout commentAreaNameLayout;
    @Bind(R.id.comment_area_email)
    EditText commentAreaEmail;
    @Bind(R.id.comment_area_email_layout)
    TextInputLayout commentAreaEmailLayout;
    @Bind(R.id.comment_area_content)
    EditText commentAreaContent;
    @Bind(R.id.comment_area_content_layout)
    TextInputLayout commentAreaContentLayout;
    @Bind(R.id.comment_fab)
    FloatingActionButton commentFab;
    @Bind(R.id.comment_send_fab)
    FloatingActionButton commentSendFab;
    @Bind(R.id.comment_send_progress)
    ProgressBar commentSendProgress;

    private PostComponent postComponent;
    private PicComponent picComponent;
    private JokeComponent jokeComponent;
    private VideoComponent videoComponent;
    private ActivityComponent activityComponent;

    private IItemInfo currentItemInfo;
    private int currentPosition;
    private boolean isCurrentFavo = false;
    private MenuItem favoMenu;
    private Drawable favoIcon, favoIconFalse;

    @Inject
    PublishSubject<CommentAction> commentActionSubject;
    Subscription commentActionSubjectSubscription;
    @Inject
    DoCommentPresenter doCommentPresenter;

    @Override
    protected void initializeInjector() {
        ActivityModule activityModule = getActivityModule();
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();
        activityComponent.inject(this);

        postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        picComponent = DaggerPicComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        jokeComponent = DaggerJokeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        videoComponent = DaggerVideoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(activityModule)
                .build();

        doCommentPresenter.setView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reader);
        initializeInjector();

        //do other
        ButterKnife.bind(this);
        initializeIcon();

        ReaderItemType type = (ReaderItemType) getIntent().getExtras().getSerializable(ARG_READER_TYPE);
        switch (type) {
            case SimplePost: {
                PostReaderPagerAdapter postReaderPagerAdapter = new PostReaderPagerAdapter(
                        getSupportFragmentManager(), this);
                postComponent.inject(postReaderPagerAdapter);
                postReaderPagerAdapter.initialize();
                viewPager.setAdapter(postReaderPagerAdapter);
                currentItemInfo = postReaderPagerAdapter;
                break;
            }
            case SimplePic: {
                PicReaderPagerAdapter picReaderPagerAdapter = new PicReaderPagerAdapter(
                        getSupportFragmentManager(), this);
                picComponent.inject(picReaderPagerAdapter);
                picReaderPagerAdapter.initialize();
                viewPager.setAdapter(picReaderPagerAdapter);
                currentItemInfo = picReaderPagerAdapter;
                break;
            }
            case SimpleJoke: {
                JokeReaderPagerAdapter jokeReaderPagerAdapter = new JokeReaderPagerAdapter(
                        getSupportFragmentManager(), this);
                jokeComponent.inject(jokeReaderPagerAdapter);
                jokeReaderPagerAdapter.initialize();
                viewPager.setAdapter(jokeReaderPagerAdapter);
                currentItemInfo = jokeReaderPagerAdapter;
                break;
            }
            case SimpleVideo: {
                VideoReaderPagerAdapter videoReaderPagerAdapter = new VideoReaderPagerAdapter(
                        getSupportFragmentManager(), this);
                videoComponent.inject(videoReaderPagerAdapter);
                videoReaderPagerAdapter.initialize();
                viewPager.setAdapter(videoReaderPagerAdapter);
                currentItemInfo = videoReaderPagerAdapter;
                break;
            }
        }

        viewPager.setPageTransformer(true, new StackPageTransformer());
        currentPosition = getIntent().getExtras().getInt(ARG_POSITION);
        viewPager.setCurrentItem(currentPosition);

        commentFab.setImageDrawable(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_edit)
                .color(getResources().getColor(R.color.md_white_1000))
                .sizeDp(12)
                .paddingDp(2));

        commentSendFab.setImageDrawable(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_send)
                .color(getResources().getColor(R.color.md_white_1000))
                .sizeDp(16)
                .paddingDp(2));

        setUpTextInputLayoutError();

        clearOldFavoInfo();

        //must calculateMetrics after layout rendered
        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                calculateMetrics();

                registerCommentAction();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public <C> C getComponent(Class<C> componentType) {
        if (componentType.isInstance(this.activityComponent)) {
            return (C) this.activityComponent;
        }
        if (componentType.isInstance(this.postComponent)) {
            return (C) this.postComponent;
        }
        if (componentType.isInstance(this.picComponent)) {
            return (C) this.picComponent;
        }
        if (componentType.isInstance(this.jokeComponent)) {
            return (C) this.jokeComponent;
        }
        if (componentType.isInstance(this.videoComponent)) {
            return (C) this.videoComponent;
        }
        throw new IllegalStateException("componentType=" + componentType.getSimpleName() + " not found");
    }

    private void initializeIcon() {
        favoIcon = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_heart)
                .color(getResources().getColor(R.color.md_pink_500))
                .sizeDp(24).paddingDp(2);
        favoIconFalse = new IconicsDrawable(getApplicationContext())
                .icon(FontAwesome.Icon.faw_heart)
                .color(getResources().getColor(R.color.md_white_1000))
                .sizeDp(24).paddingDp(2);
    }

    //<editor-fold desc="Favo Memu">
    private void clearOldFavoInfo() {
        isCurrentFavo = currentItemInfo.isInFavoItem(currentPosition);
        refreshIcon();
    }

    private void refreshIcon() {
        if (favoMenu != null)
            favoMenu.setIcon(isCurrentFavo ? favoIcon : favoIconFalse);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_reader_menu, menu);
        favoMenu = menu.findItem(R.id.item_reader_menu_favo);
        refreshIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.item_reader_menu_favo) {
            if (isCurrentFavo) {
                this.doCommentPresenter.setDeleteDataView(new DeleteDataView() {
                    @Override
                    public void deletingData() {
                        logger.info("deleting");
                        item.setEnabled(false);
                    }

                    @Override
                    public void result(boolean success) {
                        logger.info("deleting:{}", success);
                        item.setEnabled(true);
                        isCurrentFavo = !success;
                        refreshIcon();
                    }

                    @Override
                    public <T> Observable.Transformer<T, T> bindToLifecycle() {
                        return null;
                    }
                });
                this.doCommentPresenter.deleteFavoItem(currentItemInfo.getAdapterItemType(currentPosition),
                        currentItemInfo.getAdapterItemId(currentPosition));
            } else {
                this.doCommentPresenter.setSaveDataView(new SaveDataView() {
                    @Override
                    public void savingData() {
                        logger.info("saving");
                        item.setEnabled(false);
                    }

                    @Override
                    public void result(boolean success) {
                        logger.info("saving:{}", success);
                        item.setEnabled(true);
                        isCurrentFavo = success;
                        refreshIcon();
                    }
                });
                FavoItem favoItem = FavoItemTrans.from(currentItemInfo.getAdapterItem(currentPosition));
                this.doCommentPresenter.saveFavoItem(favoItem);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    @Override
    public void onBackPressed() {
        if (commentArea.getVisibility() == View.VISIBLE) {
            hideCommentArea();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentActionSubjectSubscription.unsubscribe();
    }

    //<editor-fold desc="Called by presenter">
    @Override
    public void showLoading() {
        isSendingComment = true;
        commentSendProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        isSendingComment = false;
        commentSendProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showSnackbar(commentFab, message);
    }

    @Override
    public void showSuccess() {
        showSnackbar(commentFab, "success");
    }

    @Override
    public Context context() {
        return getApplicationContext();
    }
    //</editor-fold>

    //<editor-fold desc="Comment Animation">
    private int commentFabCx, commentFabCy, commentFabRadius;
    private int commentSendFabCx, commentSendFabCy, commentSendFabRadius;
    private int[] commentSendFabLocations = new int[2];
    private int commentAreaRadius;
    private int commentMaskRadius;

    private void calculateMetrics() {
        commentFabCx = commentFab.getMeasuredWidth() / 2;
        commentFabCy = commentFab.getMeasuredHeight() / 2;
        commentFabRadius = Math.max(commentFab.getWidth(), commentFab.getHeight()) / 2;

        commentSendFabCx = commentSendFab.getMeasuredWidth() / 2;
        commentSendFabCy = commentSendFab.getMeasuredHeight() / 2;
        commentSendFabRadius = Math.max(commentSendFab.getWidth(), commentSendFab.getHeight()) / 2;

        commentSendFab.getLocationInWindow(commentSendFabLocations);

        commentAreaRadius = (int) Math.sqrt((double) (Math.pow(commentSendFabLocations[0] + commentSendFabCx, 2) + Math.pow(commentArea.getHeight(), 2)));
        commentMaskRadius = (int) Math.sqrt((double) (Math.pow(commentSendFabLocations[0] + commentSendFabCx, 2) + Math.pow(commentMask.getHeight(), 2)));
    }

    private void showCommentArea(boolean showKeyboard) {
        Animator hideCommentFabAnim = createHideCircularReveal(commentFab, commentFabCx, commentFabCy, commentFabRadius, 0);
        Animator showCommentSendFabAnim = createShowCircularReveal(commentSendFab, commentSendFabCx, commentSendFabCy, 0, commentSendFabRadius);
        Animator showCommentAreaAnim = createShowCircularReveal(commentArea, commentSendFabLocations[0] + commentSendFabCx, 0, 0, commentAreaRadius);
        Animator showCommentMaskAnim = createShowCircularReveal(commentMask, commentSendFabLocations[0] + commentSendFabCx, commentSendFabLocations[1] + commentSendFabCx, 0, commentMaskRadius);

        AnimatorSet showCommentArea = new AnimatorSet();
        showCommentArea.play(showCommentSendFabAnim).after(hideCommentFabAnim)
                .with(showCommentAreaAnim).with(showCommentMaskAnim);
        showCommentArea.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                autoCommentAreaFocus();
                if (showKeyboard) {
                    showKeyboard(ItemReaderActivity.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        showCommentArea.start();
    }

    private void hideCommentArea() {
        hideKeyboard(ItemReaderActivity.this);
        Animator showCommentFabAnim = createShowCircularReveal(commentFab, commentFabCx, commentFabCy, 0, commentFabRadius);
        Animator hideCommentSendFabAnim = createHideCircularReveal(commentSendFab, commentSendFabCx, commentSendFabCy, commentSendFabRadius, 0);
        Animator hideCommentAreaAnim = createHideCircularReveal(commentArea, commentSendFabLocations[0] + commentSendFabCx, 0, commentAreaRadius, 0);
        Animator hideCommentMaskAnim = createHideCircularReveal(commentMask, commentSendFabLocations[0] + commentSendFabCx, commentSendFabLocations[1] + commentSendFabCx, commentMaskRadius, 0);

        AnimatorSet hideCommentArea = new AnimatorSet();
        hideCommentArea.play(hideCommentSendFabAnim).before(showCommentFabAnim).with(hideCommentAreaAnim).with(hideCommentMaskAnim);
        hideCommentArea.start();
    }

    private Animator createHideCircularReveal(View view,
                                              int centerX, int centerY, float startRadius, float endRadius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animator;
    }

    private Animator createShowCircularReveal(View view,
                                              int centerX, int centerY, float startRadius, float endRadius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animator;
    }
    //</editor-fold>

    //<editor-fold desc="Comment Area Focus">
    private HashMap<String, CommentAction> commentActionHashMap = new HashMap<>();
    private boolean isSendingComment = false;

    private void clearOldCommentInfo() {
        commentAreaContent.setText("");
        commentActionHashMap.clear();
    }

    private void setUpTextInputLayoutError() {
        commentAreaName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCommentAreaName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        commentAreaEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCommentAreaEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        commentAreaContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCommentAreaContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkCommentAreaName(String s) {
        if (TextUtils.isEmpty(s)) {
            commentAreaNameLayout.setError("不要留空惹");
            commentAreaNameLayout.setErrorEnabled(true);
            return false;
        } else {
            commentAreaNameLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkCommentAreaEmail(String s) {
        if (TextUtils.isEmpty(s)
                || !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
            commentAreaEmailLayout.setError("格式不对惹");
            commentAreaEmailLayout.setErrorEnabled(true);
            return false;
        } else {
            commentAreaEmailLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkCommentAreaContent(String s) {
        if (TextUtils.isEmpty(s)) {
            commentAreaContentLayout.setError("不要留空惹");
            commentAreaContentLayout.setErrorEnabled(true);
            return false;
        } else {
            commentAreaContentLayout.setErrorEnabled(false);
            return true;
        }
    }

    private void checkCommentAreaContentWithAt() {
        commentAreaContentLayout.setError("不要只@不说话惹");
        commentAreaContentLayout.setErrorEnabled(true);
    }

    private boolean checkAllForm() {
        return checkCommentAreaName(commentAreaName.getText().toString()) &&
                checkCommentAreaEmail(commentAreaEmail.getText().toString()) &&
                checkCommentAreaContent(commentAreaContent.getText().toString());
    }

    private void registerCommentAction() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                clearOldCommentInfo();
                clearOldFavoInfo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        commentMask.setOnClickListener(v -> {
            if (commentArea.getVisibility() == View.VISIBLE) {
                hideCommentArea();
            }
        });
        commentFab.setOnClickListener(v -> {
            if (!isSendingComment && commentArea.getVisibility() == View.INVISIBLE) {
                showCommentArea(true);
            }
        });
        commentSendFab.setOnClickListener(v -> {
            if (commentArea.getVisibility() == View.VISIBLE && checkAllForm()) {
                String realComment = commentAreaContent.getText().toString();
                String duoshuoParentId = "";
                for (String key : commentActionHashMap.keySet()) {
                    CommentAction commentAction = commentActionHashMap.get(key);
                    if (commentAction.getType() == CommentAction.ActionType.Jandan) {
                        realComment = realComment.replace("@" + commentAction.getParentName(),
                                String.format("@<a href=\"%s\">%s</a>", commentAction.getParentId(), commentAction.getParentName()));
                    } else if (commentAction.getType() == CommentAction.ActionType.Duoshuo) {
                        if (realComment.contains("@" + commentAction.getParentName())) {
                            duoshuoParentId = commentAction.getParentId();
                            realComment = realComment.replace("@" + commentAction.getParentName() + ":", "");
                            realComment = realComment.replace("@" + commentAction.getParentName(), "");

                            if (HtmlHelper.isBlank(realComment)) {
                                checkCommentAreaContentWithAt();
                                return;
                            } else {
                                break;
                            }
                        }
                    }
                }
                logger.info("send:{}[{},{}] to:{}", realComment,
                        currentItemInfo.getAdapterItemId(currentPosition),
                        currentItemInfo.getAdapterItemType(currentPosition),
                        duoshuoParentId);
                switch (currentItemInfo.getAdapterItemType(currentPosition)) {
                    case SimplePost: {
                        ItemReaderActivity.this.doCommentPresenter.doPostComment(
                                Long.valueOf(currentItemInfo.getAdapterItemId(currentPosition)),
                                commentAreaName.getText().toString(),
                                commentAreaEmail.getText().toString(),
                                realComment);
                        break;
                    }
                    case SimplePic:
                    case SimpleJoke:
                    case SimpleVideo: {
                        //call other
                        ItemReaderActivity.this.doCommentPresenter.postDuoshuoComment(
                                Constants.THREAD_PREFIX + currentItemInfo.getAdapterItemId(currentPosition),
                                commentAreaName.getText().toString(),
                                commentAreaEmail.getText().toString(),
                                realComment,
                                duoshuoParentId);
                        break;
                    }
                }
                hideCommentArea();
            }
        });
        commentActionSubjectSubscription = commentActionSubject.subscribe(commentAction -> {
            logger.info("action:{},{} current:{}", commentAction.getParentId(), commentAction.getParentName(), commentAreaContent.getText().toString());
            if (!isSendingComment) {
                if (commentArea.getVisibility() == View.INVISIBLE) {
                    showCommentArea(true);
                }
                commentActionHashMap.put(commentAction.getParentName(), commentAction);
                commentAreaContent.setText(commentAreaContent.getText().toString() + "@" + commentAction.getParentName() + ":");
                commentAreaContent.setSelection(commentAreaContent.getText().length());
            }
        });
    }

    private void autoCommentAreaFocus() {
        if (Strings.isNullOrEmpty(commentAreaName.getText().toString())) {
            commentAreaName.requestFocus();
            return;
        }
        if (Strings.isNullOrEmpty(commentAreaEmail.getText().toString())) {
            commentAreaEmail.requestFocus();
            return;
        }
        commentAreaContent.requestFocus();
    }
    //</editor-fold>

    //<editor-fold desc="Keyboard Utils">

    /**
     * hide keyboard
     *
     * @return true for success
     */
    public static boolean hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        } else {
            return false;
        }
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    //</editor-fold>
}
