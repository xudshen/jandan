package info.xudshen.droiddata.dao.converter;

import java.sql.Timestamp;

/**
 * Created by xudshen on 15/7/14.
 */
public class TimestampPropertyConverter implements IPropertyConverter<Timestamp, Long> {
    public TimestampPropertyConverter() {
    }

    @Override
    public Long convertToDatabaseValue(Timestamp e) {
        return e != null ? e.getTime() : null;
    }

    @Override
    public Timestamp convertToEntityProperty(Class<Timestamp> clazz, Long value) {
        return value != null ? new Timestamp(value) : null;
    }
}