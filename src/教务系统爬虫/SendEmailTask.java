package 教务系统爬虫;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import java.util.TimerTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
public class SendEmailTask extends TimerTask {  
    public void run(){  
        System.out.println("每天定时任务启动..."); 
        System.out.println("获取今日数据中..."); 
        Jwxt jwxt=new Jwxt();
        
        String str=jwxt.toString();
//        System.out.println(str);
        float a=3;
        try {
			sendEmail("盐工定时成绩刷新",str);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
    public void sendEmail(String title,String content)throws MessagingException, IOException{
    	System.out.println("获取完成，发送中..."); 
    	Map<String,String> map= new HashMap<String,String>();
        SendEmail mail = new SendEmail("228919450@qq.com","wqedhnlvetfmbjcf");
        map.put("mail.smtp.host", "smtp.qq.com");
        map.put("mail.transport.protocol", "smtp");
        map.put("mail.smtp.host", "smtp.qq.com");
        map.put("mail.smtp.auth", "true");
        map.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        map.put("mail.smtp.port", "465");
        map.put("mail.smtp.socketFactory.port", "465");
        mail.setPros(map);
        mail.initMessage();
        /*
         * 添加收件人有三种方法：
         * 1,单人添加(单人发送)调用setRecipient(str);发送String类型
         * 2,多人添加(群发)调用setRecipients(list);发送list集合类型
         * 3,多人添加(群发)调用setRecipients(sb);发送StringBuffer类型
         */
        List<String> list = new ArrayList<String>();
        list.add("1341156974@qq.com");
        //list.add("***92@sina.cn");
        mail.setRecipients(list);
       
        mail.setSubject(title);

        mail.setDate(new Date());
        mail.setFrom("沈新宇的小助手");
        mail.setContent(content, "text/html; charset=UTF-8");
        
        System.out.println(mail.sendMessage());
    }
}
