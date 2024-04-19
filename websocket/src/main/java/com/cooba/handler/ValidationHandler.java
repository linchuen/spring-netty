package com.cooba.handler;

import com.cooba.repository.UserRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
public class ValidationHandler extends ChannelInboundHandlerAdapter {
    private final UserRepository userRepository;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;

            HttpHeaders headers = req.headers();
            String userId = headers.get("userId");
            if (userId == null) {
                log.error("userId 為空");
                throw new RuntimeException();
            }

            userRepository.findById(Long.parseLong(userId)).orElseThrow();
        }
        ctx.fireChannelRead(msg);
    }
}
