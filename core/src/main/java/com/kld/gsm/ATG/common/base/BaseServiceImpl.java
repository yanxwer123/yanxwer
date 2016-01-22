package com.kld.gsm.ATG.common.base;


import com.kld.gsm.ATG.common.exception.ServiceException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 17:13
 * @Description:
 */

public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
    protected  abstract BaseDao<T,PK> getDao();

    @Override
    public void save(T o) throws DataAccessException, ServiceException {
        this.getDao().save(o);
    }

    @Override
    public void batchInsert(List<T> list) throws DataAccessException, ServiceException {
        this.getDao().batchInsert(list);
    }

    @Override
    public void delete(PK id) throws DataAccessException, ServiceException {
        this.getDao().delete(id);
    }

    @Override
    public T getByID(PK id) throws DataAccessException, ServiceException {
        return this.getDao().getByID(id);
    }

    @Override
    public void update(T model) throws DataAccessException, ServiceException {
        this.getDao().update(model);
    }

    @Override
    public int countAll() throws DataAccessException, ServiceException {
        return this.getDao().countAll();
    }

    @Override
    public List<T> findAll() throws DataAccessException, ServiceException {
        return this.getDao().findAll();
    }

    @Override
    public List<PK> findAllIds() throws DataAccessException, ServiceException {
        return this.getDao().findAllIds();
    }

    @Override
    public Integer findNumberByCondition(Map<String, Object> parameters) throws DataAccessException, ServiceException {
        return this.getDao().findNumberByCondition(parameters);
    }

    @Override
    public List<T> findPageBreakByCondition(Map<String, Object> parameters, RowBounds rowBounds) throws DataAccessException, ServiceException {
        return this.getDao().findPageBreakByCondition(parameters,rowBounds);
    }
}
