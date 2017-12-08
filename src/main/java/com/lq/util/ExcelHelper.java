//package com.lq.util;
//
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//
//
//import com.active.logger.LogWriter;
//
//import jxl.Workbook;
//import jxl.format.Colour;
//import jxl.format.UnderlineStyle;
//import jxl.write.DateFormat;
//import jxl.write.Label;
//import jxl.write.Number;
//import jxl.write.WritableCellFormat;
//import jxl.write.WritableFont;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//public class ExcelHelper {
//
//	private static int sheetSize = 60000;
//	/**
//	 * 导出Excel New
//	 * 
//	 * @param response
//	 * @param list
//	 * @return
//	 */
//	public static boolean exportToExcel( HttpServletResponse response, String fileTitle, ArrayList<String> titleList, ArrayList<Map<String, Object>> list ) {
//		try {
//			OutputStream os = null;
//			os = response.getOutputStream();// 取得输出�?
//			response.reset();// 清空输出�?
//			String fileName = URLEncoder.encode(fileTitle, "UTF-8");
//			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件�?
//														// fine.xls是要保存的文件名
//			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			String tmptitle = fileTitle; // 标题
//
//			WritableFont wfont = new WritableFont(WritableFont.ARIAL , 16 , WritableFont.BOLD , false , UnderlineStyle.NO_UNDERLINE , Colour.BLACK);
//			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
//			wcfFC.setBackground(Colour.WHITE);
//
//			DateFormat df = new DateFormat("yyyy-MM-dd");
//			
//			int sheetNum = 0;
//			if (list.size() % sheetSize != 0) {
//				sheetNum = list.size() / sheetSize + 1;
//			} else {
//				sheetNum = list.size() / sheetSize;
//			}
//
//			for (int a = 0; a < sheetNum; a++) {
//				WritableSheet wsheet = wbook.createSheet(tmptitle + String.valueOf(a+1), a+1); // sheet名称
//				wsheet.addCell(new Label(0 , 0 , tmptitle , wcfFC));
//				wfont = new jxl.write.WritableFont(WritableFont.ARIAL , 14 , WritableFont.BOLD , false , UnderlineStyle.NO_UNDERLINE ,
//						Colour.BLACK);
//				wcfFC = new WritableCellFormat(wfont);
//
//				for (int iii = 0; iii < titleList.size(); iii++) {
//					wsheet.addCell(new Label(iii , 2 , titleList.get(iii)));
//				}
//				for (int i = a * sheetSize; i < (a + 1) * sheetSize && i < list.size(); i++) {
//					for (int ii = 0; ii < titleList.size(); ii++) {
//						Double dValue = 0.0;
//						try {
//							if (list.get(i).get(String.valueOf(ii)).toString().equals("0"))
//								dValue = 0.0;
//							else
//								dValue = Double.parseDouble(list.get(i).get(String.valueOf(ii)).toString());
//						} catch (Exception ex) {
//							dValue = -1.0;
//						}
//
//						if (dValue >= 0) {
//							Number num = new Number(ii , i - (a * sheetSize) + 3 , dValue);
//							wsheet.addCell(num);
//						} else if (DateHelper.IsDate(list.get(i).get(String.valueOf(ii)).toString())) {
//							jxl.write.DateTime cell = new jxl.write.DateTime(ii , i - (a * sheetSize) + 3 ,
//									DateHelper.StringToDate(list.get(i).get(String.valueOf(ii)).toString(), "yyyy-MM-dd") ,
//									new WritableCellFormat(df));
//							wsheet.addCell(cell);
//						} else {
//							wsheet.addCell(new Label(ii , i - (a * sheetSize) + 3 + 3 , list.get(i).get(String.valueOf(ii))
//									.toString()));
//						}
//					}
//				}
//			}
//			// 主体内容生成结束
//			wbook.write(); // 写入文件
//			wbook.close();
//			os.close(); // 关闭�?
//			return true;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//	/**
//	 * 导出Excel New
//	 * 
//	 * @param response
//	 * @param list
//	 * @return
//	 */
//	public static boolean exportToExcelToString( HttpServletResponse response, String fileTitle, ArrayList<String> titleList,
//			ArrayList<Map<String, Object>> list ) {
//		try {
//			OutputStream os = null;
//			os = response.getOutputStream();// 取得输出�?
//			response.reset();// 清空输出�?
//			String fileName = URLEncoder.encode(fileTitle, "UTF-8");
//			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件�?
//														// fine.xls是要保存的文件名
//			response.setContentType("application/msexcel;charset=UTF-8");// 定义输出类型
//			WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
//			String tmptitle = fileTitle; // 标题
//
//			WritableFont wfont = new WritableFont(WritableFont.ARIAL , 16 , WritableFont.BOLD , false , UnderlineStyle.NO_UNDERLINE , Colour.BLACK);
//			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
//			wcfFC.setBackground(Colour.WHITE);
//
//			DateFormat df = new DateFormat("yyyy-MM-dd");
//			
//			int sheetNum = 0;
//			if (list.size() % sheetSize != 0) {
//				sheetNum = list.size() / sheetSize + 1;
//			} else {
//				sheetNum = list.size() / sheetSize;
//			}
//			for (int a = 0; a < sheetNum; a++) {
//				WritableSheet wsheet = wbook.createSheet(tmptitle + String.valueOf(a+1), a+1); // sheet名称
//				wsheet.addCell(new Label(0 , 0 , tmptitle , wcfFC));
//				wfont = new jxl.write.WritableFont(WritableFont.ARIAL , 14 , WritableFont.BOLD , false , UnderlineStyle.NO_UNDERLINE ,
//						Colour.BLACK);
//				wcfFC = new WritableCellFormat(wfont);
//
//				for (int iii = 0; iii < titleList.size(); iii++) {
//					wsheet.addCell(new Label(iii , 2 , titleList.get(iii)));
//				}
//				for (int i = a * sheetSize; i < (a + 1) * sheetSize && i < list.size(); i++) {
//					for (int ii = 0; ii < titleList.size(); ii++) {
//						if (DateHelper.IsDate(list.get(i).get(String.valueOf(ii)).toString())) {
//							jxl.write.DateTime cell = new jxl.write.DateTime(ii , i - (a * sheetSize) + 3 ,
//									DateHelper.StringToDate(list.get(i).get(String.valueOf(ii)).toString(), "yyyy-MM-dd") ,
//									new WritableCellFormat(df));
//							wsheet.addCell(cell);
//						} else {
//							wsheet.addCell(new Label(ii , i - (a * sheetSize) + 3 , list.get(i).get(String.valueOf(ii)).toString()));
//						}
//						
//						if(isNum(list.get(i).get(String.valueOf(ii)).toString())){
//							wfont = new jxl.write.WritableFont(WritableFont.createFont("宋体") , 10 , WritableFont.NO_BOLD , false , UnderlineStyle.NO_UNDERLINE ,
//									Colour.BLACK);
//							wcfFC = new WritableCellFormat(wfont);
//							jxl.write.Number  cell=new Number(ii, i-(a*sheetSize)+3, Double.parseDouble(list.get(i).get(String.valueOf(ii)).toString()),wcfFC);
//						    wsheet.addCell(cell);
//						}else {
//							wsheet.addCell(new Label(ii , i - (a * sheetSize) + 3 , list.get(i).get(String.valueOf(ii)).toString()));
//						}
//					}
//				}
//			}
//			// 主体内容生成结束
//			wbook.write(); // 写入文件
//			wbook.close();
//			os.close(); // 关闭�?
//			return true;
//
//		} catch (Exception e) {
//			LogWriter.error("����"+  e.getMessage());
//			e.printStackTrace();
//			return false;
//		}
//	}
//	
//	 public static boolean isNum(String num){
//		  if(num==null||num.trim().length()==0) return false;
//		  try{
//			  Double.parseDouble(num);
//		  }catch(Exception e){
//		   return false;
//		  }
//		  return true;
//		 }
//}
