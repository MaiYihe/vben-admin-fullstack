## 前端
- 将“菜单”与“资源节点”概念区分开
- 相较开源的 VbenAdmin,增加了用户组管理、资源节点管理
- 新增 `MultiCheck` 组件，在用户组管理中使用。用于实现 **用户组-用户** 以及 **用户组-角色** 的关联
- 修改了 mock 后台，允许没有真实后端情况下单独运行

开源 VbenAdmin 前端默认行为：
- 动态路由
- 双 Token 验证机制

![资源节点](images/resources.png)
![前后端协同](images/communicate.png)

前端运行：
```bash
pnpm run dev
```
- 选择 `@vben/web-antd`


## 后端（开发中）
- 完整的微服务架构体系
- Nacos 注册/配置中心
- Dubbo RPC 内部通信
- JWT_AuthToken + RefreshToken 的（双 Token）登录安全机制    
- RBAC 菜单 + 权限码设计 
- MapStruct 实现 POJO 相互映射
- Redis 维护登陆状态： (jti, userId)
    - JWT 机制允许多端登陆
- mysql 本地数据库存储
- mybatis-plus-generator 代码生成器

- common-core/common-web/common-rpc 公告依赖模块
    - common-core 的 RedisUtils，common-web 的 GlobalExceptionHandler、JwtContextFilter、AccessCheckAspect 等类自动装配
- 双层状态码：HTTP 状态码、业务状态码（写在HTTP body）
- SpringMVC 统一正常返回（`Controller + ApiResponse<T>`）
- SpringMVC 统一异常处理（业务异常 `BizEeception`，全局异常处理封装为 `ApiResponse<T>`）    
- 自定义鉴权 AOP（accessCodes 权限码粒度功能鉴权）
- JWT 的 tokenPayload 保存权限信息，供微服务解析、查询、比对
- 自定义 JWTUtils、RedisUtils 等工具类
    - JWTUtils 含以下功能：创建 JWT、验证 JWT（合法性与正确性）、解析 JWT 取出 TokenPayload
    - RedisUtils 不使用 JDK 序列化与反序列化；定义存取 String 与存取一般对象的办法（手动序列化为 JSON）
- 自定义 JwtContextFilter，依赖 `common-web` 即自动配置
    - 将拦截 HTTP 请求并解析 JWT，将用户信息存入上下文
    - ThreadLocal 保证上下文生命周期与线程安全
- gateway-service：路由、白名单、验证 token
- auth-service：
    - 注册、登录、登出、刷新 accessToken 、获取权限码 authCodes
    - BCrypt 密码加密存储
- user-service：
    - 查询用户相关信息（表单动态查询）


> [!TIP] 项目使用 maven wrapper 构建与运行

```bash 
./mvnw clean install -Dskiptests
./mvnw -pl xxx-service spring-boot:run
```


## sql 表
根据 VbenAdmin （开源前端）提供的 api 设计了数据表
