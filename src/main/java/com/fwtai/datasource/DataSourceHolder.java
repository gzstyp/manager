package com.fwtai.datasource;

import javax.sql.DataSource;

/**
 * 主从复制多源数据库的切换
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年5月14日 下午4:22:16
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
*/
public final class DataSourceHolder {

	private static final String MASTER = "master";

	private static final String SLAVE = "slave";

	private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

	private static final ThreadLocal<DataSource> masterLocal = new ThreadLocal<DataSource>();

	private static final ThreadLocal<DataSource> slaveLocal = new ThreadLocal<DataSource>();

	/**设置数据源*/
	private static void setDataSource(String dataSourceKey){
		dataSources.set(dataSourceKey);
	}

	/**获取数据源*/
	private static String getDataSource(){
		return dataSources.get();
	}

	/**标志为主库master*/
	public static void setMaster(){
		setDataSource(MASTER);
	}

	/**标志为从库slave*/
	public static void setSlave(){
		setDataSource(SLAVE);
	}
	
	/**将主库master放入threadlocal*/
	public static void setMaster(DataSource master){
		masterLocal.set(master);
	}
	
	/**将从库slave放入threadlocal*/
	public static void setSlave(DataSource slave){
		slaveLocal.set(slave);
	}
	
	public static boolean isMaster(){
		return getDataSource() == MASTER;
	}

	public static boolean isSlave(){
		return getDataSource() == SLAVE;
	}

	/**清除thread local中的数据源*/
	public static void clearDataSource(){
		dataSources.remove();
		masterLocal.remove();
		slaveLocal.remove();
	}
}