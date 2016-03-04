package info.xudshen.jandan.view.activity;

import android.os.Bundle;

import info.xudshen.jandan.R;
import info.xudshen.jandan.view.fragment.HDImageViewerFragment;

public class HDImageViewerActivity extends BaseActivity {
    public static final String ARG_URL = "ARG_URL";
    private String url;

    @Override
    protected void initializeInjector() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeInjector();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdimage_viewer);

        url = getIntent().getExtras().getString(ARG_URL);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_content, HDImageViewerFragment.newInstance(url)).commit();
    }
}
