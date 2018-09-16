package timespider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Jwxt {
	private static String stuNumber = "";
	private static String stuName = "";
	private static String Cookie = "";
	private static String secretCodeUrl1 = "https://www.baidu.com/";
	private static String secretCodeUrl = "http://222.188.0.101/loginAction.do";
	private static String loginUrl = "http://222.188.0.101/loginAction.do";
	private static String queryStuBXQGradeUrl = "http://222.188.0.101/bxqcjcxAction.do";
	private static String queryStuGradeUrl="http://222.188.0.101/gradeLnAllAction.do?type=ln&oper=sxinfo&lnsxdm=001";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			login("1560704118", "04282914");
			System.out.println();
			queryStuGrade();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String toString(String xh,String pwd){
		String grade="";
		try {
			System.out.println("登录中...");
			login(xh,pwd);
			grade=queryStuGrade();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuName+"\n<br>"+grade;
	}
	public static boolean login(String stuNumber, String password)
			throws UnsupportedOperationException, Exception {
		stuName="";
		
		HttpGet secretCodeGet = new HttpGet(secretCodeUrl);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse responseSecret = client.execute(secretCodeGet);
		// 获取返回的Cookie
		Cookie = responseSecret.getFirstHeader("Set-Cookie").getValue().split(";")[0];
		//System.out.println("Cookie:" + Cookie);

		HttpPost loginPost = new HttpPost(loginUrl);// 创建登录的Post请求
		loginPost.setHeader("Cookie", Cookie);// 带上第一次请求的Cookie
		loginPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); 

		loginPost.setHeader("Host", "222.188.0.101");
		loginPost.setHeader("Referer", "http://222.188.0.101/logout.do");
		List<NameValuePair> nameValuePairLogin = new ArrayList<NameValuePair>();// 封装Post提交参数
		nameValuePairLogin.add(new BasicNameValuePair("mm",  password));//
		nameValuePairLogin.add(new BasicNameValuePair("zjh",stuNumber));// 
		//
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
				nameValuePairLogin, "gb2312");
		loginPost.setEntity(entity);
		HttpResponse responseLogin = client.execute(loginPost);
		// client1.close();
		// 第三步:判断提交数据是否成功，成功返回302
		HttpEntity result = responseLogin.getEntity();// 拿到返回的HttpResponse的"实体"
		String content = EntityUtils.toString(result);
		// 用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
		if (responseLogin.getStatusLine().getStatusCode() == 200) {
			// 如果提交成功，带着Cookie请求重定向的main页面，并获取学生姓名
			//System.out.println("登录成功");
			HttpGet mainGet = new HttpGet(
					"http://222.188.0.101/xjInfoAction.do?oper=xjxx");
			mainGet.setHeader("Cookie", Cookie);
			mainGet.setHeader("Host", "222.188.0.101");
			mainGet.setHeader("Referer", "http://222.188.0.101/menu/s_menu.jsp");
			HttpResponse responseMain = client.execute(mainGet);
			InputStream is = responseMain.getEntity().getContent();
			String html = "";
			try {
				html = IOUtils.getHtml(is, "GB2312");
			    System.out.println(html);
			} catch (Exception e) {
				System.out.println("解析html失败！");
				e.printStackTrace();
			}
			Elements eleGrade =Jsoup.parse(html).getElementsByClass("titleTop3").select("td");
			//System.out.println("登录成功！欢迎您!" );
			for (int j = 1; j < 5; j++)
				stuName+=eleGrade.get(j).text() + " ";
				//System.out.print(eleGrade.get(j).text() + " ");
			
			client.close();
			return true;
		} else {
			System.out.println("登录失败！");
			client.close();
			return false;
		}

	}
	public static String queryStuGrade()
			throws ClientProtocolException, IOException {
		String str="";
		int count=0;
		CloseableHttpClient client = HttpClients.createDefault();
		String newQueryStuGradeUrl = queryStuBXQGradeUrl ;
		//System.out.println(newQueryStuGradeUrl);
		HttpGet mainGet = new HttpGet(
				newQueryStuGradeUrl);
		mainGet.setHeader("Cookie", Cookie);
		mainGet.setHeader("Host", "222.188.0.101");
		mainGet.setHeader("Referer", "http://222.188.0.101/menu/s_menu.jsp");
		HttpResponse responseMain = client.execute(mainGet);
		InputStream is = responseMain.getEntity().getContent();
		String html = "";
		try {
			html = IOUtils.getHtml(is, "GB2312");
//		    System.out.println(html);
		} catch (Exception e) {
			System.out.println("解析html失败！");
			e.printStackTrace();
		}
		
		Document gradeDoc = Jsoup.parse(html);
		Elements eleGrade = gradeDoc.select("td");
		// 按需求解析html<td>标签内容并输出
		int x=1;
		for (int i = 9; i < eleGrade.size(); i +=7 ) {
			x=1;
			if (i + 7 < eleGrade.size()) {
				for (int j = i; j < i+7; j++){
					if (x==7) {
						double grade=0;
						try {
							grade=Double.parseDouble(eleGrade.get(j).text().toString().trim());
						} catch (Exception e) {
							// TODO: handle exception
							grade=0;
						}
						if (grade>0) {
							count++;
							str+="    分数:"+eleGrade.get(j).text() + " ";
							//System.out.print("分数:"+eleGrade.get(j).text() + " ");
						}
						
					}else if (x==3){
						str+=eleGrade.get(j).text() + "            ";
						//System.out.print(eleGrade.get(j).text() + "           ");
					}
					x++;
				}
				//System.out.println();
				str+="\n<br>";
				
			}
		}
		str+="\n<br>"+"教务系统网址：http://222.188.0.101/";
//		System.out.println();
		//System.out.println(str);
//		System.out.println(count+"  "+MyConstants.courseCount);
		if(count==MyConstants.courseCount){
			System.out.println("已出门数："+count);
		}else{
			System.out.println("有更新！");
			MyConstants.isSend=1;
			MyConstants.courseCount=count;
		}
		client.close();
		return str;
	}

}
