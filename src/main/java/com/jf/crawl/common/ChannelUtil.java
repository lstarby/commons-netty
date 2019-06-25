package com.jf.crawl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

public class ChannelUtil {

	private static CircledList<Channel> channels = new CircledList<>();
	private static Logger logger = LoggerFactory.getLogger(ChannelUtil.class);
	
	public static ChannelFuture asyncSendString(String msg) {
		return asyncSendMessage(msg + "\n");
	}
	
	/**
	 * 发送消息
	 * @param msg 消息体对象
	 * @return
	 */
	public static ChannelFuture asyncSendMessage(Object msg) {
		
		for (int i = 0; i < 3; i++) {
			Channel ch = getChannel();
			
			if (ch == null) {
				continue;
			}
			
			ChannelFuture future = ch.writeAndFlush(msg);
			
			future.addListener(new GenericFutureListener<ChannelFuture>() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						logger.info("send message success.");
					} else {
						logger.error("send message failed. {}", msg, future.cause());
					}
				}
			});
			return future;
		}
		
		return null;
	}
	
	private static Channel getChannel() {
		Channel ch = getChannels().get();
		if (ch != null && ch.isActive()) {
			return ch;
		}
		logger.warn("not usable channel");
		return null;
	}
	
	public static void addChannel(Channel ch) {
		getChannels().add(ch);
	}
	
	public static void removeChannel(Channel ch) {
		getChannels().remove(ch);
	}
	
	private static CircledList<Channel> getChannels() {
		return channels;
	}
	
}
