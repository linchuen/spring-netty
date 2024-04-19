package com.cooba.handler;

import com.cooba.service.CacheService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final CacheService cacheService;

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

            cacheService.cacheChannel(userId, ctx.channel());
        }
    }
}
