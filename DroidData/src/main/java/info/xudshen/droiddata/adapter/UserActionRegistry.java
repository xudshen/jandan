package info.xudshen.droiddata.adapter;

import android.view.View;

import info.xudshen.droiddata.adapter.enums.UserActionType;

/**
 * Created by xudshen on 16/2/13.
 */
public class UserActionRegistry {
    private int viewId;
    private UserActionType type;
    private OnClickListener onClickListener;

    public UserActionRegistry(int viewId, OnClickListener onClickListener) {
        this.viewId = viewId;
        this.type = UserActionType.CLICK;
        this.onClickListener = onClickListener;
    }

    public int getViewId() {
        return viewId;
    }

    public UserActionType getType() {
        return type;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }
}
