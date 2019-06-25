package com.jf.crawl.demo;

import com.jf.crawl.handler.DefaultObjectHandler;

public class PeopleMessageHandler extends DefaultObjectHandler {

	@Override
	public void handleMessage(byte[] msg) {
		PeopleMessage message = parseObject(msg, PeopleMessage.class);
		System.out.println("------------------ " + message);
	}

}
