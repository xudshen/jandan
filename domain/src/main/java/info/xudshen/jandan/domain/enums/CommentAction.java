package info.xudshen.jandan.domain.enums;

import info.xudshen.jandan.domain.model.Comment;

/**
 * Created by xudshen on 16/2/24.
 */
public class CommentAction {
    private String parentName;
    private String parentId;
    private ActionType type;

    public CommentAction() {
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public enum ActionType {
        Jandan,
        Duoshuo
    }

    public static class Builder {
        private CommentAction obj;

        public Builder() {
            this.obj = new CommentAction();
        }

        public Builder parentName(String name) {
            this.obj.parentName = name;
            return this;
        }

        public Builder parentId(String name) {
            this.obj.parentId = name;
            return this;
        }

        public CommentAction jandan() {
            this.obj.type = ActionType.Jandan;
            return this.obj;
        }

        public CommentAction duoshuo() {
            this.obj.type = ActionType.Duoshuo;
            return this.obj;
        }
    }
}
