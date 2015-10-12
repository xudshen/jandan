package info.xudshen.jandan.model.generator;

import info.xudshen.droiddata.dao.generator.Entity;
import info.xudshen.droiddata.dao.ModelGenerator;
import info.xudshen.droiddata.dao.generator.Property;
import info.xudshen.droiddata.dao.generator.Schema;

/**
 * Created by xudshen on 15/10/8.
 */
public class AppModelGenerator extends ModelGenerator {
    public AppModelGenerator() {
        super(1, "info.xudshen.jandan.model", "app/src/main/java");
    }

    @Override
    protected void addEntities(Schema schema) {
        schema.setDefaultBRPath("info.xudshen.jandan.BR");

        addArticle(schema);
        addJoke(schema);
    }

    void addArticle(Schema schema) {
        Entity entity = schema.addEntity("Article");

        entity.setObservable(true);

        entity.addIdProperty();
        Property articleId = addLongProperty(entity, "articleId").getProperty();
        addStringProperty(entity, "title").bindable(true);
        addStringProperty(entity, "author");
        addTimestampProperty(entity, "time");
        addStringProperty(entity, "content");

        addUniqueIndex(entity, articleId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
    }

    void addJoke(Schema schema) {
        Entity entity = schema.addEntity("Joke");

        entity.setObservable(true);

        entity.addIdProperty();
        Property jokeId = addLongProperty(entity, "jokeId").getProperty();
        addStringProperty(entity, "author");
        addStringProperty(entity, "content");

        addUniqueIndex(entity, jokeId);

        entity.addContentProvider();
        entity.addImport(GSON_EXPOSE);
    }

    public static void main(String[] args) throws Exception {
        AppModelGenerator generator = new AppModelGenerator();
        generator.generateAll();
    }
}
