package com.lq.util.company;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 资金头寸DateUtils
 * @author liqian
 * 2017年12月1日11:30:23
 *
 */
public class DateUtil2 {

	
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	
	
    public static String dateToStr(Date date){
	DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	return format.format(date);

    }
    public static String timeToStr(Date date){
	DateFormat format = new SimpleDateFormat("HHmmss");
	
	return format.format(date);

    }
    
    /**   
	* 获取指定月份的第一天   
	* @param p_strDate 指定月份  
	* @param p_formate 日期格式  
	* @return String 时间字符串  
	* @author zhuqx  
	* @Date:   2006-10-31  
	*/  
	public static String getDateOfMonthBegin( String p_strDate, String p_format ) throws ParseException {   
	   java.util.Date date = toUtilDateFromStrDateByFormat( p_strDate,p_format );   
	   return toStrDateFromUtilDateByFormat( date,"yyyy-MM" ) + "-01";   
	}
	
	//-------------------------------日期类型转换---------------------------------------------------------------------------   
	/**  
	* 字符型日期转化util.Date型日期  
	* @Param:p_strDate 字符型日期   
	* @param p_format 格式:"yyyy-MM-dd" / "yyyy-MM-dd hh:mm:ss"  
	 * @throws ParseException 
	* @Return:java.util.Date util.Date型日期  
	* @Throws: ParseException  
	* @Author: hexu  
	* @Date:     
	*/  
	public static java.util.Date toUtilDateFromStrDateByFormat( String p_strDate, String p_format ) throws ParseException   
	    {   
	   java.util.Date l_date = null;   
	   java.text.DateFormat df = new java.text.SimpleDateFormat( p_format );   
	   if ( p_strDate != null && ( !"".equals( p_strDate ) ) && p_format != null && ( !"".equals( p_format ) ) ) {   
	    l_date = df.parse( p_strDate );   
	   }   
	   return l_date;   
	} 
	
	/**   
	* util.Date型日期转化指定格式的字符串型日期  
	* @param   p_date    Date   
	* @param   p_format String   
	* 格式1:"yyyy-MM-dd"   
	* 格式2:"yyyy-MM-dd hh:mm:ss EE"  12小时 
	* 格式3:"yyyy-MM-dd HH:mm:ss EE"  24小时 
	* 格式4:"yyyy年MM月dd日 hh:mm:ss EE" 
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写  
	* @return String   
	* @Author: hexu  
	* @Date:  
	*/  
	public static String toStrDateFromUtilDateByFormat( java.util.Date p_utilDate, String p_format ) throws ParseException {   
	   String l_result = "";   
	   if ( p_utilDate != null ) {   
	    SimpleDateFormat sdf = new SimpleDateFormat( p_format );   
	    l_result = sdf.format( p_utilDate );   
	   }   
	   return l_result;   
	} 
	
	/**
	 * 转换时间
	 * @param date
	 * @return
	 */
	public static String convertDate(String date)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.format(sf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 转换时间
	 * @param date
	 * @return
	 */
	public static String convertDate(Date date)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sf.format(date);
	}
	
	/**
	 * 转换时间
	 * @param date
	 * @return
	 */
	public static Date parseDate(Date date)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(sf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 转换时间
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
	
	
	
	
	/**
	 * 增加或减少天数.
	 * @return
	 */
	public static Date addDay(Date date, int num)
	{
		Calendar startDT = Calendar.getInstance();
		
		startDT.setTime(date);
		
		startDT.add(Calendar.DAY_OF_MONTH, num);
		
		return startDT.getTime();
	}
	
	
	//java入口
    public static void main(String[] args) {
    	
    	System.out.println("");
	}
}