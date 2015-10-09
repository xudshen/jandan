package info.xudshen.jandan.adapter;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import info.xudshen.droiddata.adapter.DDCursorLoaderRecyclerAdapter;
import info.xudshen.jandan.BR;
import info.xudshen.jandan.JandanApp;
import info.xudshen.jandan.R;
import info.xudshen.jandan.model.Article;

/**
 * Created by xudshen on 15/10/9.
 */
public class ArticleRecyclerViewAdapter extends DDCursorLoaderRecyclerAdapter<ArticleRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater inflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ViewDataBinding binding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.binding = viewDataBinding;
        }
    }

    public ArticleRecyclerViewAdapter(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.my_text_view, parent, false);
        ViewHolder vh = new ViewHolder(viewDataBinding);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Article article = JandanApp.daoSession.getArticleDao().readEntity(cursor, 0);
        viewHolder.binding.setVariable(BR.item, article);
    }
}
