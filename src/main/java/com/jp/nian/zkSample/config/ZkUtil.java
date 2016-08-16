package com.jp.nian.zkSample.config;

import org.I0Itec.zkclient.ZkClient;

public class ZkUtil {
	public static final String FTP_CONFIG_NODE_NAME = "/config/ftp";
	
	public static ZkClient getZkClient(){
		return new ZkClient("192.168.143.172:2181");
	}
}
