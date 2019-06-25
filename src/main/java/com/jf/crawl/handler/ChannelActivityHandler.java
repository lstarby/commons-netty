package com.jf.crawl.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jf.crawl.common.ChannelUtil;
import com.jf.crawl.thread.ChannelReconnectThread;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端监听channel活动连接，并执行重连
 *
 * @author lamen 2019-06-24
 *
 */
@Sharable
public class ChannelActivityHandler extends ChannelInboundHandlerAdapter {

	private static ExecutorService reconnectPool = Executors.newFixedThreadPool(10);
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ChannelUtil.removeChannel(ctx.channel());
		reconnectPool.execute(new ChannelReconnectThread(ctx.channel()));
		ctx.fireChannelInactive();
	}
	
}
