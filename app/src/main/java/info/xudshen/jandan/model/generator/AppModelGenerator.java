package info.xudshen.jandan.model.generator;

import info.xudshen.droiddata.dao.ModelGenerator;
import info.xudshen.droiddata.dao.generator.Entity;
import info.xudshen.droiddata.dao.generator.Property;
import info.xudshen.droiddata.dao.generator.Schema;

/**
 * Created by xudshen on 15/10/8.
 */
public class AppModelGenerator extends ModelGenerator {
    public AppModelGenerator() {
        super(1, "info.xudshen.jandan.domain.model");
        setEntityOutDir("domain/src/main/java");
        setDaoOutDir("data/src/main/java");
        setBRPath("info.xudshen.data.BR");

        setDaoJavaPackage("info.xudshen.jandan.data.dao");
        setObservableJavaPackage("info.xudshen.jandan.data.model.observable");
    }

    @Override
    protected void addEntities(Schema schema) {
        addMeta(schema);
        addPost(schema);
        addSimplePost(schema);
        addAuthor(schema);
        addCategory(schema);
        addComment(schema);

        addReadLaterItem(schema);
        addPic(schema);
        addPicComment(schema);
    }

    void addMeta(Schema schema) {
        Entity entity = schema.addEntity("Meta");

        entity.addIdProperty();
        Property key = addStringProperty(entity, "key").bindable(true).getProperty();
        addStringProperty(entity, "value").bindable(true);
        addLongProperty(entity, "longValue").bindable(true);
        addTimestampProperty(entity, "timeValue").bindable(true);

        addUniqueIndex(entity, key);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
    }

