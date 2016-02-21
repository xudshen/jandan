package info.xudshen.jandan.view.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.utils.HtmlHelper;
import info.xudshen.jandan.utils.LayoutHelper;

/**
 * Created by xudshen on 16/2/18.
 */
public class AppAdapters {
    private static final Logger logger = LoggerFactory.getLogger(AppAdapters.class);

    @BindingAdapter(value = {"thumb", "placeHolder"})
    public static void setImageUrl(ImageView view, String url,
                                   Drawable placeHolder) {

        RequestCreator requestCreator =
                Picasso.with(view.getContext()).load(url);
        if (placeHolder != null) {
            requestCreator.placeholder(placeHolder);
        }
        requestCreator.resize(96, 96)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter(value = {"picThumb", "placeHolder"})
    public static void setFullImageUrl(ImageView view, String url,
                                       Drawable placeHolder) {
        DrawableTypeRequest<String> request = Glide.with(view.getContext())
                .load(url);
        request.asBitmap();
        if (placeHolder != null) {
            request.placeholder(placeHolder);
        }
        String id = url.substring(url.length() - 8, url.length());
        request
                .transform(new BitmapTransformation(view.getContext()) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                                               int outWidth, int outHeight) {
                        int width = outWidth;
                        int height = Math.min(toTransform.getHeight() * outWidth / toTransform.getWidth(),
                                LayoutHelper.toPx(view.getContext(), 480));

                        logger.info("[{}]out={},{}, bitmap={},{}, resize={},{}",
                                id,
                                outWidth, outHeight,
                                toTransform.getWidth(), toTransform.getHeight(),
                                width, height);

                        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
                        if (result == null) {
                            // Use ARGB_8888 since we're going to add alpha to the image.
                            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        }
                        TransformationUtils.setAlpha(toTransform, result);

                        Canvas canvas = new Canvas(result);

                        Paint paint = new Paint(Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
                        canvas.drawBitmap(toTransform, new Rect(0, 0, toTransform.getWidth(), height * toTransform.getWidth() / width),
                                new Rect(0, 0, width, height), paint);

                        return result;
                    }

                    @Override
                    public String getId() {
                        return "info.xudshen.jandan.transform.TestTransform";
                    }
                })
                .crossFade()
                .into(view);
    }

    @BindingAdapter(value = {"webContent"})
    public static void setWebviewContent(WebView webView, String content) {
        content = HtmlHelper.formBody(content);
        webView.loadDataWithBaseURL(null, content, "text/html; charset=UTF-8", null, null);
        webView.setOnLongClickListener(v -> true);
    }


    @BindingAdapter(value = {"richText"})
    public static void setTextviewRichText(TextView textView, String content) {
        textView.setText(Html.fromHtml(content));
    }

    @BindingAdapter(value = {"paddingBottom"})
    public static void setPaddingBottom(View view, float height) {
        view.setPadding(view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                (int) height);
    }
}
