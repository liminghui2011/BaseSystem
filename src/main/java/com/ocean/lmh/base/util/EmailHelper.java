package com.ocean.lmh.base.util;

import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailHelper {
	
	/**
	 * 发送邮件
	 * 
	 * @param map 带有邮件基本信息的Map对象
	 * @param email_to 收件人邮箱地址
	 * @param mail_from 发件人邮箱地址
	 * @param message 邮件内容
	 * @param subject 邮件主题
	 * @return 成功返回true,失败返回false
	 */
	public static boolean sendEmail(Map<String,Object> map, String email_to, String mail_from, 
			String message, String subject) {
		boolean result = false;
		try {
			int counter = 0;
			String priority = "3";
			String charset = "UTF-8";
			String mailer = "WebMail 2.0";
			InternetAddress[] toAddrs = null;
			Properties props = System.getProperties();
			String smtp_server = "IPCPEMAIL";
			String SMTP_SUPPORT_TSL = "TRUE";

			Object tempValue = map.get("email.SMTP_SUPPORT_TSL");
			if (tempValue != null && !"true".equalsIgnoreCase(tempValue.toString()))
				SMTP_SUPPORT_TSL = "FALSE";
			
			Object destServer = map.get("email.SMTP_SERVER");
			if (destServer != null && AssertHelper.isNotEmptyString(destServer.toString())){
				smtp_server = destServer.toString();
			}

			InetAddress smtp = InetAddress.getByName(smtp_server);

			props.put("mail.smtp.host", smtp.getHostAddress());
			props.put("mail.smtp.connectiontimeout", "30000");
			props.put("mail.smtp.timeout", "30000");

			if ("TRUE".equals(SMTP_SUPPORT_TSL.trim().toUpperCase())) {
				props.put("mail.smtp.socketFactory.fallback", "false");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.user", map.get("mail.SMTP_USER").toString());
				props.put("mail.smtp.password", map.get("mail.SMTP_PASSWORD").toString());
				props.put("mail.smtp.starttls.enable", "true");

			} else
				props.put("mail.smtp.socketFactory.fallback", "true");

			Session session = null;
			session = Session.getInstance(props, null);
			Transport bus = session.getTransport("smtp");
			bus.connect(smtp.getHostAddress(), map.get("email.SMTP_USER").toString(), map.get("email.SMTP_PASSWORD")
					.toString());

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mail_from));
			toAddrs = InternetAddress.parse(email_to, false);
			msg.setRecipients(Message.RecipientType.TO, toAddrs);

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(message, charset);
			mp.addBodyPart(mbp1);

			msg.setContent(mp);
			String[] lang = { "UTF-8" };
			msg.setContentLanguage(lang);
			msg.setSubject(subject, charset);

			msg.setSentDate(new java.util.Date());
			msg.setHeader("X-Mailer", mailer);
			msg.setHeader("X-Priority", priority);
			msg.saveChanges();

			do {
				System.out.println("try to send email to " + email_to);
				try {
					bus.sendMessage(msg, toAddrs);
					result = true;
					System.out.println("Mail sent to " + email_to);
					counter = 4;

				} catch (Exception e) {
					result = false;
					counter++;
					Thread.sleep(1000);
				}
			} while (counter <= 3);

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} finally {
			System.out.println("setting output in Util.sendEMail :" + result);
		}
		return result;
	}

}
