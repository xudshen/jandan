package info.xudshen.jandan.model.generator;

import info.xudshen.droiddata.daogenerator.Entity;
import info.xudshen.droiddata.daogenerator.ModelGenerator;
import info.xudshen.droiddata.daogenerator.Schema;

/**
 * Created by xudshen on 15/10/8.
 */
public class AppModelGenerator extends ModelGenerator {
    public AppModelGenerator() {
        super(1, "info.xudshen.jandan.model", "app/src/main/java");
    }

    @Override
    protected void addEntities(Schema schema) {
        addArticle(schema);
        addJoke(schema);
    }

    void addArticle(Schema schema) {
        Entity entity = schema.addEntity("Article");

        addLongProperty(entity, "articleId").primaryKey();
        addStringProperty(entity, "title");
        addStringProperty(entity, "author");
        addTimestampProperty(entity, "time");
        addStringProperty(entity, "content");

        entity.addContentProvider();

        entity.addImport(GSON_EXPOSE);
    }

    void addJoke(Schema schema) {
        Entity entity = schema.addEntity("Joke");

        addLongProperty(entity, "jokeId").primaryKey();
        addStringProperty(entity, "author");
        addStringProperty(entity, "content");

        entity.addImport(GSON_EXPOSE);
    }

    public static void main(String[] args) throws Exception {
        AppModelGenerator generator = new AppModelGenerator();
        generator.generateAll();
    }
}
