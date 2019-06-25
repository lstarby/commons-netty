package com.jf.crawl;

import java.util.ArrayList;
import java.util.List;

import com.jf.crawl.connect.DecoderType;
import com.jf.crawl.connect.NettyServer;
import com.jf.crawl.demo.PeopleMessageHandler;

import io.netty.channel.ChannelHandler;

public class TestServer {

	public static void main(String[] args) {
		// 添加业务实现类
		List<ChannelHandler> handlerList = new ArrayList<>();
		handlerList.add(new PeopleMessageHandler());
		
		Integer[] ports = { 6021 };
		for (Integer port : ports) {
			NettyServer.build(handlerList, DecoderType.OBJECT).port(port).open();
		}
	}
}
