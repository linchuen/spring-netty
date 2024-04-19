package com.cooba.handler;

import com.cooba.service.ChannelService;
import com.cooba.service.RegistryService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.NetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Locale;

@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelService cacheService;
    private final RegistryService registryService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        String request = frame.text();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete handshakeCompletedEvent = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            HttpHeaders headers = handshakeCompletedEvent.requestHeaders();
            String userId = headers.get("userId");

            registryService.registryUserConnectInfo(userId);
            cacheService.cacheChannel(userId, ctx);

        }
    }
}
