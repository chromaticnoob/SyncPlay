package com.yuroyami.syncplay.protocol

import com.yuroyami.syncplay.protocol.JsonHandler.handleJson
import com.yuroyami.syncplay.utils.loggy
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.EventLoopGroup
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.ssl.SslContextBuilder
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SpProtocolAndroid : SyncplayProtocol() {

    /** Netty stuff */
    var channel: Channel? = null
    lateinit var pipeline: ChannelPipeline

    override fun connectSocket() {
        val group: EventLoopGroup = NioEventLoopGroup()
        val b = Bootstrap()
        b.group(group) /* Assigning the event loop group to the bootstrap */
            .channel(NioSocketChannel::class.java) /* We want a NIO Socket Channel */
            .handler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    pipeline = ch.pipeline()
                    pipeline.addLast("framer",
                        DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter())
                    )
                    pipeline.addLast(StringDecoder())
                    pipeline.addLast(StringEncoder())
                    pipeline.addLast(Reader())
                }
            })

        /** After we're done bootstrapping Netty, now it's time to connect */
        val f = b.connect(session.serverHost, session.serverPort)

        /** Listening to the connection progress */
        f.addListener(ChannelFutureListener { future ->
            if (!future.isSuccess) {
                syncplayCallback?.onConnectionFailed()
            } else {
                /* This is the channel, only variable we should memorize from the entire bootstrap/connection phase */
                channel = f.channel()
            }
        })

        if (!f.await(10000)) throw Exception()
    }

    override fun isSocketValid() = channel?.isActive == true

    override fun endConnection(terminating: Boolean) {
        try {
            /* Cleaning leftovers */
            channel?.close()

            if (terminating) {
                protoScope.cancel("")
            }
        } catch (_: Exception) {
        }
    }

    override fun writeActualString(s: String) {
        val f = channel?.writeAndFlush(s)
        f?.addListener(ChannelFutureListener { future ->
            if (!future.isSuccess) {
                syncplayCallback?.onDisconnected()
            }
        })
        f?.await(10000)
    }


    /** NETTY READING: A small inner class that does the reading callback (Delegated by Netty) */
    inner class Reader : SimpleChannelInboundHandler<String>() {
        override fun userEventTriggered(ctx: ChannelHandlerContext?, evt: Any?) {
            super.userEventTriggered(ctx, evt)

            loggy("Channel event: ${evt.toString()}", 0)
        }
        override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
            if (msg != null) {
                protoScope.launch {
                    loggy(msg, 2002)
                    handleJson(msg)
                }
            }
        }

        override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
            super.exceptionCaught(ctx, cause)
            syncplayCallback?.onDisconnected()
        }
    }


    override fun upgradeTls() {
        val sslContext = SslContextBuilder
            .forClient()
            //.sslProvider(SslProvider.JDK)
            //.trustManager(Conscrypt.getDefaultX509TrustManager())
            .startTls(false)
            .build()

        val h = sslContext.newHandler(pipeline.channel().alloc(), session.serverHost, session.serverPort)
        pipeline.addFirst(h)

    }
}