package com.jf.crawl.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理接收的消息(字符串)
 *
 * @author lamen 2019-06-25
 *
 */
public abstract class DefaultStringHandler extends SimpleChannelInboundHandler<String> implements MessageHandler<String> {

	private static Logger logger = LoggerFactory.getLogger(DefaultStringHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		handleMessage(msg);
		ctx.fireChannelRead(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("exceptionCaught.", cause);
		ctx.close();
	}

}
