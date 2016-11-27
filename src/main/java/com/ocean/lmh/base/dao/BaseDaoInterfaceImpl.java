package com.ocean.lmh.base.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ocean.lmh.base.handler.MapResultHandler;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;
/**
 * mybatis 通用dao实现类
 * 
 */

@Repository(value = "mybatisBaseDao")
public class BaseDaoInterfaceImpl implements BaseDaoInterface
{
	public final static String PAGE_KEY = "page";
	/**
	 * 可读可写的sqlSession
	 */
    @Resource(name = "sqlSession")
    private SqlSessionTemplate sqlSession;

    @Override
    public int deleteData(String sqlKey, Object obj)
    {
        return sqlSession.delete(sqlKey, obj);
    }

    @Override
    public <T> T query(String sqlKey, Object obj)
    {
        return sqlSession.selectOne(sqlKey, obj);
    }

    @Override
    public <T> List<T> queryForList(String sqlKey, Map<String, Object> map)
    {
        return sqlSession.selectList(sqlKey, map);
    }
    
    
    
    @Deprecated
    @Override
    public <T> PaginationBean<T> queryForListByPage(String sqlKey,
        Map<String, Object> map, int currentPage, int pageSize)
    {
        String selectCount = sqlKey + "Count";
        int total = sqlSession.selectOne(selectCount, map);
        PaginationBean<T> pb = new PaginationBean<T>(currentPage, total,
                pageSize);

        map.put("start", (currentPage - 1 <= 0 ? 0 : currentPage - 1)
                * pageSize);
        map.put("end",pageSize);

        List<T> dataList = sqlSession.selectList(sqlKey, map);
        pb.setDataList(dataList);
        return pb;
    }
    
    

    @Override
    public boolean insertData(String sqlKey, Object obj)
    {
        int effectRow = sqlSession.insert(sqlKey, obj);
        if (effectRow == 1)
        {
            return true;
        }
           
        return false;
    }

    @Override
    public int updateData(String sqlKey, Object obj)
    {
        return sqlSession.update(sqlKey, obj);
    }

    /**
     * 批量导入数据的dao实现类。如果传入的源数据list为空或者是size为零
     * 则返回null值，当此操作成功(不报插入异常)则返回一个BatchResult类型
     * 的list集合，集合中包含一个(目前测试知道的)BatchResult，此对象的
     * getParameterObjects()会返回一个list包含了此次插入的所有实体记录
     * 信息且是带有主键id自增值；getUpdateCounts()会返回一个值为0和1整型
     * 数组，1表示那些记录插入成功，0表示那些记录插入失败。
     * @param sqlKey 需要执行插入的sql语句(单条插入的语句)
     * @param list 将要进行批量导入的源数据
     * @return List<BatchResult> 集合对象
     */
	@Override
    public <T> List<BatchResult> batchInsert(String sqlKey, List<T> list)
    {
    	if (AssertHelper.isEmptyCollection(list))
    	{
			return null;
		}
        SqlSession test = sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        int size = list.size();
        for (int i=0; i<size; i++)
        {
            test.insert(sqlKey,list.get(i));
        }
        List<BatchResult> results = ((SqlSessionTemplate) test).flushStatements();
        if (AssertHelper.isEmptyCollection(results))
        {
        	return null;
		}
        return results;
    }

    @Override
    public <T> List<T> queryByIn(String sqlKey, Object params)
    {
        return sqlSession.selectList(sqlKey, params);
    }

    @Override
    public <T> int batchDelete(String sqlKey, List<T> list)
    {
        SqlSession test = sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        for (T t : list)
        {
            test.delete(sqlKey,t);
        }
        ((SqlSessionTemplate) test).flushStatements();
        return 0;
    }

    @Override
    public <T> List<BatchResult> batchUpdate(String sqlKey, List<T> list)
    {
        SqlSession test = sqlSession.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        for (T t : list)
        {
            test.update(sqlKey,t);
        }
        List<BatchResult> result = ((SqlSessionTemplate) test).flushStatements();
        return result;
    }

    public <T> PaginationBean<T> queryForListPageByMap(String sqlKey,Map<String, Object> map)
    {
       @SuppressWarnings("unchecked")
       PaginationBean<T> page = (PaginationBean<T>) map.get(PAGE_KEY);
       if(page == null)
       {
           page = new PaginationBean<T>(1, 0, 10);
       }
       List<T> dataList = sqlSession.selectList(sqlKey, map);

       page.setDataList(dataList);
       page.setPageCount(page.getRightTotalPage(page.getRowCount(), page.getPageSize()));
       page.initPageParam();
       
       return page;
    }

	@Override
	public <T> Map<String, Object> queryForMap(String sqlKey,
			Object parameterObject, String keyProp) {
		return sqlSession.selectMap(sqlKey, parameterObject, keyProp);
	}
    
    @Override
    public <K,V> void queryMap(String sqlKey, Map<String, Object> map,Map<K,V> resultMap,String mapKey)
    {
        sqlSession.select(sqlKey, map,  new MapResultHandler<K, V>(resultMap,mapKey));
    }

}
