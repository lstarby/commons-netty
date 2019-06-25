package com.jf.crawl.handler;

public interface MessageHandler<T> {

	void handleMessage(T msg);
}
