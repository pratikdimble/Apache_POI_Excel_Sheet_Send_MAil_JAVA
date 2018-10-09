/**
 * @author Pratik
 *
 */
package com.erpproject.api.mail.dto;

public class MailMasterDto {
		private Integer userId;
		private String emailId;

		public MailMasterDto() {
			super();
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getEmailId() {
			return emailId;
		}

		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
}
