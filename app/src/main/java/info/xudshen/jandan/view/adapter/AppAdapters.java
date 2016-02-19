package info.xudshen.jandan.view.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import info.xudshen.jandan.utils.HtmlHelper;

/**
 * Created by xudshen on 16/2/18.
 */
public class AppAdapters {
    @BindingAdapter(value = {"android:src", "placeHolder"})
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

    @BindingAdapter(value = {"webContent"})
    public static void setWebviewContent(WebView webView, String content) {
        content = HtmlHelper.formBody(content);
        webView.loadDataWithBaseURL(null, content, "text/html; charset=UTF-8", null, null);
        webView.setOnLongClickListener(v -> true);
    }
}
