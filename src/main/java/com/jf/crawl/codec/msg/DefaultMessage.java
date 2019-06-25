package com.jf.crawl.codec.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 默认消息(存储对象流)
 *
 * @author lamen 2019-06-21
 *
 */
public class DefaultMessage implements Message {

	@JSONField(serialize=false)
	private byte[] bodyBuffer;
	
	@Override
	public void setBodyBuffer(byte[] bodyBuffer) {
		this.bodyBuffer = bodyBuffer;
	}

	@Override
	public byte[] getBodyBuffer() {
		return bodyBuffer;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}