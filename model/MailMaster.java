/**
 * @author Pratik
 *
 */
package com.erpproject.api.mail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;


@Entity
@Table(name="email_master", schema="public")

@NamedNativeQueries({
	@NamedNativeQuery(name="GET_SEND_TO_MAIL",query="SELECT mm.id AS \"userId\", "
			+ "mm.email_id AS \"emailId\""
			+ "FROM public.email_master mm"),
	})


public class MailMaster {
	
		@Id
		@Column(name="id")
		private Integer userId;
	
		@Column(name="email_id")
		private String emailId;

		public MailMaster() {
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
