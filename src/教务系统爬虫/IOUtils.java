package 教务系统爬虫;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;

public class IOUtils {
	/**
	 * 聽* 鎸囧畾缂栫爜鏍煎紡 锛屾妸杈撳叆娴佽浆鍖栦负瀛楃涓� 聽* 聽* @param is 聽* @return 聽* @throws IOException 聽
	 */
	public static String getHtml(InputStream is, String encoding)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		is.close();
		return new String(bos.toByteArray(), encoding);
	}

	/**
	 * 聽* 涓嬭浇鍥剧墖 聽* 聽* @param urlString 聽* @param filename 聽* @param savePath 聽* @throws
	 * Exception 聽
	 */
	public static void download(String urlString, String filename,
			String savePath) throws Exception {
		// 鏋勯�燯RL
		URL url = new URL(urlString);
		// 鎵撳紑杩炴帴
		URLConnection con = url.openConnection();
		// 璁剧疆璇锋眰瓒呮椂涓�5s
		con.setConnectTimeout(5 * 1000);
		// 杈撳叆娴�
		InputStream is = con.getInputStream();

		// 1K鐨勬暟鎹紦鍐�
		byte[] bs = new byte[1024];
		// 璇诲彇鍒扮殑鏁版嵁闀垮害
		int len;
		// 杈撳嚭鐨勬枃浠舵祦
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 寮�濮嬭鍙�
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 瀹屾瘯锛屽叧闂墍鏈夐摼鎺�
		os.close();
		is.close();
	}

	/**
	 * 聽* 鍥剧墖瑁佸壀宸ュ叿绫� 聽* 聽* @param src 聽* @param dest 聽* @param x 聽* @param y 聽* @param
	 * w 聽* @param h 聽* @throws IOException 聽
	 */
	public static void cutImage(String src, String dest, int x, int y, int w,
			int h) throws IOException {
		Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));
		in.close();

	}

	/**
	 * 聽* 鍒ゆ柇瀛楃缂栫爜闆� 聽* 聽* @param str 聽* @return 聽
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "鏈煡";
	}

	/**
	 * 聽* 鎶婅緭鍏ユ祦杞崲鎴愬浘鐗�---銆嬭幏鍙栭獙璇佺爜 聽* 聽* @param is 聽* @param filename 聽* @param
	 * savePath 聽* @throws Exception 聽
	 */
	public static void getSecret(InputStream is, String filename,
			String savePath) throws Exception {
		// 1K鐨勬暟鎹紦鍐�
		byte[] bs = new byte[1024];
		// 璇诲彇鍒扮殑鏁版嵁闀垮害
		int len;
		// 杈撳嚭鐨勬枃浠舵祦
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 寮�濮嬭鍙�
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 瀹屾瘯锛屽叧闂墍鏈夐摼鎺�
		os.close();
		is.close();
	}

	/**
	 * 聽* 鑾峰彇闅愯棌瀛楁鐨刜_VIEWSTATE鍊� 聽* 聽* @param url 聽* @param cookie 聽* @param
	 * referer 聽* @return 聽* @throws UnsupportedOperationException 聽* @throws
	 * ClientProtocolException 聽* @throws IOException 聽
	 */
	public static String getViewState(String url, String cookie, String referer)
			throws UnsupportedOperationException, ClientProtocolException,
			IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet getViewState = new HttpGet(url);
		getViewState.setHeader("Cookie", cookie);
		getViewState.setHeader("Referer", referer);// 璁剧疆澶翠俊鎭�
		String s = IOUtils.getHtml(client.execute(getViewState).getEntity()
				.getContent(), "GB2312");
		String viewstate = Jsoup.parse(s).select("input[name=__VIEWSTATE]")
				.val();
		client.close();
		return viewstate;
	}
}