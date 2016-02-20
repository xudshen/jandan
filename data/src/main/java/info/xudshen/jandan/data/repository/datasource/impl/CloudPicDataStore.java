package info.xudshen.jandan.data.repository.datasource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.jandan.data.api.response.IPicService;
import info.xudshen.jandan.data.api.response.PicListResponse;
import info.xudshen.jandan.data.dao.MetaDao;
import info.xudshen.jandan.data.dao.PicItemDao;
import info.xudshen.jandan.data.repository.datasource.PicDataStore;
import info.xudshen.jandan.domain.model.Meta;
import info.xudshen.jandan.domain.model.PicItem;
import rx.Observable;

/**
 * Created by xudshen on 16/2/20.
 */
public class CloudPicDataStore implements PicDataStore {
    private static final Logger logger = LoggerFactory.getLogger(CloudPicDataStore.class);

    private final IPicService picService;
    private final PicItemDao picItemDao;
    private final MetaDao metaDao;

    public CloudPicDataStore(IPicService picService, PicItemDao picItemDao, MetaDao metaDao) {
        this.picService = picService;
        this.picItemDao = picItemDao;
        this.metaDao = metaDao;
    }

    @Override
    public Observable<List<PicItem>> picList() {
        return this.picService.getPicListAsync(1l)
                .map(picListResponse -> {
                    List<PicItem> picItems = new ArrayList<PicItem>();
                    for (PicListResponse.PicItemWrapper wrapper : picListResponse.getList()) {
                        picItems.add(wrapper.getPicItem());
                    }
                    return picItems;
                })
                .doOnNext(picItems -> {
                    CloudPicDataStore.this.picItemDao.deleteAll();
                    CloudPicDataStore.this.picItemDao.insertOrReplaceInTx(picItems);
                })
                .doOnCompleted(() -> {
                    Meta meta = DataStoreHelper.getMeta(this.metaDao);
                    meta.setPicPage(1l);
                    this.metaDao.update(meta);
                });
    }

    @Override
    public Observable<List<PicItem>> picListNext() {
        Meta meta = DataStoreHelper.getMeta(this.metaDao);
        return this.picService.getPicListAsync(meta.getPicPage() + 1)
                .map(picListResponse -> {
                    List<PicItem> picItems = new ArrayList<PicItem>();
                    for (PicListResponse.PicItemWrapper wrapper : picListResponse.getList()) {
                        picItems.add(wrapper.getPicItem());
                    }
                    return picItems;
                })
                .doOnNext(picItems -> {
                    if (meta.getPicPage() + 1 == 1) {
                        //TODO: not right
                        CloudPicDataStore.this.picItemDao.deleteAll();
                    }
                    CloudPicDataStore.this.picItemDao.insertOrReplaceInTx(picItems);
                })
                .doOnCompleted(() -> {
                    meta.setPicPage(meta.getPicPage() + 1);
                    this.metaDao.update(meta);
                });
    }
}
