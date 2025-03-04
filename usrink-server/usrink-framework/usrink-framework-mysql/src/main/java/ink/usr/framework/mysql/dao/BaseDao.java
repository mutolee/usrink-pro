package ink.usr.framework.mysql.dao;

import ink.usr.framework.mysql.entity.enums.Ds;
import ink.usr.framework.mysql.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao 基类
 */
public abstract class BaseDao<K, T> {

    // 主键名称
    protected abstract String getPrimaryKeyName();

    // 获取 BaseMapper
    protected abstract BaseMapper<T> getBaseMapper();

    /**
     * 插入数据
     *
     * @param model 插入的数据
     * @return 插入的数据
     */
    public T insert(Ds rw, T model) {
        this.getBaseMapper().insert(model);
        return model;
    }

    /**
     * 批量插入数据
     *
     * @param modelList 批量插入的数据
     */
    public void insertBatch(Ds rw, List<T> modelList) {
        if (modelList != null && !modelList.isEmpty()) {
            this.getBaseMapper().insertBatch(modelList);
        }
    }

    /**
     * 更新数据
     *
     * @param model 更新的数据
     * @return 影响行数
     */
    public int update(Ds rw, T model) {
        return this.getBaseMapper().update(model);
    }

    /**
     * 根据 Map 更新数据
     *
     * @param map 更新的数据
     * @return 影响行数
     */
    public int updateByMap(Ds rw, Map<String, Object> map) {
        return this.getBaseMapper().updateByMap(map);
    }

    /**
     * 根据 Map 删除数据
     *
     * @param map 删除的数据
     * @return 影响行数
     */
    public int delete(Ds rw, Map<String, Object> map) {
        return this.getBaseMapper().delete(map);
    }

    /**
     * 根据 ID 删除数据
     *
     * @param id 删除的数据的主键
     * @return 影响行数
     */
    public int deleteById(Ds rw, K id) {
        if (id == null) {
            throw new NullPointerException("id is not null!");
        }
        Map map = new HashMap();
        map.put(this.getPrimaryKeyName(), id);
        return this.delete(rw, map);
    }

    /**
     * 根据 Map 查询数据
     *
     * @param map 查询的数据
     * @return 数据列表
     */
    public List<T> getModelList(Ds rw, Map<String, Object> map) {
        return this.getBaseMapper().getModelList(map);
    }

    /**
     * 根据 ID 查询数据
     *
     * @param id 查询的数据的主键
     * @return 数据对象
     */
    public T getModelById(Ds rw, K id) {
        if (id == null) {
            throw new NullPointerException("id is not null!");
        }
        Map<String, Object> map = new HashMap();
        map.put(this.getPrimaryKeyName(), id);
        return this.getModelOne(rw, map);
    }

    /**
     * 根据 ID 列表查询数据
     *
     * @param idList 查询的数据的主键列表
     * @return 数据列表
     */
    public List<T> getModelsByIds(Ds rw, List<K> idList) {
        if (idList == null || idList.isEmpty()) {
            return new ArrayList<>();
        }
        Map map = new HashMap();
        map.put(getPrimaryKeyName() + "s", idList);
        return this.getModelList(rw, map);
    }

    /**
     * 根据 Map 查询数据
     *
     * @param paramMap 查询的数据
     * @return 数据对象
     */
    public T getModelOne(Ds rw, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap();
        }
        List<T> list = this.getModelList(rw, paramMap);
        return list.size() >= 1 ? list.get(0) : null;
    }

}
