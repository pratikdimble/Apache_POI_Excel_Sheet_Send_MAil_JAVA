package com.erpproject.api.mail.service;

import java.math.BigInteger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erpproject.api.mail.dao.IEmailMasterDAO;
import com.erpproject.common.Response;

import in.param.exception.ApplicationException;
/**
 * @author Pratik
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EmailServiceImpl implements IEmailService {
	
	@Autowired
	IEmailMasterDAO emailMasterDAO;
	
	@Override
	public Response getDataAndExportExcel() throws ApplicationException {
		return emailMasterDAO.getDataAndExportExcel();
	}
	
	@Override
	public Response sendMail() throws ApplicationException{
		return emailMasterDAO.sendMail();
	}
}
