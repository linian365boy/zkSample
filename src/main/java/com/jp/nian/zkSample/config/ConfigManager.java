package com.jp.nian.zkSample.config;

import org.I0Itec.zkclient.ZkClient;

public class ConfigManager {
	
	private FtpConfig ftpConfig;
	
	public void loadConfigFromDb(){
		//query from db
		//TODO
		ftpConfig = new FtpConfig(21, "192.168.1.1", "test", "123456");
	}
	
	/**
     * 模拟更新DB中的配置
     *
     * @param port
     * @param host
     * @param user
     * @param password
     */
    public void updateFtpConfigToDB(int port, String host, String user, String password) {
        if (ftpConfig == null) {
            ftpConfig = new FtpConfig();
        }
        ftpConfig.setPort(port);
        ftpConfig.setHost(host);
        ftpConfig.setUser(user);
        ftpConfig.setPassword(password);
        //write to db...
        //TODO...
    }
    
    /**
     * 将配置同步到ZK
     */
    public void syncFtpConfigToZk() {
        ZkClient zk = ZkUtil.getZkClient();
        if (!zk.exists(ZkUtil.FTP_CONFIG_NODE_NAME)) {
            zk.createPersistent(ZkUtil.FTP_CONFIG_NODE_NAME, true);
        }
        zk.writeData(ZkUtil.FTP_CONFIG_NODE_NAME, ftpConfig);
        zk.close();
    }
    
    
}
