package com.jf.crawl.connect;

import java.net.InetSocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jf.crawl.codec.ObjectMessageCodec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer extends AbstractConnector {

	private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
	private ServerBootstrap bootstrap = new ServerBootstrap();
	
	public static AbstractConnector build(List<ChannelHandler> handlerList, DecoderType decoderType) {
		return new NettyServer(handlerList, decoderType);
	}

	public NettyServer(List<ChannelHandler> handlerList, DecoderType decoderType) {
		bootstrap.group(EventLoopGroupFactory.INS.getBoss(), EventLoopGroupFactory.INS.getWorker())
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)		  // 指定不能及时处理的连接队列大小
			.option(ChannelOption.SO_RCVBUF, 2048)		  // 数据接收缓冲区大小
			.option(ChannelOption.SO_SNDBUF, 2048)		  // 数据发送缓冲区大小
			.childOption(ChannelOption.TCP_NODELAY, true) // 低延迟
			.childHandler(initPipeline(handlerList, decoderType));
	}
	
	@Override
	public void open() {
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(getPort()));

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				if (channelFuture.isSuccess()) {
					logger.info("server netty start success.");
				} else {
					logger.error("server netty start failed.", channelFuture.cause());
				}
			}
		});
	}

	private static ChannelInitializer<Channel> initPipeline(List<ChannelHandler> handlerList, DecoderType decoderType) {
		return new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				
				switch (decoderType) {
				case OBJECT:
					pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
					pipeline.addLast(new ObjectMessageCodec());
					break;
				case STRING:
					pipeline.addLast(new LineBasedFrameDecoder(80));
					pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
					pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
					break;
				}
				
				if (handlerList != null) {
					for (ChannelHandler handler : handlerList) {
						pipeline.addLast(handler);
					}
				}
			}
		};
	}

}
