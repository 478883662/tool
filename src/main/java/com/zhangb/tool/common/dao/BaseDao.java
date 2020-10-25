package com.zhangb.tool.common.dao;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.zhangb.tool.common.annotation.DbFeildAnnotation;
import com.zhangb.tool.common.annotation.DbTableAnnotation;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by z9104 on 2020/9/25.
 */
public class BaseDao {

    /**
     * 插入
     *
     * @param t
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <T> int insert(T t) throws SQLException, IllegalAccessException {
        if (t == null || !t.getClass().isAnnotationPresent(DbTableAnnotation.class)) {
            throw new IllegalArgumentException("未被DbTableAnnotation注解的类");
        }
        String tableName = AnnotationUtil.getAnnotationValue(t.getClass(), DbTableAnnotation.class);
        Field[] fields = t.getClass().getDeclaredFields();
        Entity entity = Entity.create(tableName);
        for (Field field : fields) {
            String colName = AnnotationUtil.getAnnotationValue(field, DbFeildAnnotation.class);
            boolean isPrimary = AnnotationUtil.getAnnotationValue(field, DbFeildAnnotation.class, "isPrimary");
            if (isPrimary) {
                //如果是主键就不用设置了，让它自增即可
                continue;
            }
            entity.set(colName, ReflectUtil.getFieldValue(t, field));
        }
        return Db.use().insert(entity);
    }


    /**
     * 更新表数据，全部字段都更新
     *
     * @param setT   设置参数
     * @param whereT where条件参数
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <T> int update(T setT, T whereT) throws SQLException, IllegalAccessException {
        if (setT == null || !setT.getClass().isAnnotationPresent(DbTableAnnotation.class)) {
            throw new IllegalArgumentException("未被DbTableAnnotation注解的类");
        }
        //获取表名
        String tableName = AnnotationUtil.getAnnotationValue(setT.getClass(), DbTableAnnotation.class);
        //获取更新的参数名
        Field[] setFields = setT.getClass().getDeclaredFields();
        Entity setEntity = Entity.create();
        for (Field setField : setFields) {
            String colName = AnnotationUtil.getAnnotationValue(setField, DbFeildAnnotation.class);
            boolean isPrimary = AnnotationUtil.getAnnotationValue(setField, DbFeildAnnotation.class, "isPrimary");
            if (isPrimary) {
                //如果是主键就不用设置了，更新时不允许修改
                continue;
            }
            setEntity.set(colName, ReflectUtil.getFieldValue(setT, setField));
        }
        //获取where条件
        Field[] whereFields = whereT.getClass().getDeclaredFields();
        Entity whereEntity = Entity.create(tableName);
        for (Field whereField : whereFields) {
            //获取参数值
            Object colValue = ReflectUtil.getFieldValue(whereT, whereField);
            if (colValue == null) {
                continue;
            }
            //获取字段名
            String colName = AnnotationUtil.getAnnotationValue(whereField, DbFeildAnnotation.class);
            whereEntity.set(colName, colValue);
        }
        return Db.use().update(
                setEntity, //修改的数据
                whereEntity //where条件
        );
    }

    /**
     * 更新表数据,只更新有值的字段。
     *
     * @param setT   设置参数
     * @param whereT where条件参数
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <T> int updateForValue(T setT, T whereT) throws SQLException, IllegalAccessException {
        if (setT == null || !setT.getClass().isAnnotationPresent(DbTableAnnotation.class)) {
            throw new IllegalArgumentException("未被DbTableAnnotation注解的类");
        }
        //获取表名
        String tableName = AnnotationUtil.getAnnotationValue(setT.getClass(), DbTableAnnotation.class);
        //获取更新的参数名
        Field[] setFields = setT.getClass().getDeclaredFields();
        Entity setEntity = Entity.create();
        for (Field setField : setFields) {
            String colName = AnnotationUtil.getAnnotationValue(setField, DbFeildAnnotation.class);
            boolean isPrimary = AnnotationUtil.getAnnotationValue(setField, DbFeildAnnotation.class, "isPrimary");
            if (isPrimary) {
                //如果是主键就不用设置了，更新时不允许修改
                continue;
            }
            Object colValue = ReflectUtil.getFieldValue(setT, setField);
            if (colValue != null) {
                setEntity.set(colName, colValue);
            }
        }
        //获取where条件
        Field[] whereFields = whereT.getClass().getDeclaredFields();
        Entity whereEntity = Entity.create(tableName);
        for (Field whereField : whereFields) {
            //获取参数值
            Object colValue = ReflectUtil.getFieldValue(whereT, whereField);
            if (colValue == null) {
                continue;
            }
            //获取字段名
            String colName = AnnotationUtil.getAnnotationValue(whereField, DbFeildAnnotation.class);
            whereEntity.set(colName, colValue);
        }
        return Db.use().update(
                setEntity, //修改的数据
                whereEntity //where条件
        );
    }

    /**
     * 条件查询数量
     *
     * @param t
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <T> int count(T t) throws SQLException, IllegalAccessException {
        if (t == null || !t.getClass().isAnnotationPresent(DbTableAnnotation.class)) {
            throw new IllegalArgumentException("未被DbTableAnnotation注解的类");
        }
        String tableName = AnnotationUtil.getAnnotationValue(t.getClass(), DbTableAnnotation.class);
        Field[] fields = t.getClass().getDeclaredFields();
        Entity entity = Entity.create(tableName);
        for (Field field : fields) {
            Object value = ReflectUtil.getFieldValue(t, field);
            if (value == null) {
                continue;
            }
            String colName = AnnotationUtil.getAnnotationValue(field, DbFeildAnnotation.class);
            entity.set(colName, value);
        }
        return Db.use().count(entity);
    }

    /**
     * 条件查询
     *
     * @param t
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <R, T> List<R> select(T t, Class<R> clazz) throws SQLException, IllegalAccessException {
        if (t == null || !t.getClass().isAnnotationPresent(DbTableAnnotation.class)) {
            throw new IllegalArgumentException("未被DbTableAnnotation注解的类");
        }
        String tableName = AnnotationUtil.getAnnotationValue(t.getClass(), DbTableAnnotation.class);
        Field[] fields = t.getClass().getDeclaredFields();
        Entity entity = Entity.create(tableName);
        for (Field field : fields) {
            Object value = ReflectUtil.getFieldValue(t, field);
            if (value == null) {
                continue;
            }
            String colName = AnnotationUtil.getAnnotationValue(field, DbFeildAnnotation.class);
            entity.set(colName, value);
        }
        return Db.use().findAll(entity, clazz);
    }


    /**
     * 条件删除
     *
     * @param t
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public static <T> int delete(T t) throws SQLException, IllegalAccessException {
        if (t == null || !t.getClass().isAnnotationPresent(DbTableAnnotation.class)) {
            throw new IllegalArgumentException("未被DbTableAnnotation注解的类");
        }
        String tableName = AnnotationUtil.getAnnotationValue(t.getClass(), DbTableAnnotation.class);
        Field[] fields = t.getClass().getDeclaredFields();
        Entity entity = Entity.create(tableName);
        for (Field field : fields) {
            Object value = ReflectUtil.getFieldValue(t, field);
            if (value == null) {
                continue;
            }
            String colName = AnnotationUtil.getAnnotationValue(field, DbFeildAnnotation.class);
            entity.set(colName, value);
        }
        return Db.use().del(entity);
    }

}
