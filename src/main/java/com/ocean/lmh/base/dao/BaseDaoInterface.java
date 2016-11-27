package com.ocean.lmh.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;

import com.ocean.lmh.base.model.vo.PaginationBean;

/**
 * mybatis 通用dao接口
 * 
 */
public interface BaseDaoInterface
{
    /**
     * 
     * 新增
     * 
     * @param sqlKey     sqlMap.xml文件中的select标签id
     * @param obj 需要插入的对象
     * @return  
     * @exception/throws [异常类型] [异常说明]
     */
    
    boolean insertData(String sqlKey, Object obj);

    /**
     * 
     * 更新
     * 
     * @param sqlKey     sqlMap.xml文件中的select标签id
     * @param obj     需要更新的对象
     * @return  
     * @exception/throws [异常类型] [异常说明]
     */
    int updateData(String sqlKey, Object obj);

    /**
     * 
     * 删除
     * 
     * @param sqlKey     sqlMap.xml文件中的select标签id
     * @param obj     需要删除的对象
     * @return  
     * @exception/throws [异常类型] [异常说明]
     */
    int deleteData(String sqlKey, Object obj);

    /**
     * 
     * 查询一条记录
     * 
     * @param sqlKey     sqlMap.xml文件中的select标签id
     * @param obj     需要查询的参数对象
     * @return  
     * @exception/throws [异常类型] [异常说明]
     */
    <T> T query(String sqlKey, Object obj);

    /**
     * 
     * 查询多条记录
     * 
     * @param sqlKey     sqlMap.xml文件中的select标签id
     * @param map     封装了查询参数的map对象
     * @return  
     * @exception/throws [异常类型] [异常说明]
     */
    <T> List<T> queryForList(String sqlKey,  Map<String,Object> map);

   /**
    * 
    * 分页查询
    * 
    * @param sqlKey     sqlMap.xml文件中的select标签id
    * @param map     用来存取查询的字段和对应的值
    * @param currentPage    当前页
    * @param pageSize       每页的条数
    * @return  PaginationBean
    * @exception/throws [异常类型] [异常说明]
    */
    <T> PaginationBean<T> queryForListByPage(String sqlKey, Map<String,Object> map, int currentPage,int pageSize);

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
    <T> List<BatchResult> batchInsert(String sqlKey, List<T> list);

    /**
     * 组装in语句查询数据
     */
    <T> List<T> queryByIn(String sqlKey, Object obj);
    
    <T> int batchDelete(String sqlKey, List<T> list);
    
    <T> List<BatchResult> batchUpdate(String sqlKey, List<T> list);
    
    /**
     * 分页查询
     * @param sqlKey            sqlMap.xml文件中的select标签id
     * @param map               用来存取查询的字段和对应的值,map内要有key="page",value="PaginationBean"的键值对
     * @return  PaginationBean  封装了返回结果和参数的PaginationBean对象
     */
    <T> PaginationBean<T> queryForListPageByMap(String sqlKey,Map<String, Object> map);
    
    
    <T> Map<String,Object> queryForMap(String sqlKey, Object parameterObject,  
            String keyProp);
    
    /**
     * 
     * 返回一个map结果集包含所有记录
     * @param <K> 结果集的key类型
     * @param <V> 结果集的封装对象
     * @param sqlKey 查询语句
     * @param paramMap 查询参数 
     * @param resultMap 结果集
     * @param mapKey 结果集键值
     * @throws
     */
    public <K,V> void queryMap(String sqlKey, Map<String, Object> paramMap,Map<K,V> resultMap,String mapKey);
    
}
