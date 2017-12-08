package com.lq.service.system.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lq.model.UserInfo;
import com.lq.repository.UserInfoMapper;
import com.lq.service.system.interfaces.IUserInfoService;

/**
 * 系统用户接口实现类
 * 
 * @author liqian 2017-12-4 10:51:50
 */

@Service("userInfoService")
public class UserInfoService 
		implements IUserInfoService {

	@Resource(name = "userInfoMapper")
	private UserInfoMapper userInfoMapper;

	

	@Override
	public List<UserInfo> selectAllByPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectAllByPage(map);
	}

	@Override
	public int deleteByPrimaryKey(BigDecimal keyId) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int insert(UserInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(UserInfo record) {
		// TODO Auto-generated method stub
		int count = 0;
		count = userInfoMapper.insertSelective(record);
		if(count > 0){
			return count;
		}else{
			return 0;
		}
	}



	@Override
	public UserInfo selectByPrimaryKey(BigDecimal keyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(UserInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(UserInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long getKeyId() throws Exception {
		// TODO Auto-generated method stub
		return userInfoMapper.getKeyId();
	}

	
}
