package info.xudshen.jandan.view.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.R;
import info.xudshen.jandan.utils.glide.ProgressTarget;

/**
 * Created by xudshen on 16/2/22.
 */
public class ProgressImageView extends RelativeLayout {
    private static final Logger logger = LoggerFactory.getLogger(ProgressImageView.class);
    private boolean showProgressText = true, showProgressBar = true;
    private TextView progressTextView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ProgressTarget<String, GlideDrawable> target;

    public ProgressImageView(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.progress_image_view, this, true);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ProgressImageView,
                    0, 0);

            try {
                showProgressText = a.getBoolean(R.styleable.ProgressImageView_showProgressText, true);
                showProgressBar = a.getBoolean(R.styleable.ProgressImageView_showProgressBar, true);
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imageView = (ImageView) getChildAt(0);

        progressBar = (ProgressBar) getChildAt(1);
        if (!showProgressBar) progressBar.setVisibility(GONE);
        progressTextView = (TextView) getChildAt(2);
        if (!showProgressText) progressTextView.setVisibility(GONE);

        target = new MyProgressTarget<>(new GlideDrawableImageViewTarget(imageView), progressBar, imageView, progressTextView);
    }

    public void load(String url, Drawable placeHolder, Fragment fragment) {
        if (url.endsWith("gif")) {
            target.setModel(url); // update target's cache
            DrawableTypeRequest<String> request = Glide.with(fragment).load(url);
            if (placeHolder != null) {
                request.placeholder(placeHolder);
            }

            request.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .sizeMultiplier(0.6f)
                    .fitCenter() // needs explicit transformation, because we're using a custom target
                    .crossFade()
                    .into(target);
        }
    }

    /**
     * Demonstrates 3 different ways of showing the progress:
     * <ul>
     * <li>Update a full fledged progress bar</li>
     * <li>Update a text view to display size/percentage</li>
     * <li>Update the placeholder via Drawable.level</li>
     * </ul>
     * This last one is tricky: the placeholder that Glide sets can be used as a progress drawable
     * without any extra Views in the view hierarchy if it supports levels via <code>usesLevel="true"</code>
     * or <code>level-list</code>.
     *
     * @param <Z> automatically match any real Glide target so it can be used flexibly without reimplementing.
     */
    private static class MyProgressTarget<Z> extends ProgressTarget<String, Z> {
        private final TextView text;
        private final ProgressBar progress;
        private final ImageView image;

        public MyProgressTarget(Target<Z> target, ProgressBar progress, ImageView image, TextView text) {
            super(target);
            this.progress = progress;
            this.image = image;
            this.text = text;
        }

        @Override
        public float getGranualityPercentage() {
            return 0.1f; // this matches the format string for #text below
        }

        @Override
        protected void onConnecting() {
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
//            image.setImageLevel(0);
            text.setVisibility(View.VISIBLE);
            text.setText("connecting");
        }

        @Override
        protected void onDownloading(long bytesRead, long expectedLength) {
            progress.setIndeterminate(false);
            progress.setProgress((int) (100 * bytesRead / expectedLength));
//            image.setImageLevel((int) (10000 * bytesRead / expectedLength));
            text.setText(String.format("downloading %.2f/%.2f MB %.1f%%",
                    bytesRead / 1e6, expectedLength / 1e6, 100f * bytesRead / expectedLength));
        }

        @Override
        protected void onDownloaded() {
            progress.setIndeterminate(true);
//            image.setImageLevel(10000);
            text.setText("decoding and transforming");
        }

        @Override
        protected void onDelivered() {
            progress.setVisibility(View.INVISIBLE);
//            image.setImageLevel(0); // reset ImageView default
            text.setVisibility(View.INVISIBLE);
        }
    }
}
