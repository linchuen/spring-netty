package com.cooba.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfig {

    @Bean
    public EventLoopGroup bossGroup(){
        return new NioEventLoopGroup();
    }

    @Bean
    public EventLoopGroup workerGroup(){
        return new NioEventLoopGroup();
    }

}
