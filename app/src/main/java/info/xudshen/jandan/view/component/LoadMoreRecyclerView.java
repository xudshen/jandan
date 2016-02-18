package info.xudshen.jandan.view.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import rx.functions.Action0;

/**
 * Created by xudshen on 16/2/18.
 */
public class LoadMoreRecyclerView extends RecyclerView {
    private boolean loading = false;
    private int visibleThreshold = 0;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * TODO: WARN: may cause memory leak
     */
    public void setOnLoadMoreListener(Action0 action) {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = getLayoutManager().getChildCount();
                    totalItemCount = getLayoutManager().getItemCount();
                    firstVisibleItem = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached
                        loading = true;
                        action.call();
                    }
                }

            }
        });
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
