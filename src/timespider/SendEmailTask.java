package timespider;

import java.text.SimpleDateFormat;
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
    	System.out.println("************盐工成绩刷新*******************");
        System.out.println("获取数据中..."); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        Jwxt jwxt=new Jwxt();
        String str=jwxt.toString("", "");
        //System.out.println(str);
        float a=3;
        try {
        	if(MyConstants.isSend==1){
        		//先发送我的
        		sendEmail("盐工成绩更新",str,"1341156974@qq.com");
        		
        		//订阅的
        		List<StuInfo> list = new ArrayList<StuInfo>();
        		StuInfo info1=new StuInfo();
        		info1.setEmail("hugh.dong@outlook.com");
        		info1.setXh("");
        		info1.setPwd("");
        		list.add(info1);
//        		
        		StuInfo info2=new StuInfo();
        		info2.setEmail("@qq.com");
        		info2.setXh("");
        		info2.setPwd("");
        		list.add(info2);
//        		
        		StuInfo info3=new StuInfo();
        		info3.setEmail("@qq.com");
        		info3.setXh("");
        		info3.setPwd("");
        		list.add(info3);
        		
        		StuInfo info4=new StuInfo();
        		info4.setEmail("@qq.com");
        		info4.setXh("");
        		info4.setPwd("");
        		list.add(info4);
        		
        		StuInfo info5=new StuInfo();
        		info5.setEmail("@qq.com");
        		info5.setXh("");
        		info5.setPwd("");
        		list.add(info5);
        		
        		for (int i = 0; i < list.size(); i++) {
        			StuInfo info=list.get(i);
                    str=jwxt.toString(info.getXh(),info.getPwd());
                    sendEmail("盐工成绩更新",str,info.getEmail());
                    //sendEmail("盐工成绩更新",str,"1341156974@qq.com");
				}
        		MyConstants.isSend=0;
        		
        	}
        	else
        		System.out.println("无更新，不发送");

        	System.out.println("*****************************************");
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
    public void sendEmail(String title,String content,String email)throws MessagingException, IOException{
    	System.out.println("获取完成，发送:"+email+"中..."); 
    	Map<String,String> map= new HashMap<String,String>();
        SendEmail mail = new SendEmail("@qq.com","");
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
        //list.add("1341156974@qq.com");
        list.add(email);
//        list.add("hugh.dong@outlook.com");
        mail.setRecipients(list);
       
        mail.setSubject(title);

        mail.setDate(new Date());
        mail.setFrom("沈新宇的小助手");
        mail.setContent(content, "text/html; charset=UTF-8");
        
        System.out.println(mail.sendMessage());
    }
}
