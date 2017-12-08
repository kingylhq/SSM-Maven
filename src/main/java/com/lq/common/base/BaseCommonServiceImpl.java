package com.lq.common.base;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 公共接口实现类
 * 
 */

public class BaseCommonServiceImpl<T> implements IBaseCommonService<T> {

	//记录日志
	private  final static Logger log = (Logger) LoggerFactory.getLogger(BaseCommonServiceImpl.class);
	
	private IBaseMapper<T> baseMapper;

	@Override
	public List<T> selectAllByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseMapper.selectAllByPage(map);
	}

	@Override
	public Long getKeyId() throws Exception {
		// TODO Auto-generated method stub
		return baseMapper.getKeyId();
	}


	
	
	
 
}
