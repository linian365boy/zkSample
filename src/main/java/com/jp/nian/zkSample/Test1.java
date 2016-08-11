package com.jp.nian.zkSample;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class Test1 {
	public static void main(String[] args) {
		String serverList = "192.168.143.172:2181";
		ZkClient zkClient = testZkClient(serverList);
		if(!zkClient.exists("/root")){
			zkClient.create("/root","heheda", CreateMode.PERSISTENT);//创建目录并写入数据
			String data = zkClient.readData("/root");
			System.out.println("data value : "+data);
			//递归删除子目录
			//zkClient.deleteRecursive("/root");
			System.out.println(String.format("删除节点是否成功：%s", zkClient.delete("/root")));
			System.out.println(String.format("节点是否存在：%s", zkClient.exists("/root")));
		}
	}
	
	//订阅children变化
	//逗号隔开serverList
	private static ZkClient testZkClient(String serverList){
		ZkClient zkClient4SubChild = new ZkClient(serverList);
		zkClient4SubChild.subscribeChildChanges("/root", new IZkChildListener(){
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) 
					throws Exception {
				System.out.println("clildren of path " + parentPath + ":" + currentChilds);
			}
		});
		
		zkClient4SubChild.subscribeDataChanges("/root", new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println(dataPath + " has deleted");
			}
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("Data of " + dataPath + " has changed");
			}
		});
		
		zkClient4SubChild.subscribeStateChanges(new IZkStateListener() {
			@Override
			public void handleStateChanged(KeeperState state) throws Exception {
				 System.out.println("handleStateChanged,stat:" + state);
			}
			@Override
			public void handleSessionEstablishmentError(Throwable throwable) throws Exception {
				 System.out.println("establishmentError,throwable:" + throwable);
			}
			@Override
			public void handleNewSession() throws Exception {
				 System.out.println("handleNewSession()");
			}
		});
		
		return zkClient4SubChild;
	}
	
}
