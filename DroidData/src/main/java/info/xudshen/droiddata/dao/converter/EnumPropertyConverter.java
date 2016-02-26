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
        //TODO: check Enums.getIfPresent(Blah.class, "A")
        try {
            return valueOfIgnoreCase(clazz, value);
        } catch (Exception e) {
            Log.e(this.getClass().getName(), String.format("can not convert (value=%s) to (type=%s)", value, clazz.getName()));
        }
        return null;
    }

    public static String getName(String className) {
        return "info.xudshen.droiddata.dao.converter.EnumPropertyConverter<" + DaoUtil.getClassnameFromFullyQualified(className) + ">";
    }

    public static <T extends Enum<T>> T valueOfIgnoreCase(
            Class<T> enumeration, String name) {

        for (T enumValue : enumeration.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(name)) {
                return enumValue;
            }
        }

        throw new IllegalArgumentException(String.format(
                "There is no value with name '%s' in Enum %s",
                name, enumeration.getName()
        ));
    }
}