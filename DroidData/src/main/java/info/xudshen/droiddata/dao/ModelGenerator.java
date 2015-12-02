package info.xudshen.droiddata.dao;

import info.xudshen.droiddata.dao.converter.EnumPropertyConverter;
import info.xudshen.droiddata.dao.converter.TimestampPropertyConverter;
import info.xudshen.droiddata.dao.generator.DaoGenerator;
import info.xudshen.droiddata.dao.generator.Entity;
import info.xudshen.droiddata.dao.generator.Index;
import info.xudshen.droiddata.dao.generator.Property;
import info.xudshen.droiddata.dao.generator.Schema;

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
    private String daoOutDir;
    private String entityOutDir;
    private String testOutDir;

    public ModelGenerator(int version, String entityJavaPackage) {
        this.schema = new Schema(version, entityJavaPackage);
        this.schema.enableKeepSectionsByDefault();
    }


    public void setEntityOutDir(String entityOutDir) {
        this.entityOutDir = entityOutDir;
    }

    public void setDaoOutDir(String daoOutDir) {
        this.daoOutDir = daoOutDir;
    }

    public void setTestOutDir(String testOutDir) {
        this.testOutDir = testOutDir;
    }

    public void setBRPath(String brPath) {
        this.schema.setDefaultBRPath(brPath);
    }

    public void setDaoJavaPackage(String daoJavaPackage) {
        this.schema.setDefaultJavaPackageDao(daoJavaPackage);
    }

    public void setObservableJavaPackage(String observableJavaPackage) {
        this.schema.setDefaultJavaPackageObservable(observableJavaPackage);
    }

    protected abstract void addEntities(Schema schema);

    protected void generateAll() throws Exception {
        addEntities(this.schema);
        new DaoGenerator().generateAll(this.schema, this.daoOutDir, this.entityOutDir, this.testOutDir);
    }
}
