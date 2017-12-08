package com.lq.common.base;

import java.util.List;
import java.util.Map;

import com.lq.model.UserInfo;

/**
 * 公共的接口方法，其他的接口以后都可以以此来继承它
 * 
 * @author lq 2017-12-5 11:32:52
 */
public interface IBaseCommonService<T> {

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
