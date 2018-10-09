package com.erpproject.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.erpproject.api.mail.dto.MailMasterDto;
import com.erpproject.api.mail.model.MailMaster;


@Component
public class ExcelMailSender {
	
	@Value("${MAIL_SENDER}")
    private String mailSender;
	
	@Value("${MAIL_SENDER_PASSWORD}")
    private String password;

	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String sendEmail(List<MailMasterDto> list ) {
		 // Sender's email ID needs to be mentioned
	    String from = "email.demoeracal@gmail.com";

//	    final String username = "email.demoeracal@gmail.com";//change accordingly
//	    final String password = "email5678";//change accordingly

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com"); // for smtp credinshital
		props.put("mail.smtp.port", "587"); // for smtp credinshital

	
		// Recipient's email ID needs to be mentioned.
				List<String> mailAddressTo= new ArrayList<String>();
					for (MailMasterDto temp : list) {
							mailAddressTo.add(temp.getEmailId());
					}

					InternetAddress[] mailAddress_TO = new InternetAddress[mailAddressTo.size()];
				
	    // GET THE SESSION OBJECT.
	    Session session = Session.getInstance(props,
	       new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	             return new PasswordAuthentication(mailSender, password);
	          }
	       });

	    try {
	    	for (int i = 0; i < mailAddressTo.size(); i++) {
				mailAddress_TO[i] = new InternetAddress(mailAddressTo.get(i));
			}
	       // Create a default MimeMessage object.
	       Message message = new MimeMessage(session);

	       // Set From: header field of the header.
	       message.setFrom(new InternetAddress(from));

	       // Set To: header field of the header.
	       message.setRecipients(Message.RecipientType.TO,mailAddress_TO);
	      /* message.setRecipients(Message.RecipientType.TO,
	          InternetAddress.parse(to));*/

	       // Set Subject: header field
	       message.setSubject("New Excel Data");

	       // Create the message part
	       BodyPart messageBodyPart = new MimeBodyPart();

	       // Now set the actual message
	       messageBodyPart.setText("Please Find the Attachment");

	       // Create a multipar message
	       Multipart multipart = new MimeMultipart();

	       // Set text message part
	       multipart.addBodyPart(messageBodyPart);

	       // Part two is attachment
	       messageBodyPart = new MimeBodyPart();
	       String filename = "D:\\UserData.xlsx";
	       DataSource source =  new FileDataSource(filename);
	       messageBodyPart.setDataHandler(new DataHandler(source));
	       messageBodyPart.setFileName(filename);
	       multipart.addBodyPart(messageBodyPart);

	       // Send the complete message parts
	       message.setContent(multipart);

	       // Send message
	       Transport.send(message);

	       System.out.println("Sent message successfully....");
	       System.out.println("Mail Has Been Sent to "+mailAddressTo.size()+" Recipients...");
	       return "Sent message successfully....";
	    }
	       catch (MessagingException e) {
		       throw new RuntimeException(e);
		    }
	}
}
