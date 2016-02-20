package info.xudshen.jandan.data.repository.datasource.impl;

import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.domain.model.Meta;

/**
 * Created by xudshen on 16/2/20.
 */
public class DataStoreHelper {
    public static Meta getMeta(MetaDao metaDao) {
        Meta meta = null;
        if (metaDao.count() == 0) {
            meta = new Meta(1l);
            meta.setPostPage(0l);
            meta.setPicPage(0l);
            metaDao.insertInTx(meta);
        } else {
            meta = metaDao.queryBuilder().where(MetaDao.Properties.Id.eq(1l))
                    .build().forCurrentThread().unique();
        }
        return meta;
    }
}
