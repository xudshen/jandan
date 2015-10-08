package info.xudshen.droiddata.daogenerator;

import info.xudshen.droiddata.daogenerator.converter.EnumPropertyConverter;
import info.xudshen.droiddata.daogenerator.converter.TimestampPropertyConverter;

/**
 * Created by xudshen on 15/10/8.
 */
public abstract class ModelGenerator {
    public static final String TIMESTAMP_TYPE = "java.sql.Timestamp";

    public static final String TIMESTAMP_CONVERTER = TimestampPropertyConverter.class.getName();
    public static final String GSON_EXPOSE = "com.google.gson.annotations.Expose";
    public static final String GSON_SERIALIZEDNAME = "com.google.gson.annotations.SerializedName";
    public static final String GSON_EXPOSE_ANNOTAION = "@Expose";

    protected static Property.PropertyBuilder addDoubleProperty(Entity entity, String propertyName) {
        return entity.addDoubleProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION);
    }

    protected static Property.PropertyBuilder addLongProperty(Entity entity, String propertyName) {
        return entity.addLongProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION);
    }

    protected static Property.PropertyBuilder addIntProperty(Entity entity, String propertyName) {
        return entity.addIntProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION);
    }

    protected static Property.PropertyBuilder addBooleanProperty(Entity entity, String propertyName) {
        return entity.addBooleanProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION);
    }

    protected static Property.PropertyBuilder addStringProperty(Entity entity, String propertyName) {
        return entity.addStringProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION);
    }

    protected static Property.PropertyBuilder addTimestampProperty(Entity entity, String propertyName) {
        entity.addImport(TIMESTAMP_TYPE);
        return entity.addLongProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION)
                .customType(TIMESTAMP_TYPE, TIMESTAMP_CONVERTER);
    }

    protected static Property.PropertyBuilder addEnumProperty(Entity entity, String propertyName, String enumName) {
        entity.addImport(enumName);
        return entity.addStringProperty(propertyName).codeBeforeField(GSON_EXPOSE_ANNOTAION)
                .customType(enumName, EnumPropertyConverter.getName(enumName));
    }

    protected static void addUniqueIndex(Entity entity, Property... properties) {
        Index index = new Index();
        for (Property property : properties) {
            index.addProperty(property);
        }
        index.makeUnique();
        entity.addIndex(index);
    }

    protected static void addIndex(Entity entity, Property... properties) {
        Index index = new Index();
        for (Property property : properties) {
            index.addProperty(property);
        }
        entity.addIndex(index);
    }

    private Schema schema;
    private int version;
    private String defaultJavaPackage;
    private String outDir;

    public ModelGenerator(int version, String defaultJavaPackage, String outDir) {
        this.version = version;
        this.defaultJavaPackage = defaultJavaPackage;
        this.outDir = outDir;
        this.schema = new Schema(version, defaultJavaPackage);
        this.schema.enableKeepSectionsByDefault();
    }

    protected abstract void addEntities(Schema schema);

    protected void generateAll() throws Exception {
        addEntities(this.schema);
        new DaoGenerator().generateAll(this.schema, this.outDir);
    }
}
