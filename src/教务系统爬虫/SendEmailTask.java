package ����ϵͳ����;

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
    	System.out.println("************ˢ��*******************");
        System.out.println("��ȡ������..."); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
        Jwxt jwxt=new Jwxt();
        String str=jwxt.toString();
        System.out.println(str);
        float a=3;
        try {
        	if(MyConstants.isSend==1){
        		MyConstants.isSend=0;
        		sendEmail("�ι�-�����³ɼ�����",str);
        	}
        	else
        		System.out.println("�޸��£�������");

        	System.out.println("*********************************");
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
    public void sendEmail(String title,String content)throws MessagingException, IOException{
    	System.out.println("��ȡ��ɣ�������..."); 
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
         * ����ռ��������ַ�����
         * 1,�������(���˷���)����setRecipient(str);����String����
         * 2,�������(Ⱥ��)����setRecipients(list);����list��������
         * 3,�������(Ⱥ��)����setRecipients(sb);����StringBuffer����
         */
        List<String> list = new ArrayList<String>();
        list.add("1341156974@qq.com");
        //list.add("***92@sina.cn");
        mail.setRecipients(list);
       
        mail.setSubject(title);

        mail.setDate(new Date());
        mail.setFrom("�������С����");
        mail.setContent(content, "text/html; charset=UTF-8");
        
        System.out.println(mail.sendMessage());
    }
}
