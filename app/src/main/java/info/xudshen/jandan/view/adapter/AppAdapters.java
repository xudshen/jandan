package info.xudshen.jandan.view.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hdodenhof.circleimageview.CircleImageView;
import info.xudshen.jandan.R;
import info.xudshen.jandan.data.utils.HtmlUtils;
import info.xudshen.jandan.domain.enums.ImageQuality;
import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.utils.LayoutHelper;
import info.xudshen.jandan.view.component.ProgressImageView;

/**
 * Created by xudshen on 16/2/18.
 */
public class AppAdapters {
    private static final Logger logger = LoggerFactory.getLogger(AppAdapters.class);
    public static ImageQuality IMAGE_QUALITY = ImageQuality.MEDIUM;

    @BindingAdapter(value = {"avatar", "placeHolder"})
    public static void setAvatarUrl(CircleImageView view, String url,
                                    Drawable placeHolder) {
        if (Strings.isNullOrEmpty(url)) url = "http://localhost";
        RequestCreator requestCreator =
                Picasso.with(view.getContext()).load(url);
        if (placeHolder != null) {
            requestCreator.placeholder(placeHolder);
        }
        requestCreator.resize(48, 48)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter(value = {"webContent"})
    public static void setWebviewContent(WebView webView, String content) {
        if (content != null) {
            content = HtmlHelper.formBody(content);
            webView.loadDataWithBaseURL(null, content, "text/html; charset=UTF-8", null, null);
            webView.setOnLongClickListener(v -> true);
        }
    }

    @BindingAdapter(value = {"richText"})
    public static void setTextviewRichText(TextView textView, String content) {
        if (content != null) {
            textView.setText(Html.fromHtml(content));
        }
    }

    @BindingAdapter(value = {"paddingBottom"})
    public static void setPaddingBottom(View view, float height) {
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                (int) height);
    }
}
