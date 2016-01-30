package info.xudshen.jandan.view.activity;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.mikepenz.iconics.context.IconicsLayoutInflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.xudshen.jandan.R;
import info.xudshen.jandan.internal.di.HasComponents;
import info.xudshen.jandan.internal.di.components.ActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerActivityComponent;
import info.xudshen.jandan.internal.di.components.DaggerPostComponent;
import info.xudshen.jandan.internal.di.components.PostComponent;
import info.xudshen.jandan.internal.di.modules.ActivityModule;
import info.xudshen.jandan.view.adapter.ItemReadPagerAdapter;

public class ItemReaderActivity extends BaseActivity implements HasComponents {
    @Bind(R.id.item_reader_view_pager)
    ViewPager viewPager;

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

        viewPager.setAdapter(new ItemReadPagerAdapter(getSupportFragmentManager()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
}
