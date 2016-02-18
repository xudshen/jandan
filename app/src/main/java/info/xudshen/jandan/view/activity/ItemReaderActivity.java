package info.xudshen.jandan.view.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.common.base.Strings;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.internal.di.components.ActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerPostComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.view.adapter.ItemReaderPagerAdapter;
import info.xudshen.jandan.view.transition.StackPageTransformer;

public class ItemReaderActivity extends BaseActivity implements HasComponents {
    private static final Logger logger = LoggerFactory.getLogger(ItemReaderActivity.class);

    @Bind(R.id.item_reader_view_pager)
    ViewPager viewPager;
    @Bind(R.id.comment_area)
    View commentArea;
    @Bind(R.id.comment_area_name)
    EditText commentAreaName;
    @Bind(R.id.comment_area_email)
    EditText commentAreaEmail;
    @Bind(R.id.comment_area_content)
    EditText commentAreaContent;
    @Bind(R.id.comment_fab)
    FloatingActionButton commentFab;
    @Bind(R.id.comment_send_fab)
    FloatingActionButton commentSendFab;

    private PostComponent postComponent;
    private ActivityComponent activityComponent;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reader);
        initializeInjector();

        //do other
        ButterKnife.bind(this);

        viewPager.setAdapter(new ItemReaderPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new StackPageTransformer());

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

        registerCommentFocus();

        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                calculateMetrics();
                commentFab.setOnClickListener(v -> {
                    if (commentArea.getVisibility() == View.INVISIBLE) {
                        showCommentArea(true);
                    }
                });
                commentSendFab.setOnClickListener(v -> {
                    if (commentArea.getVisibility() == View.VISIBLE) {
                        hideCommentArea();
                    }
                });
                onCommentAreaFocusChangeListener = (v, isFocus) -> {
                    if (!isFocus && commentArea.getVisibility() == View.VISIBLE) {
                        hideCommentArea();
                    }
                };
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
        throw new IllegalStateException("componentType=" + componentType.getSimpleName() + " not found");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_reader_menu, menu);
        menu.findItem(R.id.item_reader_menu_favo).setIcon(new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_heart)
                .color(getResources().getColor(R.color.md_white_1000))
                .sizeDp(24).paddingDp(2));
        menu.findItem(R.id.item_reader_menu_later).setIcon(new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_get_pocket)
                .color(getResources().getColor(R.color.md_white_1000))
                .sizeDp(24).paddingDp(2));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (commentArea.getVisibility() == View.VISIBLE) {
            hideCommentArea();
        } else {
            super.onBackPressed();
        }
    }

    //<editor-fold desc="Comment Animation">
    private int commentFabCx, commentFabCy, commentFabRadius;
    private int commentSendFabCx, commentSendFabCy, commentSendFabRadius;
    private int[] commentSendFabLocations = new int[2];
    private int commentAreaRadius;

    private void calculateMetrics() {
        commentFabCx = commentFab.getMeasuredWidth() / 2;
        commentFabCy = commentFab.getMeasuredHeight() / 2;
        commentFabRadius = Math.max(commentFab.getWidth(), commentFab.getHeight()) / 2;

        commentSendFabCx = commentSendFab.getMeasuredWidth() / 2;
        commentSendFabCy = commentSendFab.getMeasuredHeight() / 2;
        commentSendFabRadius = Math.max(commentSendFab.getWidth(), commentSendFab.getHeight()) / 2;

        commentSendFab.getLocationInWindow(commentSendFabLocations);

        commentAreaRadius = (int) Math.sqrt((double) (Math.pow(commentSendFabLocations[0] + commentSendFabCx, 2) + Math.pow(commentArea.getHeight(), 2)));
    }

    private void showCommentArea(boolean showKeyboard) {
        Animator hideCommentFabAnim = createHideCircularReveal(commentFab, commentFabCx, commentFabCy, commentFabRadius, 0);
        Animator showCommentSendFabAnim = createShowCircularReveal(commentSendFab, commentSendFabCx, commentSendFabCy, 0, commentSendFabRadius);
        Animator showCommentAreaAnim = createShowCircularReveal(commentArea, commentSendFabLocations[0] + commentSendFabCx, 0, 0, commentAreaRadius);

        AnimatorSet showCommentArea = new AnimatorSet();
        showCommentArea.play(showCommentSendFabAnim).after(hideCommentFabAnim).with(showCommentAreaAnim);
        showCommentArea.start();
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
    }

    private void hideCommentArea() {
        hideKeyboard(ItemReaderActivity.this);
        Animator showCommentFabAnim = createShowCircularReveal(commentFab, commentFabCx, commentFabCy, 0, commentFabRadius);
        Animator hideCommentSendFabAnim = createHideCircularReveal(commentSendFab, commentSendFabCx, commentSendFabCy, commentSendFabRadius, 0);
        Animator hideCommentAreaAnim = createHideCircularReveal(commentArea, commentSendFabLocations[0] + commentSendFabCx, 0, commentAreaRadius, 0);

        AnimatorSet hideCommentArea = new AnimatorSet();
        hideCommentArea.play(hideCommentSendFabAnim).before(showCommentFabAnim).with(hideCommentAreaAnim);
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
    private boolean isCommentAreaNameFocus, isCommentAreaEmailFocus, isCommentAreaContentFocus;
    private View.OnFocusChangeListener onCommentAreaFocusChangeListener;

    private void registerCommentFocus() {
        commentAreaName.setOnFocusChangeListener((v, hasFocus) -> {
            isCommentAreaNameFocus = hasFocus;
        });
        commentAreaEmail.setOnFocusChangeListener((v, hasFocus) -> {
            isCommentAreaEmailFocus = hasFocus;
        });
        commentAreaContent.setOnFocusChangeListener((v, hasFocus) -> {
            isCommentAreaContentFocus = hasFocus;
        });
        commentArea.getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            if (newFocus.getId() != R.id.comment_area
                    && newFocus.getId() != R.id.comment_area_name
                    && newFocus.getId() != R.id.comment_area_email
                    && newFocus.getId() != R.id.comment_area_content) {
                judgeCommentAreaFocus();
            }
        });
    }

    private void judgeCommentAreaFocus() {
        if (!isCommentAreaNameFocus && !isCommentAreaEmailFocus && !isCommentAreaContentFocus) {
            if (onCommentAreaFocusChangeListener != null) {
                onCommentAreaFocusChangeListener.onFocusChange(null, false);
            }
        }
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
}
