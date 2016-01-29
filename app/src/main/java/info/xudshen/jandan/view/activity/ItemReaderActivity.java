package info.xudshen.jandan.view.activity;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.mikepenz.iconics.context.IconicsLayoutInflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.view.adapter.ItemReadPagerAdapter;

public class ItemReaderActivity extends BaseActivity {
    @Bind(R.id.item_reader_view_pager)
    ViewPager viewPager;

    @Override
    protected void initializeInjector() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reader);
        initializeInjector();

        //do other
        ButterKnife.bind(this);

        viewPager.setAdapter(new ItemReadPagerAdapter(getSupportFragmentManager()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
