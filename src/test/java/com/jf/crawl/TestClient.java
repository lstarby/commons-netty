package com.jf.crawl;

import com.jf.crawl.common.ChannelUtil;
import com.jf.crawl.connect.DecoderType;
import com.jf.crawl.connect.NettyClient;
import com.jf.crawl.demo.PeopleMessage;

public class TestClient {

	public static void main(String[] args) throws InterruptedException {
		
		String[] connectInfoList = { "127.0.0.1:6021" };
		
		for (String connectInfo : connectInfoList) {
			String[] connect = connectInfo.split(":");
			NettyClient.build(DecoderType.OBJECT).host(connect[0]).port(Integer.valueOf(connect[1])).open();
		}
		
		for (int i = 0; i < 10; i++) {
			PeopleMessage m = new PeopleMessage();
			m.setAge(i);
			m.setName("张三");
			
			ChannelUtil.asyncSendMessage(m);
		}
	}
}
