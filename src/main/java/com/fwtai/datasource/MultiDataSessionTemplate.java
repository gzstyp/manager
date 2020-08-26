package com.fwtai.datasource;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * 动态代理SqlSessionTemplate,在执行SqlSessionTemplate操作数据库方法之前，
 * 根据方法名动态判断是发往主库还是从库,如果方法是在spring的事务中，则跳过此环节
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年5月14日 下午4:27:25
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
*/
public final class MultiDataSessionTemplate implements SqlSession{
	
	private static final String SELECT = "select";
	
	private static final String INSERT = "insert";
	
	private static final String DELETE = "delete";
	
	private static final String UPDATE = "update";

	private SqlSessionTemplate sqlSessionTemplate;

	private final SqlSession sqlSessionProxy;
	
	public MultiDataSessionTemplate(final SqlSessionTemplate sqlSessionTemplate){
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.sqlSessionProxy = (SqlSession) newProxyInstance(SqlSessionFactory.class.getClassLoader(),new Class[] {SqlSession.class}, new SqlSessionInterceptor());
	}

	/**方法拦截,此处是拦截SqlSessionTemplate的方法,进行读写分离*/
	private class SqlSessionInterceptor implements InvocationHandler{
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			try {
				final boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
				if (synchronizationActive){
					return method.invoke(sqlSessionTemplate,args);/*在spring的事务中，不做任何处理*/
				} else {
					String methodName = method.getName();
					if (methodName.startsWith(SELECT)){
						DataSourceHolder.setSlave();/*获取读集群的数据源*/
					} else if (methodName.startsWith(INSERT) || methodName.startsWith(UPDATE) || methodName.startsWith(DELETE)){
						DataSourceHolder.setMaster();/*获取主库数据源*/
					}
					final Object result = method.invoke(sqlSessionTemplate, args);
					DataSourceHolder.clearDataSource();/*清理工作*/
					return result;
				}
			} catch (Exception e){
				throw e;
			}
		}
	}

	@Override
	public <T> T selectOne(String statement){
		return sqlSessionProxy.selectOne(statement);
	}

	@Override
	public <T> T selectOne(String statement, Object parameter){
		return sqlSessionProxy.selectOne(statement, parameter);
	}

	@Override
	public <T> List<T> selectList(String statement){
		return sqlSessionProxy.selectList(statement);
	}

	@Override
	public <T> List<T> selectList(String statement, Object parameter){
		return sqlSessionProxy.selectList(statement, parameter);
	}

	@Override
	public <T> List<T> selectList(String statement, Object parameter, RowBounds rowBounds){
		return sqlSessionProxy.selectList(statement, parameter, rowBounds);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, String mapKey){
		return sqlSessionProxy.selectMap(statement, mapKey);
	}

	@Override
	public <K,V> Map<K, V> selectMap(String statement, Object parameter, String mapKey){
		return sqlSessionProxy.selectMap(statement, parameter, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds){
		return sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
	}

	@Override
	public void select(String statement, Object parameter, ResultHandler handler){
		sqlSessionProxy.select(statement, parameter, handler);
	}

	@Override
	public void select(String statement, ResultHandler handler){
		sqlSessionProxy.select(statement, handler);
	}

	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler){
		sqlSessionProxy.select(statement, parameter, rowBounds, handler);
	}

	@Override
	public int insert(String statement){
		return sqlSessionProxy.insert(statement);
	}

	@Override
	public int insert(String statement, Object parameter){
		return sqlSessionProxy.insert(statement, parameter);
	}

	@Override
	public int update(String statement){
		return sqlSessionProxy.update(statement);
	}

	@Override
	public int update(String statement, Object parameter){
		return sqlSessionProxy.update(statement, parameter);
	}

	@Override
	public int delete(String statement){
		return sqlSessionProxy.delete(statement);
	}

	@Override
	public int delete(String statement, Object parameter){
		return sqlSessionProxy.delete(statement, parameter);
	}

	@Override
	public void commit(){
		sqlSessionProxy.commit();
	}

	@Override
	public void commit(boolean force){
		sqlSessionProxy.commit(force);
	}

	@Override
	public void rollback(){
		sqlSessionProxy.rollback();
	}

	@Override
	public void rollback(boolean force){
		sqlSessionProxy.rollback(force);
	}

	@Override
	public List<BatchResult> flushStatements(){
		return sqlSessionProxy.flushStatements();
	}

	public void close(){
		sqlSessionProxy.close();
	}

	public void clearCache(){
		sqlSessionProxy.clearCache();
	}

	public Configuration getConfiguration(){
		return sqlSessionProxy.getConfiguration();
	}

	public <T> T getMapper(Class<T> type){
		return sqlSessionProxy.getMapper(type);
	}

	public Connection getConnection(){
		return sqlSessionProxy.getConnection();
	}

	@Override
	public <T> Cursor<T> selectCursor(final String sqlMapId){
		return sqlSessionProxy.selectCursor(sqlMapId);
	}

	@Override
	public <T> Cursor<T> selectCursor(final String sqlMapId, final Object params){
		return sqlSessionProxy.selectCursor(sqlMapId, params);
	}

	@Override
	public <T> Cursor<T> selectCursor(final String sqlMapId, final Object params, RowBounds rowBounds){
		return sqlSessionProxy.selectCursor(sqlMapId, params, rowBounds);
	}
}