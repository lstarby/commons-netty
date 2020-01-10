package com.jf.crawl.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jf.crawl.codec.msg.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理接收的消息(普通对象)
 *
 * @author lamen 2019-06-21
 *
 */
public abstract class DefaultObjectHandler extends SimpleChannelInboundHandler<Message> implements MessageHandler<byte[]> {

	private static Logger logger = LoggerFactory.getLogger(DefaultObjectHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
		handleMessage(msg.getBodyBuffer());
		ctx.fireChannelRead(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("exceptionCaught.", cause);
		ctx.close();
	}
	
	protected <T> T parseObject(byte[] msg, Class<T> c) {
		return JSON.parseObject(msg, c);
	}
}
