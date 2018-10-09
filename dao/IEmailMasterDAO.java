/**
 * @author Pratik
 *
 */
package com.erpproject.api.mail.dao;


import com.erpproject.common.Response;

import in.param.exception.ApplicationException;

@SuppressWarnings("rawtypes")
public interface IEmailMasterDAO {
	
	public Response getDataAndExportExcel() throws ApplicationException;
	public Response sendMail() throws ApplicationException;
}
