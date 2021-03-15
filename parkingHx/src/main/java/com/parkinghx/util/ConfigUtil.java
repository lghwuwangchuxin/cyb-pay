package com.parkinghx.util;

import com.parkinghx.dto.HxFiles;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigUtil { 
	public static ResourceBundle resourceBundle =null; 

	static{
		if(resourceBundle==null){
			resourceBundle=ResourceBundle.getBundle("config");
		}
	}

	public static String getValue(String refName){
		try{
			if(refName!=null){
				String result=resourceBundle.getString(refName);
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	//渠道密码 文件
	public static String loadMerPwd(String pwdpath){
			String pwd = "";
			Properties properties = new Properties();
			// 使用InPutStream流读取properties文件
			BufferedReader bufferedReader;
			try {
				bufferedReader = new BufferedReader(new FileReader(pwdpath));
				properties.load(bufferedReader);
				pwd = properties.getProperty("pwd");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return pwd;
		}

	/**
	 * TODO:递归扫描指定文件夹下面的指定文件
     * @return ArrayList<Object>
     * @throws FileNotFoundException
     */
	public static HxFiles scanFilesWithRecursion(String folderPath) throws FileNotFoundException {
		HxFiles hxFiles = new HxFiles();
		File directory = new File(folderPath);
		if(!directory.isDirectory()){
			throw new FileNotFoundException('"' + folderPath + '"' + " input path is not a Directory");
		}
		if(directory.isDirectory()){
			File [] filelist = directory.listFiles();
			for(int i = 0; i < filelist.length; i ++){
				/**如果当前是文件夹，进入递归扫描文件夹**/
				if (filelist[i].isDirectory()) {
					/**递归扫描下面的文件夹**/
					scanFilesWithRecursion(filelist[i].getAbsolutePath());
				} else { /**非文件夹**/
					//scanFiles.add(filelist[i].getAbsolutePath());
					if (filelist[i].getName().endsWith("jks")) {
						hxFiles.setJksName(filelist[i].getName());
					} else if (filelist[i].getName().endsWith("cer")) {
						hxFiles.setCerName(filelist[i].getName());
					} else if (filelist[i].getName().endsWith("properties")) {
						hxFiles.setPropertiesName(filelist[i].getName());
					}
				}
			}
		}
		return hxFiles;
	}


}
