package com.jf.crawl;

import com.jf.crawl.common.ChannelUtil;
import com.jf.crawl.connect.DecoderType;
import com.jf.crawl.connect.NettyClient;
import com.jf.crawl.demo.PeopleMessage;

/**
 * 启动客户端，通过ip和端口连接
 * @author lamen 2019/8/12
 */
public class TestClient {

	public static void main(String[] args) throws InterruptedException {
		
		String[] connectInfoList = { "127.0.0.1:6021" };
		
		for (String connectInfo : connectInfoList) {
			String[] connect = connectInfo.split(":");
			NettyClient.build(DecoderType.CUSTOM).host(connect[0]).port(Integer.valueOf(connect[1])).open();
		}
		
		for (int i = 0; i < 10; i++) {
			PeopleMessage m = new PeopleMessage();
			m.setAge(i);
			m.setName("张三");
			
			ChannelUtil.asyncSendMessage(m);
		}
	}
}
