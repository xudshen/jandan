package info.xudshen.jandan.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.xudshen.jandan.R;
import uk.co.senab.photoview.PhotoView;

public class HDImageViewerFragment extends BaseFragment {
    private static final Logger logger = LoggerFactory.getLogger(HDImageViewerFragment.class);
    private static final String ARG_URL = "ARG_URL";

    public static HDImageViewerFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);

        HDImageViewerFragment fragment = new HDImageViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HDImageViewerFragment() {
    }

    private String url;

    @Override
    protected void inject() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inject();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hdimage_viewer, container, false);

        PhotoView photoView = (PhotoView) view.findViewById(R.id.image_hd_view);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageOnFail(R.drawable.placeholder_failed)
                .showImageOnLoading(R.drawable.placeholder_loading)
                .imageScaleType(ImageScaleType.NONE)
                .displayer(new FadeInBitmapDisplayer(300))
                .resetViewBeforeLoading(true)
                .build();

        ImageLoader.getInstance().displayImage(url, photoView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                float scale = 1f;
                if (loadedImage.getHeight() > view.getHeight()) {
                    scale = view.getWidth() / (loadedImage.getWidth() * view.getHeight() * 1.0f / loadedImage.getHeight());
                } else {
                    scale = view.getWidth() / loadedImage.getWidth() / 2;
                }
                ((PhotoView) view).setScaleLevels(scale / 2, scale, scale * 2);
                ((PhotoView) view).setScale(scale, view.getWidth() / 2, 0, false);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return view;
    }
}
