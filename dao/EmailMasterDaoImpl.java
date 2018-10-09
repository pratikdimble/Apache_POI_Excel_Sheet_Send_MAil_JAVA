/**
 * @author Pratik
 *
 */
package com.erpproject.api.mail.dao;




import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.dozer.DozerBeanMapper;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erpproject.api.dto.UserDto;
import com.erpproject.api.mail.dto.MailMasterDto;
import com.erpproject.common.ICommonConstants;
import com.erpproject.common.IError;
import com.erpproject.common.Response;

import in.param.exception.ApplicationException;

@Repository
@Transactional
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EmailMasterDaoImpl implements IEmailMasterDAO, ICommonConstants, IError {
	
	@Autowired
	private SessionFactory session;
	
	/**
	 * @author Pratik 
	 * 
	 * API FOR GET DATA AND EXPORT EXCEL
	 *
	 */
	
	@Override
	public Response getDataAndExportExcel() throws ApplicationException {
		
	// CALCULATE DATE BEFORE 1 DAY AND 7 DAYS 
		Calendar calBefore = Calendar.getInstance();
		calBefore.add(Calendar.DATE, -7);
		Date dateBefore7Days = calBefore.getTime();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date dateBefore1Day = cal.getTime();
		
		List<UserDto> listUserDto = session.getCurrentSession()
											.getNamedQuery("GET_USER_DETAILS_FOR_MAIL")
											.setDate("dateBefore", dateBefore7Days)
											.setDate("dateNow", dateBefore1Day)
											.setResultTransformer(Transformers.aliasToBean(UserDto.class))
											.list();
		int listLength = listUserDto.size();
		try {
			String excelFileName = "D:\\UserData.xlsx";//name of excel file

			String sheetName = "UserSheet1";//name of sheet

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(sheetName) ;
			int record  = 0;
			 // Setting style only for header
		     CellStyle style=null;
		     // Creating a font
		        HSSFFont font= workbook.createFont();
		        font.setFontHeightInPoints((short)10);
		        font.setFontName("Arial");
		        font.setColor(IndexedColors.RED.getIndex());
		        font.setBold(true);
		        font.setItalic(false);
		 
		        style=workbook.createCellStyle();
		        style.setAlignment(CellStyle.ALIGN_CENTER);
		        // Setting font to style
		        style.setFont(font);
		        
//			CODE FOR SETTING HEADERS
			   HSSFRow excelHeader = sheet.createRow(record);
				Cell cell1 = excelHeader.createCell(0);cell1.setCellValue("First Name"); cell1.setCellStyle(style);
				Cell cell2 =excelHeader.createCell(1);cell2.setCellValue("Last Name");cell2.setCellStyle(style);
				Cell cell3 =excelHeader.createCell(2);cell3.setCellValue("Email Id");cell3.setCellStyle(style);
				Cell cell4 =excelHeader.createCell(3);cell4.setCellValue("Mobile No");cell4.setCellStyle(style);
				Cell cell5 =excelHeader.createCell(4);cell5.setCellValue("Created Date");cell5.setCellStyle(style);
				
		
			        
			        // Setting cell style
			       
//				CODE FOR WRITING DATA TO SHEET FROM DTO
				record = 2;
				for (UserDto user : listUserDto) {
					HSSFRow excelRow = sheet.createRow(record++);
					excelRow.createCell(0).setCellValue(user.getUserFName());
					excelRow.createCell(1).setCellValue(user.getUserLName());
					excelRow.createCell(2).setCellValue(user.getEmailId());
					excelRow.createCell(3).setCellValue(user.getMobileNo());
					excelRow.createCell(4).setCellValue(user.getMailDate());

				}
				
			FileOutputStream fileOut = new FileOutputStream(excelFileName);
			
			//write this workbook to an Outputstream.
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
				System.out.println("UserData.xlsx written successfully"+ listLength);
			workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new Response(SUCCESS, SUCCESS_200,COMMON_SUCCESS, listUserDto, null);
	}
	/**
	 * @author Pratik 
	 * 
	 * API FOR GET DATA AND SEND MAIL
	 *
	 */
	@Override
	public Response sendMail() throws ApplicationException {
	    try {
	    	//GET THE  RECIPIENT'S EMAIL ID .
			List<MailMasterDto> list = session
					.getCurrentSession()
					.getNamedQuery("GET_SEND_TO_MAIL")
					.setResultTransformer(Transformers.aliasToBean(MailMasterDto.class))
					.list();
	       return new Response(SUCCESS, SUCCESS_200,null, list, null);
	       
	    }
	       catch (Exception e) {
		       throw new RuntimeException(e);
		    }
	}
	
}
