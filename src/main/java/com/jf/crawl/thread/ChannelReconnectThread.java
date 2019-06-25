package com.jf.crawl.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.crawl.common.ThreadUtil;
import com.jf.crawl.connect.Connector;

import io.netty.channel.Channel;

/**
 * channel重连
 *
 * @author lamen 2019-06-24
 *
 */
public class ChannelReconnectThread implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ChannelReconnectThread.class);
	private static ConcurrentMap<Channel, Connector> channelMap = new ConcurrentHashMap<>();
	private Channel channel;
	
	public ChannelReconnectThread(Channel channel) {
		this.channel = channel;
	}
	
	@Override
	public void run() {
		while (!open()) {
			logger.info("channel reconnect doing ...");
			ThreadUtil.sleep(3000);
		}
		logger.info("channel reconnect success.");
	}
	
	private boolean open() {
		Connector connector = channelMap.get(channel);
		if (connector == null) {
			return true;
		}
		
		try {
			connector.open();
			return true;
		} catch (Exception e) {
			logger.error("channel open failed.", e);
		}
		return false;
	}
	
	public static void addChannelConnector(Channel ch, Connector connector) {
		channelMap.put(ch, connector);
	}

}
