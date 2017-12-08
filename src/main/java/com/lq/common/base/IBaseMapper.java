package com.lq.common.base;

import java.util.List;
import java.util.Map;

public interface IBaseMapper<T> {
	
	/**
	 * 分页模糊查询所有数据
	 * @param map
	 * @return
	 */
	public List<T> selectAllByPage(Map<String, Object> map);
	
	/**
	 * 获取主键
	 * @return
	 * @throws Exception
	 */
	public Long getKeyId() throws Exception;

}
