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
    	System.out.println("************�ι��ɼ�ˢ��*******************");
        System.out.println("��ȡ������..."); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
        Jwxt jwxt=new Jwxt();
        String str=jwxt.toString("1560704118", "04282914");
        //System.out.println(str);
        float a=3;
        try {
        	if(MyConstants.isSend==1){
        		//�ȷ����ҵ�
        		sendEmail("�ι��ɼ�����",str,"1341156974@qq.com");
        		
        		//���ĵ�
        		List<StuInfo> list = new ArrayList<StuInfo>();
        		StuInfo info1=new StuInfo();
        		info1.setEmail("hugh.dong@outlook.com");
        		info1.setXh("1560704106");
        		info1.setPwd("01035219");
        		list.add(info1);
//        		
        		StuInfo info2=new StuInfo();
        		info2.setEmail("2824965514@qq.com");
        		info2.setXh("1560704204");
        		info2.setPwd("09102824");
        		list.add(info2);
//        		
        		StuInfo info3=new StuInfo();
        		info3.setEmail("139023996@qq.com");
        		info3.setXh("1560704202");
        		info3.setPwd("11097960");
        		list.add(info3);
        		
        		StuInfo info4=new StuInfo();
        		info4.setEmail("857772332@qq.com");
        		info4.setXh("1560704130");
        		info4.setPwd("10136123");
        		list.add(info4);
        		
        		StuInfo info5=new StuInfo();
        		info5.setEmail("2296498609@qq.com");
        		info5.setXh("1560704203");
        		info5.setPwd("07254967");
        		list.add(info5);
        		
        		for (int i = 0; i < list.size(); i++) {
        			StuInfo info=list.get(i);
                    str=jwxt.toString(info.getXh(),info.getPwd());
                    sendEmail("�ι��ɼ�����",str,info.getEmail());
                    //sendEmail("�ι��ɼ�����",str,"1341156974@qq.com");
				}
        		MyConstants.isSend=0;
        		
        	}
        	else
        		System.out.println("�޸��£�������");

        	System.out.println("*****************************************");
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }  
    public void sendEmail(String title,String content,String email)throws MessagingException, IOException{
    	System.out.println("��ȡ��ɣ�����:"+email+"��..."); 
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
        //list.add("1341156974@qq.com");
        list.add(email);
//        list.add("hugh.dong@outlook.com");
        mail.setRecipients(list);
       
        mail.setSubject(title);

        mail.setDate(new Date());
        mail.setFrom("�������С����");
        mail.setContent(content, "text/html; charset=UTF-8");
        
        System.out.println(mail.sendMessage());
    }
}
