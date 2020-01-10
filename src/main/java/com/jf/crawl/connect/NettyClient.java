package com.jf.crawl.connect;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.crawl.codec.ObjectMessageCodec;
import com.jf.crawl.common.ChannelUtil;
import com.jf.crawl.handler.ChannelActivityHandler;
import com.jf.crawl.thread.ChannelReconnectThread;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

public class NettyClient extends AbstractConnector {

	private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
	private Bootstrap bootstrap = new Bootstrap();
	
	public static AbstractConnector build(DecoderType decoderType) {
		return new NettyClient(decoderType);
	}
	
	public NettyClient(DecoderType decoderType) {
		bootstrap.group(EventLoopGroupFactory.INS.getWorker())
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true) // 低延迟
			.option(ChannelOption.SO_RCVBUF, 2048) // 数据接收缓冲区大小
			.option(ChannelOption.SO_SNDBUF, 2048) // 数据发送缓冲区大小
			.handler(initPipeline(decoderType));
	}
	
	@Override
	public void open() {
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(getHost(), getPort()));
		
		future.addListener((ChannelFutureListener) future1 -> {
			if (future1.isSuccess()) {
				ChannelUtil.addChannel(future1.channel());
			}
		});
		
		try {
			future.sync();
			ChannelReconnectThread.addChannelConnector(future.channel(), this);
		} catch (InterruptedException e) {
			logger.error("future open failed.", e);
		}
	}
	
	private ChannelInitializer<Channel> initPipeline(DecoderType decoderType) {
		return new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) {
				ChannelPipeline pipeline = ch.pipeline();
				
				switch (decoderType) {
				case CUSTOM:
					pipeline.addLast(new ObjectMessageCodec());
					break;
				case STRING:
					pipeline.addLast(new LineBasedFrameDecoder(80));
					pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
					pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
					break;
				}
				
				pipeline.addLast(new LoggingHandler(LogLevel.INFO));
				pipeline.addLast(new ChannelActivityHandler());
			}
		};
	}
}
