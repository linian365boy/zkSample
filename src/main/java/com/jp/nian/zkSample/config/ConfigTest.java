package com.jp.nian.zkSample.config;

import org.junit.Test;

/**
 * @ClassName: ConfigTest  
 * @Description: 配置中心的核心：数据从DB拿出来之后，同步到zk。
 * 				修改DB的数据，同样也要同步到zk 
 * @date: 2016年8月29日 上午11:47:24 
 * 
 * @author tanfan 
 * @version  
 * @since JDK 1.7
 */
public class ConfigTest {
	@Test
    public void testZkConfig() throws InterruptedException {
 
        ConfigManager cfgManager = new ConfigManager();
        ClientApp clientApp = new ClientApp();
 
        //模拟【配置管理中心】初始化时，从db加载配置初始参数
        cfgManager.loadConfigFromDb();
        //然后将配置同步到ZK
        cfgManager.syncFtpConfigToZk();
 
        //模拟客户端程序运行
        clientApp.run();
 
        //模拟配置修改
        cfgManager.updateFtpConfigToDB(23, "10.6.12.34", "newUser", "newPwd");
        cfgManager.syncFtpConfigToZk();
 
        //模拟客户端自动感知配置变化
        clientApp.run();
 
    }
}
