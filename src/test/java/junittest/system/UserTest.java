package junittest.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.junit.Test;

import java.util.HashMap;

import com.lq.model.UserInfo;
import com.lq.model.page.PageInfo;
import com.lq.service.system.interfaces.IUserInfoService;
import com.lq.test.BaseDaoTestUtils;
import com.lq.util.company.DateUtil2;
import com.lq.util.md5.MD5Tools;

public class UserTest extends BaseDaoTestUtils {
	
	@Resource(name = "userInfoService")
	private IUserInfoService userInfoService;
	
	private static final Log log = LogFactory.getLog(UserTest.class);
	
	@Test
	public void testInsert(){
		try {
			UserInfo ui = new UserInfo();
			for(int i=0;i<30;i++){
				ui.setKeyId(new BigDecimal(userInfoService.getKeyId())); //主键编号id
				ui.setUserName("李谦"+i); //用户姓名
				ui.setUserLonginName("liqian"+i);  //用户登录名
				ui.setUserPassword(MD5Tools.getMD5("123456"));  //密码
				ui.setUserSex("男"); //性别
				ui.setUserIsEnabled(new BigDecimal(1)); //是否可用
				ui.setUserCreateDate(DateUtil2.convertDate(new Date())); //创建日期
				ui.setUserIconUploadName("既然选择了远方"+i);
				ui.setUserIconUrl("");
				ui.setUserIconSize(new BigDecimal(500+i));
				int count = userInfoService.insertSelective(ui);
				if(count > 0){
					System.out.println("添加成功"+i);
				}else{
					System.out.println("添加失败"+i);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("添加失败");
		}
	}
	
	@Test
	public void testSelectAllByPage(){
		
		Map<String,  Object> map = new HashMap<String,  Object>();
		PageInfo<UserInfo> pg = new PageInfo<UserInfo>();
		UserInfo ui = new UserInfo();
		map.put("page", pg);
		map.put("obj", ui);
		
		List<UserInfo> userList = new ArrayList<UserInfo>();
		userList = userInfoService.selectAllByPage(map);
		System.out.println("userList返回结果大小"+userList.size());
		//查询结果放入行中
		pg.setRows(userList);
		if(CollectionUtils.isNotEmpty(userList)){
			for (UserInfo userInfo : userList) {
				System.out.println("用户登录名称："+userInfo.getUserLonginName());
			}
		}else{
			System.out.println("数据为空");
		}
		
	}


}
