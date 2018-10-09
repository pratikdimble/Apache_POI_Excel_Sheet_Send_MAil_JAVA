/**
 * @author Pratik
 *
 */
package com.erpproject.api.mail.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.erpproject.api.accounting.master.dto.GroupMasterDto;
import com.erpproject.api.controller.ExcelMailSender;
import com.erpproject.api.mail.dto.MailMasterDto;
import com.erpproject.api.mail.service.IEmailService;
import com.erpproject.common.ICommonConstants;
import com.erpproject.common.IError;
import com.erpproject.common.Response;

@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
@RequestMapping(value = "/email")
@EnableScheduling
public class EmailController implements ICommonConstants, IError {
	
	@Autowired
	IEmailService emailService;
	
	@Autowired
	ExcelMailSender excelMailSender;
	
	/**
	 * @author Pratik 
	 * 
	 * API FOR GET DATA AND EXPORT EXCEL
	 *
	 */
	@Scheduled(cron = "0 0 09 ? * MON")
    public void scheduleTaskUsingCronExpression() {
        System.out.println("Run Job " + new Date());
        System.out.println("P is running");
        Response response = null;
		try
			{
				response = emailService.getDataAndExportExcel();
				if(response.getCode().equals("200")){
					response = emailService.sendMail();
					if(response.getListObject()!=null){
						excelMailSender.sendEmail(response.getListObject());
					}
				}
			}
		catch (Exception e)
			{
				e.printStackTrace();
			}
    }
	
	
	/*@RequestMapping(value = "/getDate", method = RequestMethod.GET)
	public @ResponseBody Response getDataAndExportExcel()
		{
			Response response = null;
			try
				{
					response = emailService.getDataAndExportExcel();
					return response;
				}
			catch (Exception e)
				{
					e.printStackTrace();
					return new Response(ERROR, ERR_500, ERR_500_MESSAGE, null, null);
				}
		}*/
	
	/**
	 * @author Pratik 
	 * 
	 * API FOR GET DATA AND SEND MAIL
	 *
	 */
	/*@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public @ResponseBody Response sendMail()
		{
			Response<MailMasterDto, Integer> response = null;
			try
				{
					response = emailService.sendMail();
					if(response.getListObject()!=null){
						excelMailSender.sendEmail(response.getListObject());
					}
					return response;
				}
			catch (Exception e)
				{
					e.printStackTrace();
					return new Response(ERROR, ERR_500, ERR_500_MESSAGE, null, null);
				}
		}*/
}//controller close
