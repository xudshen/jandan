package info.xudshen.droiddata.dao.converter;

import android.util.Log;

import info.xudshen.droiddata.dao.generator.DaoUtil;


/**
 * Created by xudshen on 15/7/14.
 */
public class EnumPropertyConverter<T extends Enum<T>> implements IPropertyConverter<T, String> {

    public EnumPropertyConverter() {
    }

    @Override
    public String convertToDatabaseValue(T e) {
        if (e == null) return null;
        return e.name();
    }

    @Override
    public T convertToEntityProperty(Class<T> clazz, String value) {
//        if (BaseStatus.class.isAssignableFrom(clazz)) {
//            return (T) BaseStatus.valueOf(value);
//        }
        Log.e(this.getClass().getName(), String.format("can not convert (value=%s) to (type=%s)", value, clazz.getName()));
        return null;
    }

    public static String getName(String className) {
        return "info.xudshen.droiddata.dao.converter.EnumPropertyConverter<" + DaoUtil.getClassnameFromFullyQualified(className) + ">";
    }
}