package com.vbenadmin.backend.commonrpc.config.properties;

// import lombok.Data;
// import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * 不能用！因为只要存在 ProtocolConfig，就认为当前服务要暴露 Provider 端口
 * 比如 Auth 是 consumer， user 是 provider，只有 user 需要有 ProtocolConfig 这个 Bean
 * 也不能单独把 RegistryConfig 作为 Bean 自动装配。因为有些微服务不需要 Dubbo
 */

//@Data
//@ConfigurationProperties(prefix = "dubbo")
//public class DubboProperties {
//    private final Registry registry = new Registry();
//    private final Protocol protocol = new Protocol();
//
//    @Data
//    public static class Registry {
//        private String address = "nacos://127.0.0.1:8848";
//    }
//
//    @Data
//    public static class Protocol {
//        private String name = "dubbo";
//        private int port = -1; //自动选择 Provider 端口
//    }
//}
