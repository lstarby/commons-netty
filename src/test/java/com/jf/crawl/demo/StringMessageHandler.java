package com.jf.crawl.demo;

import com.jf.crawl.handler.DefaultStringHandler;

public class StringMessageHandler extends DefaultStringHandler {

	@Override
	public void handleMessage(String msg) {
		System.out.println("------------------ " + msg);
	}

}
