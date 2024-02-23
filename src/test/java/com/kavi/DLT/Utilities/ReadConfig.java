package com.kavi.DLT.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties pro;

	public ReadConfig() {
		File xyz = new File("E:\\DA_LoadTesting\\Properties\\Config.properties");
		try {
			FileInputStream File = new FileInputStream(xyz);
			pro = new Properties();
			pro.load(File);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String readfile() {
		String url = pro.getProperty("BaseURL");
		return url;

	}
	
	public String readOpenMsg() {
		String msg = pro.getProperty("OpeningMsg");
		return msg;
	}
	
	public String readPasswordMsg() {
		String msg = pro.getProperty("RequestPasswordMsg");
		return msg;
	}
	
	public String readWelcomeMsg() {
		String msg = pro.getProperty("WelcomeMsg");
		return msg;
	}
}
