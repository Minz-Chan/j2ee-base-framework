package com.iisi.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;

public class vJMail {
	String host = "sm1.iet";
	String from = "sa@iisigroup.com";
	String subjectBig5 = "sa";
	String subject;
	String body;

	public boolean setReject(String to, String bodyBig5) {
		return false;
	}

	/**
	 * 發送Mail
	 * 
	 * @param to
	 *            完整Email Address
	 * @param ccstring
	 *            完整Email Address
	 * @param body
	 *            Email內容
	 * @param subject
	 *            Email主題
	 * @param from
	 *            發件人
	 * @return 發送成功或者失敗標誌
	 */
	public boolean sendMail(String[] _to, String[] _ccstring, String body,
			String subject, String from) {

		/*** 檢查是否存在抄送部分 ***/
		boolean isNoCcString = false;
		if (_ccstring.length == 0 || _ccstring[0].equalsIgnoreCase("")) {
			isNoCcString = true;
			_ccstring = new String[1];
			_ccstring[0] = "No CcString";
		}

		
		/*** 驗證發件人郵件地址 ***/
		if(!isValidEmailAddress(from))
			return false;
		
		/*** 過濾非法的郵件地址 ***/
		List<String> cc = new ArrayList<String>();
		List<String> to = new ArrayList<String>();
		for(String s: _ccstring){
			if(isValidEmailAddress(s))
				cc.add(s);
		}
		for(String s: _to){
			if(isValidEmailAddress(s))
				to.add(s);
		}
		

		/*** 發送郵件 ***/
		InternetAddress[] CC = new InternetAddress[cc.size()];
		InternetAddress[] TO = new InternetAddress[to.size()];
		boolean sessionDebug = false;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		// props.put("mail.transport.protocol","smtp");
		Session mailSession = Session.getDefaultInstance(props, null);
		mailSession.setDebug(sessionDebug);
		Message msg = new MimeMessage(mailSession);
		try {
			msg.setFrom(new InternetAddress(from));
			// InternetAddress[] address={new InternetAddress(to)};
			System.out.println("From: " + from);
			for (int j = 0; j < to.size(); j++) {
				if(isValidEmailAddress(to.get(j))){	// 當郵件地址合法時發送
					TO[j] = new InternetAddress(to.get(j));
					System.out.println("TO[" + j + "]: " + TO[j]);
				}
			}
			if (!_ccstring[0].equals("No CcString")) {
				for (int i = 0; i < cc.size(); i++) {
					if(isValidEmailAddress(cc.get(i))){ // 當郵件地址合法時發送
						CC[i] = new InternetAddress(cc.get(i));
						System.out.println("CC[" + i + "]: " + CC[i]);
					}
				}
			}
			System.out.println("Subject: " + subject);
			System.out.println("Body: " + body);
			// InternetAddress[] CCaddress={new InternetAddress(cc),new
			// InternetAddress(cc2)};
			msg.setRecipients(Message.RecipientType.TO, TO);
			if (!isNoCcString) {
				msg.addRecipients(Message.RecipientType.CC, CC);
			}

			((MimeMessage) msg).setSubject(subject, "big5");
			msg.setSentDate(new Date());
			msg.setText(body);
			System.out.println("Mail is sending");
			try {
				Transport.send(msg);
			} catch (MessagingException e) {
				// e.printStackTrace();
				System.out.println("Transport.send(msg)失敗");
				return false;
			}
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	public boolean sendMail(String[] to, String body, String subject,
			String from) {
		String cc[] = new String[1];
		cc[0] = "";
		boolean tosendMail = sendMail(to, cc, body, subject, from);
		return tosendMail;
	}
	
	/*** 驗證是否為合法的郵件地址 ***/
	public boolean isValidEmailAddress(String e){
		Pattern p =  Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		Matcher m =  p.matcher(e);
		return m.matches();
	}
	
}
