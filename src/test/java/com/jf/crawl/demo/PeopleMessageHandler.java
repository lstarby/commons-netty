package com.jf.crawl.demo;

import com.jf.crawl.handler.DefaultObjectHandler;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class PeopleMessageHandler extends DefaultObjectHandler {

	@Override
	public void handleMessage(byte[] msg) {
		PeopleMessage message = parseObject(msg, PeopleMessage.class);
		System.out.println("------------------ " + message);
	}

}
