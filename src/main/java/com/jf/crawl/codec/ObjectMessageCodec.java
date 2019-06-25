package com.jf.crawl.codec;

import java.util.List;

import com.jf.crawl.codec.msg.DefaultMessage;
import com.jf.crawl.codec.msg.Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.ReferenceCountUtil;

/**
 * 实体对象编解码
 *
 * @author lamen 2019-06-21
 *
 */
public class ObjectMessageCodec extends ByteToMessageCodec<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		byte[] bytes = msg.toString().getBytes();
		out.writeInt(bytes.length);
		out.writeBytes(bytes);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int length = in.readableBytes();
		ByteBuf bodyBuffer = Unpooled.buffer(length);
		bodyBuffer.writeBytes(in.readBytes(length));

		Message message = new DefaultMessage();
		message.setBodyBuffer(bodyBuffer.array());
		
		ReferenceCountUtil.release(bodyBuffer);
		out.add(message);
	}
	
}
