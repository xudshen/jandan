package info.xudshen.jandan.data.repository.datasource.impl;

import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.domain.model.Meta;

/**
 * Created by xudshen on 16/2/20.
 */
public class DataStoreHelper {
    public static final String POST_KEY = "post_page";
    public static final String PIC_KEY = "pic_page";

    public static Meta getMeta(MetaDao metaDao, String key) {
        Meta meta = null;
        if (metaDao.queryBuilder().where(MetaDao.Properties.Key.eq(key)).count() == 0) {
            meta = new Meta();
            meta.setKey(key);
            meta.setLongValue(0l);
            metaDao.insert(meta);
        } else {
            meta = metaDao.queryBuilder().where(MetaDao.Properties.Key.eq(key))
                    .build().forCurrentThread().unique();
        }
        return meta;
    }
}
