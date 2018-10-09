package com.erpproject.api.mail.service;

import java.math.BigInteger;

import com.erpproject.common.Response;

/**
 * @author Pratik
 *
 */
import in.param.exception.ApplicationException;

public interface IEmailService {
	
	public Response getDataAndExportExcel() throws ApplicationException;
	public Response sendMail() throws ApplicationException;

}
