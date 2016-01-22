package com.kld.gsm.ATG.common.base;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 17:11
 * @Description:
 */


import com.kld.gsm.ATG.common.exception.ServiceException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public   interface BaseService<T, PK extends Serializable> {

    /**
     * 保存对象
     *
     * @param o
     * @return
     */
    void save(T o)throws DataAccessException,ServiceException;




    /**
     * 批量添加
     *
     * @param list
     */
    void batchInsert(List<T> list)throws DataAccessException,ServiceException;

    /**
     * 删除对象记录
     *
     * @param id
     */
    void delete(PK id)throws DataAccessException,ServiceException;

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    T getByID(PK id)throws DataAccessException,ServiceException;

    /**
     * 跟新对象
     *
     * @param model
     */
    void update(T model)throws DataAccessException,ServiceException;

    /**
     * 统计数据总条数
     *
     * @return
     */
    int countAll()throws DataAccessException,ServiceException;

    /**
     * 获取全部对象
     *
     * @return
     */
    List<T> findAll()throws DataAccessException,ServiceException;

    /**
     * 查询所有的ID集合
     *
     * @return
     */
    List<PK> findAllIds()throws DataAccessException,ServiceException;

    /**
     * 根据此查询条件统计记录总条数
     *
     * @param parameters map 包含各种属性的查询
     * @return
     */
    Integer findNumberByCondition(Map<String, Object> parameters)throws DataAccessException,ServiceException;

    /**
     * 分页查询函数，返回对象集合
     *
     * @param parameters map 包含各种属性的查询
     * @param rowBounds  偏移量恢复为初始值(offet:0,limit:Integer.max) 使用参考：new RowBounds(pageNum, pageSize)
     * @return
     */
    List<T> findPageBreakByCondition(Map<String, Object> parameters, RowBounds rowBounds)throws DataAccessException,ServiceException;

}
