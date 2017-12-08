/**
 * Project Name:fam-core
 * File Name:StatusUtils.java
 * Package Name:com.linc.fam.util
 * Date:2015年12月12日下午2:14:47
 * Copyright @ 2010-2015 上海企垠信息科技有限公司  All Rights Reserved.
 *
 */

package com.lq.util.company;

/**
 * ClassName:StatusUtils <br/>
 * Description: TODO Description. <br/>
 * Date: 2015年12月12日 下午2:14:47 <br/>
 *
 * @author Shipeng Tang
 * @version
 * @since JDK 1.7
 * @see
 */
public class StatusConstants {
	// 交易对手状态
	public static String getArcStatusMessage(String status) {
		String message = "";
		if (ARC_ADD.equals(status)) {
			message = "保存";
		} else if (ARC_SUBMIT.equals(status)) {
			message = "提交";
		} else if (ARC_APPROVE.equals(status)) {
			message = "复核";
		} else if (ARC_REJECT.equals(status)) {
			message = "驳回";
		} else if (ARC_INVALID.equals(status)) {
			message = "失效";
		} else if (APPROVE_EDIT.equals(status)) {
			message = "复合后修改";
		} else if (APPROVE_SUBMIT.equals(status)) {
			message = "复合后提交";
		}
		return message;

	}

	public static String getStatusCode(String status) {
		String stateCode = "";
		if (status.equals("保存")) {
			stateCode = "01";
		} else if (status.equals("复核")) {
			stateCode = "02";
		} else if (status.equals("审批中")) {
			stateCode = "03";
		} else if (status.equals("完成")) {
			stateCode = "04";
		} else if (status.equals("撤销")) {
			stateCode = "05";
		} else if (status.equals("驳回")) {
			stateCode = "06";
		} else if (status.equals("结果确认")) {
			stateCode = "07";
		} else if (status.equals("撤销待复核")) {
			stateCode = "08";
		}
		return stateCode;
	}

	public static String getStatusMessage(String status) {
		String message = "";
		if (ADD.equals(status)) {
			message = "保存";
		} else if (SUBMIT.equals(status)) {
			message = "复核";
		} else if (CHECK.equals(status)) {
			message = "审批中";
		} else if (APPROVE.equals(status)) {
			message = "完成";
		} else if (REVOKE.equals(status)) {
			message = "撤销";
		} else if (REJECT.equals(status)) {
			message = "驳回";
		} else if (CONFIRM.equals(status)) {
			message = "结果确认";
		} else if (REVOKE_WAIT_APPROVE.equals(status)) {
			message = "撤销待复核";
		} else if (PRINT.equals(status)) {
			message = "打印";
		} 

		return message;
	}

	public static final String ADD = "01";
	public static final String SUBMIT = "02";
	public static final String CHECK = "03";
	public static final String APPROVE = "04";
	public static final String REVOKE = "05";
	public static final String REJECT = "06";
	public static final String CONFIRM = "07";
	public static final String REVOKE_WAIT_APPROVE = "08";
	public static final String PRINT = "09";
	public static final String AUDING = "10";

	// 交易对手状态
	public static final String ARC_ADD = "01";
	public static final String ARC_SUBMIT = "02";
	public static final String ARC_APPROVE = "03";
	public static final String ARC_REJECT = "04";
	public static final String ARC_INVALID = "05";
	public static final String APPROVE_EDIT = "06";
	public static final String APPROVE_SUBMIT = "07";
}
