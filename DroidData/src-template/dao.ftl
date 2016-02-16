<#--

Copyright (C) 2011-2015 Markus Junginger, greenrobot (http://greenrobot.de)
                                                                           
This file is part of greenDAO Generator.                                   
                                                                           
greenDAO Generator is free software: you can redistribute it and/or modify 
it under the terms of the GNU General Public License as published by       
the Free Software Foundation, either version 3 of the License, or          
(at your option) any later version.                                        
greenDAO Generator is distributed in the hope that it will be useful,      
but WITHOUT ANY WARRANTY; without even the implied warranty of             
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              
GNU General Public License for more details.                               
                                                                           
You should have received a copy of the GNU General Public License          
along with greenDAO Generator.  If not, see <http://www.gnu.org/licenses/>.

-->
<#assign toBindType = {"Boolean":"Long", "Byte":"Long", "Short":"Long", "Int":"Long", "Long":"Long", "Float":"Double", "Double":"Double", "String":"String", "ByteArray":"Blob", "Date": "Long" } />
<#assign toCursorType = {"Boolean":"Short", "Byte":"Short", "Short":"Short", "Int":"Int", "Long":"Long", "Float":"Float", "Double":"Double", "String":"String", "ByteArray":"Blob", "Date": "Long"  } />
package ${entity.javaPackageDao};

<#if entity.toOneRelations?has_content || entity.incomingToManyRelations?has_content>
import java.util.List;
</#if>
<#if entity.toOneRelations?has_content>
import java.util.ArrayList;
</#if>
import java.lang.ref.WeakReference;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.RemoteException;

import info.xudshen.droiddata.dao.DDAbstractDao;
import de.greenrobot.dao.Property;
<#if entity.toOneRelations?has_content>
import de.greenrobot.dao.internal.SqlUtils;
</#if>
import de.greenrobot.dao.internal.DaoConfig;
<#if entity.incomingToManyRelations?has_content>
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
</#if>

<#if entity.javaPackageDao != schema.defaultJavaPackageDao>
import ${schema.defaultJavaPackageDao}.DaoSession;

</#if>
<#if entity.additionalImportsDao?has_content>
<#list entity.additionalImportsDao as additionalImport>
import ${additionalImport};
</#list>

</#if>
import java.util.ArrayList;
<#if entity.observable >
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

</#if>
import ${entity.javaPackage}.${entity.className};
<#if entity.protobuf>
import ${entity.javaPackage}.${entity.className}.Builder;
</#if>

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "${entity.tableName}".
*/
public class ${entity.classNameDao} extends DDAbstractDao<${entity.className}, ${entity.pkType}> {

    public static final String TABLENAME = "${entity.tableName}";

    /**
     * Properties of entity ${entity.className}.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
<#list entity.propertiesColumns as property>
        public final static Property ${property.propertyName?cap_first} = new Property(${property_index}, ${property.javaType}.class, "${property.propertyName}", ${property.primaryKey?string}, "${property.columnName}");
</#list>
    }

<#if entity.active>
    private DaoSession daoSession;

</#if>
<#list entity.properties as property><#if property.customType?has_content><#--
-->    private final ${property.converterClassName} ${property.propertyName}Converter = new ${property.converterClassName}();
</#if></#list>
<#list entity.incomingToManyRelations as toMany>
    private Query<${toMany.targetEntity.className}> ${toMany.sourceEntity.className?uncap_first}_${toMany.name?cap_first}Query;
</#list>

    public ${entity.classNameDao}(DaoConfig config) {
        super(config);
    }
    
    public ${entity.classNameDao}(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
<#if entity.active>
        this.daoSession = daoSession;
</#if>
    }

<#if !entity.skipTableCreation>
    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"${entity.tableName}\" (" + //
<#list entity.propertiesColumns as property>
                "\"${property.columnName}\" ${property.columnType}<#if property.constraints??> ${property.constraints} </#if><#if property_has_next>," +<#else>);");</#if> // ${property_index}: ${property.propertyName}
</#list>
<#if entity.indexes?has_content >
        // Add Indexes
<#list entity.indexes as index>
        db.execSQL("CREATE <#if index.unique>UNIQUE </#if>INDEX " + constraint + "${index.name} ON ${entity.tableName}" +
                " (<#list index.properties 
as property>\"${property.columnName}\"<#if property_has_next>,</#if></#list>);");
</#list>
</#if>         
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"${entity.tableName}\"";
        db.execSQL(sql);
    }

</#if>
    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ${entity.className} entity) {
        stmt.clearBindings();
<#list entity.properties as property>
<#if property.notNull || entity.protobuf>
<#if entity.protobuf>
        if(entity.has${property.propertyName?cap_first}()) {
    </#if>        stmt.bind${toBindType[property.propertyType]}(${property_index + 1}, ${property.databaseValueExpressionNotNull});
<#if entity.protobuf>
        }
</#if>
<#else> <#-- nullable, non-protobuff -->
        ${property.javaTypeInEntity} ${property.propertyName} = entity.get${property.propertyName?cap_first}();
        if (${property.propertyName} != null) {
            stmt.bind${toBindType[property.propertyType]}(${property_index + 1}, ${property.databaseValueExpression});
        }
</#if>
</#list>
<#list entity.toOneRelations as toOne>
<#if !toOne.fkProperties?has_content>

        ${toOne.targetEntity.className} ${toOne.name} = entity.peak${toOne.name?cap_first}();
        if(${toOne.name} != null) {
            ${toOne.targetEntity.pkProperty.javaType} ${toOne.name}__targetKey = ${toOne.name}.get${toOne.targetEntity.pkProperty.propertyName?cap_first}();
<#if !toOne.targetEntity.pkProperty.notNull>
            if(${toOne.name}__targetKey != null) {
                // TODO bind ${toOne.name}__targetKey
            }
<#else>
            // TODO bind ${toOne.name}__targetKey
</#if>
        }
</#if>
</#list>
    }

<#if entity.active>
    @Override
    protected void attachEntity(${entity.className} entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

</#if>
    /** @inheritdoc */
    @Override
    public ${entity.pkType} readKey(Cursor cursor, int offset) {
<#if entity.pkProperty??>
        return <#if !entity.pkProperty.notNull>cursor.isNull(offset + ${entity.pkProperty.ordinal}) ? null : </#if><#if
            entity.pkProperty.propertyType == "Byte">(byte) </#if><#if
            entity.pkProperty.propertyType == "Date">new java.util.Date(</#if>cursor.get${toCursorType[entity.pkProperty.propertyType]}(offset + ${entity.pkProperty.ordinal})<#if
            entity.pkProperty.propertyType == "Boolean"> != 0</#if><#if
            entity.pkProperty.propertyType == "Date">)</#if>;
<#else>
        return null;
</#if>  
    }    

    /** @inheritdoc */
    @Override
    protected ${entity.className} readEntity(Cursor cursor, int offset) {
<#if entity.protobuf>
        Builder builder = ${entity.className}.newBuilder();
<#list entity.properties as property>
<#if !property.notNull>
        if (!cursor.isNull(offset + ${property_index})) {
    </#if>        builder.set${property.propertyName?cap_first}(cursor.get${toCursorType[property.propertyType]}(offset + ${property_index}));
<#if !property.notNull>
        }
</#if>        
</#list>        
        return builder.build();
<#elseif entity.constructors>
<#--
############################## readEntity non-protobuff, constructor ############################## 
-->
        ${entity.className} entity = new ${entity.className}( //
<#list entity.properties as property>
            <#if !property.notNull>cursor.isNull(offset + ${property_index}) ? null : </#if><#--
            -->${property.getEntityValueExpression("cursor.get${toCursorType[property.propertyType]}(offset + ${property_index})")}<#--
            --><#if property_has_next>,</#if> // ${property.propertyName}
</#list>        
        );
        return entity;
<#else>
<#--
############################## readEntity non-protobuff, setters ############################## 
-->
        ${entity.className} entity = new ${entity.className}();
        readEntity(cursor, entity, offset);
        return entity;
</#if>
    }
     
    /** @inheritdoc */
    @Override
    protected void readEntity(Cursor cursor, ${entity.className} entity, int offset) {
<#if entity.protobuf>
        throw new UnsupportedOperationException("Protobuf objects cannot be modified");
<#else> 
<#list entity.properties as property>
        entity.set${property.propertyName?cap_first}(<#if !property.notNull>cursor.isNull(offset + ${property_index}) ? null : </#if><#--
            -->${property.getEntityValueExpression("cursor.get${toCursorType[property.propertyType]}(offset + ${property_index})")});
</#list>
</#if>
    }
    
    /** @inheritdoc */
    @Override
    protected ${entity.pkType} updateKeyAfterInsert(${entity.className} entity, long rowId) {
<#if entity.pkProperty??>
<#if entity.pkProperty.propertyType == "Long">
<#if !entity.protobuf>
        entity.set${entity.pkProperty.propertyName?cap_first}(rowId);
</#if>
        return rowId;
<#else>
        return entity.get${entity.pkProperty.propertyName?cap_first}();
</#if>
<#else>
        // Unsupported or missing PK type
        return null;
</#if>
    }
    
    /** @inheritdoc */
    @Override
    public ${entity.pkType} getKey(${entity.className} entity) {
<#if entity.pkProperty??>
        if (entity != null) {
            return entity.get${entity.pkProperty.propertyName?cap_first}();
        } else {
            return null;
        }
<#else>
        return null;
</#if>    
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return ${(!entity.protobuf)?string};
    }

    public static final String AUTHORITY = "${schema.defaultJavaPackageDao}.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLENAME);
    private WeakReference<Context> contextWeakReference;

    public void setContext(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    protected void notifyInsert(${entity.className} entity) {
        Long key = getKey(entity);
        if (key != null) {
<#if entity.observable >
            notifyExtraOb(key);

</#if>
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().insert(
                        ContentUris.withAppendedId(CONTENT_URI, key), null);
        }
    }

    @Override
    protected void notifyInsert(Iterable<${entity.className}> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (${entity.className} entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
<#if entity.observable >
                notifyExtraOb(key);

</#if>
                ops.add(ContentProviderOperation.newInsert(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyUpdate(${entity.className} entity) {
        Long key = getKey(entity);
        if (key != null) {
<#if entity.observable >
            notifyExtraOb(key);

</#if>
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().update(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null, null);
        }
    }

    @Override
    protected void notifyUpdate(Iterable<${entity.className}> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (${entity.className} entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
<#if entity.observable >
                notifyExtraOb(key);

</#if>
                ops.add(ContentProviderOperation.newUpdate(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyDelete(${entity.className} entity) {
        Long key = getKey(entity);
        if (key != null) {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
        }
    }

    @Override
    protected void notifyDelete(Iterable<${entity.className}> entities) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (${entity.className} entity : entities) {
            Long key = getKey(entity);
            if (key != null) {
                ops.add(ContentProviderOperation.newDelete(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void notifyDeleteByKey(Long key) {
        if (key != null && contextWeakReference.get() != null) {
            if (key == -1) {
                contextWeakReference.get().getContentResolver().delete(
                        CONTENT_URI, null, null);
            } else {
                contextWeakReference.get().getContentResolver().delete(
                        ContentUris.withAppendedId(CONTENT_URI, key), null, null);
            }
        }
    }

    @Override
    protected void notifyDeleteByKey(Iterable<Long> keys) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        for (Long key : keys) {
            if (key != null) {
                ops.add(ContentProviderOperation.newDelete(
                        ContentUris.withAppendedId(CONTENT_URI, key)).build());
            }
        }

        try {
            if (contextWeakReference.get() != null)
                contextWeakReference.get().getContentResolver().applyBatch(AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }
<#if entity.observable>

    private Map<Long, WeakHashMap<IModelObservable<${entity.className}>, Boolean>> extraObMap = new HashMap<>();

    /**
     * register entity to extraObMap
     */
    public void registerExtraOb(IModelObservable entity) {
        if (entity == null) return;
        if (!extraObMap.containsKey(entity.getModelKey())) {
            extraObMap.put(entity.getModelKey(), new WeakHashMap<>());
        }
        WeakHashMap<IModelObservable<${entity.className}>, Boolean> map = extraObMap.get(entity.getModelKey());
        if (!map.containsKey(entity)) {
            map.put(entity, true);
        }
    }

    private void notifyExtraOb(Long key) {
        if (extraObMap.containsKey(key)) {
            ${entity.className} newEntity = load(key);
            for (IModelObservable<${entity.className}> entity : extraObMap.get(key).keySet()) {
                if (entity != null)
                    entity.refresh(newEntity);
            }
        }
    }

    public ${entity.className} loadEntity(Cursor cursor){
        return this.readEntity(cursor, 0);
    }

</#if>
<#list entity.incomingToManyRelations as toMany>
    /** Internal query to resolve the "${toMany.name}" to-many relationship of ${toMany.sourceEntity.className}. */
    public List<${toMany.targetEntity.className}> _query${toMany.sourceEntity.className?cap_first}_${toMany.name?cap_first}(<#--
    --><#if toMany.targetProperties??><#list toMany.targetProperties as property><#--
    -->${property.javaType} ${property.propertyName}<#if property_has_next>, </#if></#list><#else><#--
    -->${toMany.sourceProperty.javaType} ${toMany.sourceProperty.propertyName}</#if>) {
        synchronized (this) {
            if (${toMany.sourceEntity.className?uncap_first}_${toMany.name?cap_first}Query == null) {
                QueryBuilder<${toMany.targetEntity.className}> queryBuilder = queryBuilder();
<#if toMany.targetProperties??>
    <#list toMany.targetProperties as property>
                queryBuilder.where(Properties.${property.propertyName?cap_first}.eq(null));
    </#list>
<#else>
                queryBuilder.join(${toMany.joinEntity.className}.class, ${toMany.joinEntity.classNameDao}.Properties.${toMany.targetProperty.propertyName?cap_first})
                    .where(${toMany.joinEntity.classNameDao}.Properties.${toMany.sourceProperty.propertyName?cap_first}.eq(${toMany.sourceProperty.propertyName}));
</#if>
<#if toMany.order?has_content>
                queryBuilder.orderRaw("${toMany.order}");
</#if>
                ${toMany.sourceEntity.className?uncap_first}_${toMany.name?cap_first}Query = queryBuilder.build();
            }
        }
        Query<${toMany.targetEntity.className}> query = ${toMany.sourceEntity.className?uncap_first}_${toMany.name?cap_first}Query.forCurrentThread();
<#if toMany.targetProperties??>
    <#list toMany.targetProperties as property>
        query.setParameter(${property_index}, ${property.propertyName});
    </#list>
<#else>
        query.setParameter(0, ${toMany.sourceProperty.propertyName});
</#if>
        return query.list();
    }

</#list>   
<#if entity.toOneRelations?has_content>
    <#include "dao-deep.ftl">
</#if>
}
