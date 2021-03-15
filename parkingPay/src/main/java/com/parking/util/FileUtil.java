

package com.parking.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;

/**
 *    文件 操作 工具类  
 */


public class FileUtil {

	//@Autowired
	ResourceLoader resourceLoader;
	
	/**
	 * 
	 * creatBocHtmlFile: 操作 文件工具类 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param bocHtmlStr  字符流
	 * @param  @param pathHtml  创建  html文件 
	 * @param  @throws IOException    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public synchronized static void creatBocHtmlFile(String bocHtmlStr, String pathHtml) throws IOException {
		  File fi = null;
		  FileOutputStream  fos = null;
		  try {
			 fi = new File(pathHtml);
			 if (fi.exists()) {
				 fi.mkdirs();
			 }
			 fi.createNewFile();
			 fos = new FileOutputStream(fi);
			 String bochtml = bocHtmlStr.replace("// alert(navigator.userAgent);", "");
			 byte[] by =   bochtml.getBytes("UTF-8");
			 int b= by.length;  //是字节的长度，不是字符串的长度
		     fos.write(by, 0, b);
		     fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		  finally {
			if (null != fos) {
				fos.close();
			}
		  }
	}
	/**
	 * 
	 * getBozReturnFile: 读 取 本地 数据 html 数据
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param path
	 * @param  @return
	 * @param  @throws FileNotFoundException    设定文件
	 * @return String    DOM对象
	 * @throws IOException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public synchronized static String getBozReturnFile (String path) throws IOException {
		byte[] htmlText = null;
		InputStream is = null;
		try {
			File file = new File(path);
		    is = new FileInputStream(file);
			htmlText = read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				is.close();
			}
		}
		return new String(htmlText);
	}
	
	public static byte[] read(InputStream inputStream) throws IOException {

		byte[] temp = new byte[1024];
		int length = -1;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while ((length = inputStream.read(temp)) != -1) {
			outputStream.write(temp, 0, length);
		}
		byte[] data = outputStream.toByteArray();
		inputStream.close();
		outputStream.close();
		return outputStream.toByteArray();
	}

	/**
	 * templates 读取本地 资源下文件
	 * @param htmlName
	 * @return
	 * @throws IOException
	 */
	public synchronized static String readResourceFile(String htmlName) throws IOException {
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream is = null;
		String data = "";
		try {
			ClassPathResource resource = new ClassPathResource("templates/"+htmlName+".html");
			//Resource resource = new ClassPathResource("templates/"+htmlName);
			is = resource.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String tempStr = "";
			while((tempStr = br.readLine()) != null) {
				data += tempStr;
			}
			System.out.println("读取文件："+data);
		} catch (Exception e) {
             e.printStackTrace();
             System.out.println("读取文件异常："+htmlName);
             return  data;
		} finally {
			br.close();
			isr.close();
			is.close();
		}
		return data;
	}

	public void testReaderFile() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:resource.properties");
		InputStream is = resource.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String data = null;
		while((data = br.readLine()) != null) {
			System.out.println(data);
		}
		br.close();
		isr.close();
		is.close();
	}

}

