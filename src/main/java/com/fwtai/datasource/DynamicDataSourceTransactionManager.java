package com.fwtai.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 事务管理
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年5月14日 下午4:26:28
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
*/
public final class DynamicDataSourceTransactionManager extends DataSourceTransactionManager{

	private static final long serialVersionUID = 7160082287881717832L;

	/**只读事务到从库,读写事务到主库*/
	@Override
	protected void doBegin(final Object transaction,final TransactionDefinition definition){
		boolean readOnly = definition.isReadOnly();
		if(readOnly){
			DataSourceHolder.setSlave();
		}else{
			DataSourceHolder.setMaster();
		}		
		super.doBegin(transaction, definition);
	}
	
	/**清理本地线程的数据源*/
	@Override
	protected void doCleanupAfterCompletion(final Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		DataSourceHolder.clearDataSource();
	}
}