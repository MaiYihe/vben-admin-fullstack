package com.vbenadmin.backend.commonrpc.config;

//import com.vbenadmin.backend.common.config.properties.DubboProperties;
//import org.apache.dubbo.config.ProtocolConfig;
//import org.apache.dubbo.config.RegistryConfig;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

/*
 * 不能用！因为只要存在 ProtocolConfig，就认为当前服务要暴露 Provider
 * 比如 Auth 是 consumer， user 是 provider，只有 user 需要有 ProtocolConfig 这个 Bean
 */

//@Configuration
//@EnableConfigurationProperties(DubboProperties.class) //注册 DubboProperties 为 Bean
//public class DubboConfig {
//    @Bean
//    @ConditionalOnMissingBean(RegistryConfig.class)
//    public RegistryConfig dubboRegistryConfig(DubboProperties props) {
//        RegistryConfig config = new RegistryConfig();
//        config.setAddress(props.getRegistry().getAddress());
//        return config;
//    }
//a
//    @Bean
//    @ConditionalOnMissingBean(ProtocolConfig.class)
//    public ProtocolConfig dubboProtocolConfig(DubboProperties props) {
//        ProtocolConfig protocol = new ProtocolConfig();
//        protocol.setName(props.getProtocol().getName());
//        protocol.setPort(props.getProtocol().getPort());
//        return protocol;
//    }
//}
