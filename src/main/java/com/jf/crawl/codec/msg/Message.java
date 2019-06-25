package com.jf.crawl.codec.msg;

public interface Message {

	void setBodyBuffer(byte[] buffer);
	
    byte[] getBodyBuffer();
}
