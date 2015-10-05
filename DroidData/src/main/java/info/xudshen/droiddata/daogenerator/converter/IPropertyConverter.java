package info.xudshen.droiddata.daogenerator.converter;

/**
 * Created by xudshen on 15/7/14.
 */
public interface IPropertyConverter<T, TypeInDB> {
    TypeInDB convertToDatabaseValue(T e);

    T convertToEntityProperty(Class<T> clazz, TypeInDB value);
}