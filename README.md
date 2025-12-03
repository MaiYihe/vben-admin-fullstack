## 前端
- 将“菜单”与“资源节点”概念区分开
- 相较开源的 VbenAdmin,增加了用户组管理、资源节点管理
- 新增 `MultiCheck` 组件，在用户组管理中使用。用于实现 **用户组-用户** 以及 **用户组-角色** 的关联
- 修改了 mock 后台，允许没有真实后端情况下单独运行

开源 VbenAdmin 前端默认行为：
- 动态路由
- 双 Token 验证机制

![资源节点](images/resources.png)
![前后端协同](images/commmunicate.png)

前端运行：
```bash
pnpm run dev
```
- 选择 `@vben/web-antd`


## 后端（开发中）
- 完整的微服务架构体系
- Nacos 注册/配置中心
- Dubbo 内部通信   
- JWT_AuthToken + RefreshToken 的（双 Token）登录安全机制    
- RBAC 菜单 + 权限码设计 
- Redis 缓存 JTI
- mybatis-plus-generator 代码生成器

- common-core/common-web/common-rpc 模块    
- SpringMVC 统一正常返回（`Controller + ApiResponse<T>`）
- SpringMVC 统一异常处理（业务异常 `BizEeception`，全局异常处理封装为 `ApiResponse<T>`）    
- 自定义鉴权 AOP
- gateway-service：路由、白名单、验证 token
- auth-service：
    - 注册、登录、登出、刷新 authToken 、获取权限码
    - BCrypt 密码加密存储
- user-service：
    - 查询用户相关信息


### sql 表
根据 VbenAdmin （开源前端）提供的 api 设计了数据表
