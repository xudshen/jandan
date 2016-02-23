package ${contentProvider.javaPackage};

import java.util.ArrayList;
import java.util.List;

import info.xudshen.droiddata.dao.IModelTrans;
import info.xudshen.droiddata.dao.IModelObservable;
<#list contentProvider.entities as entity>
import ${entity.javaPackage}.${entity.className};
</#list>
<#list contentProvider.entities as entity>
import ${schema.defaultJavaPackageObservable}.${entity.className}Observable;
</#list>

public class ${contentProvider.className} {

<#list contentProvider.entities as entity>
    private static final IModelTrans<${entity.className}, ${entity.className}Observable> ${entity.tableName}_TRANS =
            new IModelTrans<${entity.className}, ${entity.className}Observable>() {
                @Override
                public ${entity.className}Observable to(${entity.className} entity) {
                    return new ${entity.className}Observable(entity);
                }
            };
</#list>

    /**
     * This must be set from outside, it's recommended to do this inside your Application object.
     * Subject to change (static isn't nice).
     */
    public DaoSession daoSession;

    public ${contentProvider.className}(DaoSession daoSession) {
        this.daoSession = daoSession;
    }
<#list contentProvider.entities as entity>

    //<editor-fold desc="Trans${entity.className}">
    public <TO extends IModelObservable> TO trans${entity.className?cap_first}(${entity.className} entity, IModelTrans<${entity.className}, TO> trans) {
        TO entityOb = trans.to(entity);
        this.daoSession.get${entity.className?cap_first}Dao().registerExtraOb(entityOb);
        return entityOb;
    }

    public ${entity.className}Observable trans${entity.className?cap_first}(${entity.className} entity) {
        return trans${entity.className?cap_first}(entity, ${entity.tableName}_TRANS);
    }

    public <TO extends IModelObservable> Iterable<TO> trans${entity.className?cap_first}(Iterable<${entity.className}> entities, IModelTrans<${entity.className}, TO> trans) {
        List<TO> list = new ArrayList<>();
        ${entity.className?cap_first}Dao dao = this.daoSession.get${entity.className?cap_first}Dao();
        for (${entity.className} entity : entities) {
            TO entityOb = trans.to(entity);
            list.add(entityOb);
            dao.registerExtraOb(entityOb);
        }
        return list;
    }

    public Iterable<${entity.className}Observable> trans${entity.className?cap_first}(Iterable<${entity.className}> entities) {
        return trans${entity.className?cap_first}(entities, ${entity.tableName}_TRANS);
    }
    //</editor-fold>
</#list>
}