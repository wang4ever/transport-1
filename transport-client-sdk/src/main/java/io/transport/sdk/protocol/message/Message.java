package io.transport.sdk.protocol.message;

import java.io.Serializable;

import io.netty.buffer.ByteBuf;

/**
 * 基础抽象消息
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @date 2017年10月12日
 * @since
 */
public abstract class Message implements Serializable {
	final private static long serialVersionUID = -799664737197392144L;

	private Head head = new Head();

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	/**
	 * 读取ByteBuf流到Java Bean
	 * 
	 * @param in
	 */
	public abstract void readByteBufDecoder(ByteBuf in);

	/**
	 * 具体编码实现
	 * 
	 * @param out
	 */
	public abstract void writeBodyBufEncoder(ByteBuf out);

	@Override
	public String toString() {
		return "Message [head=" + head + "]";
	}

}
