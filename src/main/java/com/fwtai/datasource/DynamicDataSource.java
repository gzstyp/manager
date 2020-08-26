package com.fwtai.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据操作数据的标识动态数据源,select切换到从库,insert、update、delete切换到主库
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年5月14日 下午4:24:17
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
*/
public class DynamicDataSource extends AbstractRoutingDataSource{
	
	private AtomicInteger counter = new AtomicInteger();
	
	/**master库 dataSource数据源*/
	private DataSource master;
	
	/**从库slaves数据源*/
	private List<DataSource> slaves;
	
	@Override
	protected Object determineCurrentLookupKey(){
		return null;
	}
	
	@Override
	public void afterPropertiesSet(){
	}

	/**根据标识,获取数据源*/
	@Override
	protected DataSource determineTargetDataSource(){
		DataSource returnDataSource = null;
		if(DataSourceHolder.isMaster()){
			returnDataSource = master;
		}else if(DataSourceHolder.isSlave()){
			int count = counter.incrementAndGet();
			if(count>1000000){
				counter.set(0);
			}
			int n = slaves.size();
			int index = count%n;
			returnDataSource = slaves.get(index);
		}else{
			returnDataSource = master;
		}
		return returnDataSource;
	}

	public DataSource getMaster(){
		return master;
	}

	public void setMaster(DataSource master){
		this.master = master;
	}

	public List<DataSource> getSlaves(){
		return slaves;
	}

	public void setSlaves(List<DataSource> slaves){
		this.slaves = slaves;
	}
}