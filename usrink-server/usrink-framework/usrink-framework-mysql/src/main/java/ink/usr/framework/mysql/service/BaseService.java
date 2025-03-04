package ink.usr.framework.mysql.service;

import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.entity.enums.Ds;

import java.util.List;
import java.util.Map;

/**
 * Server 基类
 */
public abstract class BaseService<K, T> {

    public abstract BaseDao<K, T> getBaseDao();

    /**
     * 插入数据
     *
     * @param model 插入的数据
     * @return 插入的数据对象
     */
    public T insert(Ds rw, T model) {
        return this.getBaseDao().insert(rw, model);
    }

    /**
     * 批量插入数据
     *
     * @param modelList 插入的数据
     */
    public void insertBatch(Ds rw, List<T> modelList) {
        this.getBaseDao().insertBatch(rw, modelList);
    }

    /**
     * 更新数据
     *
     * @param model 更新的数据
     * @return 更新的数据对象
     */
    public int update(Ds rw, T model) {
        return this.getBaseDao().update(rw, model);
    }

    /**
     * 根据map更新数据
     *
     * @param map 更新的数据
     * @return 更新的数据对象
     */
    public int updateByMap(Ds rw, Map<String, Object> map) {
        return this.getBaseDao().updateByMap(rw, map);
    }

    /**
     * 根据id删除数据
     *
     * @param id 删除数据的主键
     * @return 影响行数
     */
    public int deleteById(Ds rw, K id) {
        return this.getBaseDao().deleteById(rw, id);
    }

    /**
     * 根据map删除数据
     *
     * @param map 删除数据的条件
     * @return 影响行数
     */
    public int deleteByMap(Ds rw, Map<String, Object> map) {
        return this.getBaseDao().delete(rw, map);
    }

    /**
     * 根据map删除数据
     *
     * @param map 删除数据的条件
     * @return 影响行数
     */
    public List<T> getModelList(Ds rw, Map<String, Object> map) {
        return this.getBaseDao().getModelList(rw, map);
    }

    /**
     * 根据id查询数据
     *
     * @param id 查询数据的主键
     * @return 查询的数据对象
     */
    public T getModelById(Ds rw, K id) {
        return this.getBaseDao().getModelById(rw, id);
    }

    /**
     * 根据id列表查询数据
     *
     * @param idList 查询数据的主键列表
     * @return 查询的数据对象列表
     */
    public List<T> getModelsByIds(Ds rw, List<K> idList) {
        return this.getBaseDao().getModelsByIds(rw, idList);
    }

    /**
     * 根据map查询数据
     *
     * @param map 查询数据的条件
     * @return 查询的数据对象
     */
    public T getModelOne(Ds rw, Map<String, Object> map) {
        return this.getBaseDao().getModelOne(rw, map);
    }
}