    void addPost(Schema schema) {
        Entity entity = schema.addEntity("Post");

        entity.addIdProperty();
        Property postId = addLongProperty(entity, "postId")
                .codeBeforeField("@Expose\n    @SerializedName(\"id\")")
                .bindable(true).getProperty();
        addStringProperty(entity, "url").bindable(true);
        addStringProperty(entity, "title").bindable(true);
        addStringProperty(entity, "content").bindable(true);
        addStringProperty(entity, "excerpt").bindable(true);
        addTimestampProperty(entity, "date");
        addTimestampProperty(entity, "modified");

        addLongProperty(entity, "commentCount");

        addLongProperty(entity, "authorId");
        addStringProperty(entity, "authorName");
        addLongProperty(entity, "categoryId");
        addStringProperty(entity, "categoryDescription");

        addUniqueIndex(entity, postId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addSimplePost(Schema schema) {
        Entity entity = schema.addEntity("SimplePost");

        entity.addIdProperty();
        Property postId = addLongProperty(entity, "postId")
                .codeBeforeField("@Expose\n    @SerializedName(\"id\")")
                .bindable(true).getProperty();
        addStringProperty(entity, "url").bindable(true);
        addStringProperty(entity, "title").bindable(true);
        addStringProperty(entity, "excerpt").bindable(true);
        addStringProperty(entity, "thumbC").bindable(true);
        addTimestampProperty(entity, "date");
        addTimestampProperty(entity, "modified");

        addLongProperty(entity, "commentCount");

        addStringProperty(entity, "authorName");
        addStringProperty(entity, "categoryDescription");

        addStringProperty(entity, "expireTag");

        addUniqueIndex(entity, postId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addAuthor(Schema schema) {
        Entity entity = schema.addEntity("Author");

        entity.addIdProperty();
        Property authorId = addLongProperty(entity, "authorId")
                .codeBeforeField("@Expose\n    @SerializedName(\"id\")")
                .bindable(true).getProperty();
        addStringProperty(entity, "slug").bindable(true);
        addStringProperty(entity, "name").bindable(true);
        addStringProperty(entity, "firstName").bindable(true);
        addStringProperty(entity, "lastName").bindable(true);
        addStringProperty(entity, "nickName").bindable(true);
        addStringProperty(entity, "url").bindable(true);
        addStringProperty(entity, "description").bindable(true);

        addUniqueIndex(entity, authorId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addCategory(Schema schema) {
        Entity entity = schema.addEntity("Category");

        entity.addIdProperty();
        Property categoryId = addLongProperty(entity, "categoryId")
                .codeBeforeField("@Expose\n    @SerializedName(\"id\")")
                .bindable(true).getProperty();
        addStringProperty(entity, "slug").bindable(true);
        addStringProperty(entity, "title").bindable(true);
        addStringProperty(entity, "description").bindable(true);
        addStringProperty(entity, "postCount").bindable(true);

        addUniqueIndex(entity, categoryId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addComment(Schema schema) {
        Entity entity = schema.addEntity("Comment");

        entity.addIdProperty();
        Property commentId = addLongProperty(entity, "commentId")
                .codeBeforeField("@Expose\n    @SerializedName(\"id\")")
                .bindable(true).getProperty();
        Property postId = addLongProperty(entity, "postId").bindable(true).getProperty();
        addStringProperty(entity, "name").bindable(true);
        addStringProperty(entity, "url").bindable(true);
        addTimestampProperty(entity, "date");

        addStringProperty(entity, "content").bindable(true);

        addLongProperty(entity, "parent").bindable(true);
        addLongProperty(entity, "votePositive").bindable(true);
        addLongProperty(entity, "voteNegative").bindable(true);
        addLongProperty(entity, "index").bindable(true);

        addLongProperty(entity, "commentTo").bindable(true);

        addUniqueIndex(entity, commentId, postId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addPic(Schema schema) {
        Entity entity = schema.addEntity("PicItem");

        entity.addIdProperty();
        addLongProperty(entity, "picId").codeBeforeField("@Expose\n    @SerializedName(\"comment_ID\")").bindable(true);
        addStringProperty(entity, "picAuthor").codeBeforeField("@Expose\n    @SerializedName(\"comment_author\")").bindable(true);
        addStringProperty(entity, "picAuthorEmail").codeBeforeField("@Expose\n    @SerializedName(\"comment_author_email\")").bindable(true);
        addStringProperty(entity, "picAuthorUrl").codeBeforeField("@Expose\n    @SerializedName(\"comment_author_url\")").bindable(true);
        addTimestampProperty(entity, "date").codeBeforeField("@Expose\n    @SerializedName(\"comment_date\")").bindable(true);

        addLongProperty(entity, "votePositive").bindable(true);
        addLongProperty(entity, "voteNegative").bindable(true);

        addLongProperty(entity, "commentCount").bindable(true);
        addStringProperty(entity, "picThreadId").bindable(true);

        addStringProperty(entity, "picContent").codeBeforeField("@Expose\n    @SerializedName(\"comment_content\")").bindable(true);
        addStringProperty(entity, "picTextContent").codeBeforeField("@Expose\n    @SerializedName(\"text_content\")").bindable(true);
        addStringProperty(entity, "pics").codeBeforeField("").bindable(true);
        addStringProperty(entity, "picFirst").bindable(true);
        addLongProperty(entity, "picCount").bindable(true);
        addBooleanProperty(entity, "hasGif").bindable(true);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addPicComment(Schema schema) {
        Entity entity = schema.addEntity("PicComment");

        entity.addIdProperty();
        addLongProperty(entity, "picCommentId").codeBeforeField("@Expose\n    @SerializedName(\"post_id\")").bindable(true);
        addLongProperty(entity, "picId").bindable(true);
        addLongProperty(entity, "picThreadId").codeBeforeField("@Expose\n    @SerializedName(\"thread_id\")").bindable(true);

        addStringProperty(entity, "message").bindable(true);
        addTimestampProperty(entity, "date").codeBeforeField("@Expose\n    @SerializedName(\"created_at\")").bindable(true);
        addStringProperty(entity, "parentCommentId").codeBeforeField("@Expose\n    @SerializedName(\"parent_id\")").bindable(true);

        addStringProperty(entity, "authorId").bindable(true);
        addStringProperty(entity, "authorName").bindable(true);
        addStringProperty(entity, "authorAvatar").bindable(true);
        addStringProperty(entity, "authorUrl").bindable(true);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
        entity.addImport(GSON_SERIALIZEDNAME);
    }

    void addReadLaterItem(Schema schema) {
        Entity entity = schema.addEntity("ReadLaterItem");

        entity.addIdProperty();
        Property readLaterItemId = addLongProperty(entity, "readLaterItemId").bindable(true).getProperty();
        addEnumProperty(entity, "type", "info.xudshen.jandan.domain.enums.ReaderItemType");
        addLongProperty(entity, "actualId").bindable(true);

        addTimestampProperty(entity, "addDate").bindable(true);

        addUniqueIndex(entity, readLaterItemId);

        entity.addImport(GSON_EXPOSE);
    }

    public static void main(String[] args) throws Exception {
        AppModelGenerator generator = new AppModelGenerator();
        generator.generateAll();
    }
}
