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
        addPost(schema);
        addAuthor(schema);
    }

    void addPost(Schema schema) {
        Entity entity = schema.addEntity("Post");

        entity.addIdProperty();
        Property postId = addLongProperty(entity, "postId").bindable(true).getProperty();
        addStringProperty(entity, "url").bindable(true);
        addStringProperty(entity, "title").bindable(true);
        addTimestampProperty(entity, "date");

        addStringProperty(entity, "comment_count");

        addUniqueIndex(entity, postId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
    }

    void addAuthor(Schema schema) {
        Entity entity = schema.addEntity("Author");

        entity.addIdProperty();
        Property authorId = addLongProperty(entity, "authorId").bindable(true).getProperty();
        addStringProperty(entity, "slug").bindable(true);
        addStringProperty(entity, "name").bindable(true);
        addStringProperty(entity, "first_name").bindable(true);
        addStringProperty(entity, "last_name").bindable(true);
        addStringProperty(entity, "nick_name").bindable(true);
        addStringProperty(entity, "url").bindable(true);
        addStringProperty(entity, "description").bindable(true);

        addUniqueIndex(entity, authorId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
    }

    public static void main(String[] args) throws Exception {
        AppModelGenerator generator = new AppModelGenerator();
        generator.generateAll();
    }
}
