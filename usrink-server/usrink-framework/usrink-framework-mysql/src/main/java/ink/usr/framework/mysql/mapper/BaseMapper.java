package ink.usr.framework.mysql.mapper;

import java.util.List;
import java.util.Map;

/**
 * Mapper 基类
 */
public interface BaseMapper<T> {

    /**
     * 插入
     *
     * @param model
     */
    void insert(T model);

    /**
     * 批量插入
     *
     * @param modelList
     */
    void insertBatch(List<T> modelList);

    /**
     * 更新
     *
     * @param model
     * @return
     */
    int update(T model);

    /**
     * 根据传入的 map 进行更新
     *
     * @param map 参数
     * @return
     */
    int updateByMap(Map<String, Object> map);

    /**
     * 删除数据
     *
     * @param map 参数
     * @return
     */
    int delete(Map<String, Object> map);

    /**
     * 获取记录列表
     *
     * @param map 查询条件
     * @return
     */
    List<T> getModelList(Map<String, Object> map);

}
