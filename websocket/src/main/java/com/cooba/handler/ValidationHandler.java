package com.cooba.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
public class ValidationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;

            HttpHeaders headers = req.headers();
            String playerId = headers.get("playerId");
            if (playerId == null) {
                log.error("playerId 為空");
                throw new RuntimeException();
            }
        }
        ctx.fireChannelRead(msg);
    }
}
