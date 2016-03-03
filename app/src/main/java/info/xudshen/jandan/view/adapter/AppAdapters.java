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

    @BindingAdapter(value = {"thumb", "placeHolder"})
    public static void setImageUrl(ImageView view, String url,
                                   Drawable placeHolder) {
        if (Strings.isNullOrEmpty(url)) url = "http://localhost";
        RequestCreator requestCreator =
                Picasso.with(view.getContext()).load(url);
        if (placeHolder != null) {
            requestCreator.placeholder(placeHolder);
        }
        requestCreator.resize(96, 96)
                .centerCrop()
                .into(view);
    }

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

    @BindingAdapter(value = {"picThumb", "placeHolder"})
    public static void setThumbImageUrl(ImageView view, String url,
                                        Drawable placeHolder) {
//        if (Strings.isNullOrEmpty(url)) url = "http://localhost";
//        DrawableTypeRequest<String> request = Glide.with(view.getContext())
//                .load(url);
//        request.asBitmap();
//        if (placeHolder != null) {
//            request.placeholder(placeHolder);
//        }
//        request
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transform(new BitmapTransformation(view.getContext()) {
//                    @Override
//                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
//                                               int outWidth, int outHeight) {
//                        int width = outWidth;
//                        int height = Math.min(toTransform.getHeight() * outWidth / toTransform.getWidth(),
//                                LayoutHelper.toPx(view.getContext(), 480));
//
//                        Bitmap result = pool.get(width, height, Bitmap.Config.RGB_565);
//                        if (result == null) {
//                            result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//                        }
//                        TransformationUtils.setAlpha(toTransform, result);
//
//                        Canvas canvas = new Canvas(result);
//
//                        Paint paint = new Paint(Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
//                        canvas.drawBitmap(toTransform, new Rect(0, 0, toTransform.getWidth(), height * toTransform.getWidth() / width),
//                                new Rect(0, 0, width, height), paint);
//
//                        return result;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return "info.xudshen.jandan.transform.ThumbTransform";
//                    }
//                })
//                .crossFade()
//                .into(view);
    }

    @BindingAdapter(value = {"picFull", "placeHolder"})
    public static void setFullImageUrl(ProgressImageView view, String url,
                                       Drawable placeHolder) {
//        view.load(HtmlUtils.optimizedUrl(url, IMAGE_QUALITY, ImageQuality.MEDIUM), placeHolder, );
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
