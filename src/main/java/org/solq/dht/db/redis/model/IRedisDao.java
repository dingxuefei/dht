package org.solq.dht.db.redis.model;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.solq.dht.db.redis.event.IRedisEvent;
import org.springframework.data.redis.connection.DataType;

public interface IRedisDao<Key, T extends IRedisEntity> {

	///////////////////////// 事务/////////////////
	public T tx(Key key, TxCallBack<T> callBack);

	public <R> R lock(Key key, LockCallBack<R> callBack);

	// /////////////////////CUD/////////////////////
	@SuppressWarnings("unchecked")
	public void saveOrUpdateSync(T... entitys);
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(T... entitys);
	
	@SuppressWarnings("unchecked")
	public void remove( Key... keys);
	@SuppressWarnings("unchecked") 
	public void removeSync(Key... keys);

 	public void clearCache(String... keys);
 	public void clearAllCache();

 
	/////////////////////// key生命周期管理////////////////////////////////
	public boolean rename(Key oldKey, Key newKey);

	public Boolean move(Key key, int dbIndex);

	public boolean exists(Key key);

	public void expire(Key key, long timeOut, TimeUnit unit);

	public DataType type(Key key);

	// //////////////////////搜索////////////////////////////////
	public Set<String> keys(String pattern);

	public List<T> query(String pattern);
	
	public void cursor(String pattern,CursorCallBack<T> cb);
	

	public T findOne(Key key);

	public T findOneForCache(Key key);

	public void destroy();

	public void send(IRedisEvent msg, String... channels);


	// public List<T> sort(SortQuery<Key> query);
}
