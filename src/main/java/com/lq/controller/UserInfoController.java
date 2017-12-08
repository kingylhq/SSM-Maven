package com.lq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.lq.model.UserInfo;
import com.lq.model.page.PageInfo;
import com.lq.service.system.interfaces.IUserInfoService;

/**
 * 用戶信息管理Controller
 * 
 * @author liqian 2017年12月7日15:03:49
 */
@Controller
@RequestMapping("userInfo")
public class UserInfoController {

	@Resource(name = "userInfoService")
	private IUserInfoService userInfoService;

	/**
	 * 分页查询用户信息
	 * 
	 * @param request
	 * @param response
	 * @param pageInfo
	 * @param userInfo
	 * @return http://localhost:520/mymaven/userInfo/selectAllByPage
	 * 可以在@RequestMapping里面添加  method = RequestMethod.GET, produces = "text/json;charset=UTF-8"
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "selectAllByPage")
	@ResponseBody
	public String selectAllByPage(HttpServletRequest request,
			HttpServletResponse response, PageInfo<UserInfo> pageInfo,
			UserInfo userInfo) {

		JSONArray jSONArray = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageInfo);
		map.put("obj", userInfo);

		List<UserInfo> userList = new ArrayList<UserInfo>();
		userList = userInfoService.selectAllByPage(map);
		System.out.println("userList返回结果大小" + userList.size());
		// 查询结果放入行中
		pageInfo.setRows(userList);

		// if (CollectionUtils.isNotEmpty(userList)) {
		// for (UserInfo ui : userList) {
		// System.out.println("用户登录名称：" + ui.getUserLonginName());
		// }
		// } else {
		// System.out.println("数据为空");
		// }
		String returnStr = jSONArray.toJSONString(pageInfo).toString();
		return returnStr;
	}
}
