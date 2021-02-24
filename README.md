#### 一: 功能简介: 在线教育系统

> 谷粒学院，是一个B2C模式的职业技能在线教育系统，分为前台用户系统和后台运营平台.

#### 二: 系统模块:

![AllenHeart-log](https://edu-2002.oss-cn-beijing.aliyuncs.com/2020/1bb0cd7a29b6b1bcf3c569e2441efcec.png)

#### 三: 系统架构:

- **性能**:主要考虑访问频率，每个用户每天的访问次数。项目初始阶段用户的访问量并不大，如果考虑做运营推广，可能会迎来服务器访问量骤增，因此要考虑**分布式部署，引入缓存
- **可扩展性**:系统功能会随着用户量的增加以及多变的互联网用户需求不断地扩展，因此考虑到系统的可扩展性的要求需要**使用微服务架构，引入消息中间件
- **高可用**: 系统一旦宕机，将会带来不可挽回的损失，因此必须做负载均衡，甚至是异地多活这类复杂的方案。如果数据丢失，修复将会非常麻烦，只能靠人工逐条修复，这个很难接受，因此需要考虑存储高可靠。我们需要考虑多种异常情况：机器故障、机房故障，针对机器故障，我们需要设计 MySQL 同机房主备方案；针对机房故障，我们需要设计 MySQL 跨机房同步方案。
- **安全性**:系统的信息有一定的隐私性，例如用户的个人身份信息，不包含强隐私（例如玉照、情感）的信息，因此使用账号密码管理、数据库访问权限控制即可。
- **成本**:视频类网站的主要成本在于服务器成本、流量成本、存储成本、流媒体研发成本，中小型公司可以考虑使用云服务器和云服务。

#### 四: 项目工程的架构简介:
```
- guli-parent：在线教学根目录（父工程），管理四个子模块：
- canal-client：canal数据库表同步模块（统计同步数据）
- common：公共模块父节点
    - common-util：工具类模块，所有模块都可以依赖于它
    - service-base：service服务的base包，包含service服务的公共配置类，所有service模块依赖于它
    - spring-security：认证与授权模块，需要认证授权的service服务依赖于它
- infrastructure：基础服务模块父节点
    - api-gateway：api网关服务
- service：api接口服务父节点
    - service-acl：用户权限管理api接口服务（用户管理、角色管理和权限管理等）
    - service-cms：cms api接口服务
    - service-edu：教学相关api接口服务
    - service-msm：短信api接口服务
    - service-order：订单相关api接口服务
    - service-oss：阿里云oss api接口服务
    - service-statistics：统计报表api接口服务
    - service-ucenter：会员api接口服务
    - service-vod：视频点播api接口服务
```


#### 五: 全栈技术架构流程图
![AllenHeart-log](https://edu-2002.oss-cn-beijing.aliyuncs.com/2020/7509966.png)
