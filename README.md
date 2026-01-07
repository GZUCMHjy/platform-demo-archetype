# Platform Demo Archetype

企业级Spring Boot多模块项目脚手架，遵循阿里巴巴Java开发规范。

## ✨ 特性

- 🏗️ **多模块架构**：api、common、infrastructure、service四层分离
- 🎯 **技术栈**：Spring Boot 3.3.1 + Java 17 + MyBatis Plus
- 🔐 **统一响应**：Result统一返回格式，全局异常处理
- 📝 **规范遵循**：严格遵循阿里巴巴Java开发规范
- 🚀 **中间件集成**：Redis、RabbitMQ、MySQL
- 🛠️ **工具类库**：JSON、日期、字符串、集合、加密、JWT
- 🔒 **分布式锁**：基于Redisson实现
- 📊 **日志管理**：Logback配置，支持异步日志
- 🌍 **多环境配置**：dev、test、prod环境隔离

## 📦 快速开始

### 前置要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.8+

### 1. 安装Archetype

```bash
cd platform-demo-archetype
mvn clean install
```

### 2. 生成项目

#### 方法一：使用脚本（推荐）

**Windows PowerShell:**
```powershell
.\generate-project.ps1 -GroupId "com.mynewproject" -ArtifactId "my-project"
```

**Linux/Mac:**
```bash
./generate-project.sh -g com.mynewproject -a my-project
```

#### 方法二：使用Maven命令

```bash
mvn archetype:generate \
  -DarchetypeGroupId=com.platform \
  -DarchetypeArtifactId=platform-demo-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DgroupId=com.mynewproject \
  -DartifactId=my-project \
  -Dpackage=com.mynewproject \
  -DarchetypeRepository=~/.m2/repository \
  -B \
  -Dfile.encoding=UTF-8
```

**⚠️ 重要：必须指定 `-Dpackage` 参数，否则 `${package}` 不会被替换！**

### 3. 配置项目

```bash
cd my-project

# 修改数据库配置
vim ${artifactId}-service/src/main/resources/application-dev.yml

# 执行数据库初始化脚本
mysql -u root -p < ${artifactId}-service/src/main/resources/sql/init.sql
```

### 4. 启动项目

```bash
mvn clean compile
cd ${artifactId}-service
mvn spring-boot:run
```

访问：http://localhost:8080

## 📁 项目结构

```
my-project/
├── my-project-api/              # API接口层
│   └── src/main/java/
│       └── com/mynewproject/
│           ├── dto/             # 数据传输对象
│           ├── vo/              # 视图对象
│           └── feign/           # Feign客户端（Dubbo接口）
├── my-project-common/           # 公共工具层
│   └── src/main/java/
│       └── com/mynewproject/
│           ├── common/          # 公共组件
│           │   ├── enums/       # 枚举类
│           │   ├── exception/   # 异常类
│           │   └── result/      # 响应类
│           └── utils/           # 工具类
├── my-project-instructure/      # 基础设施层
│   └── src/main/java/
│       └── com/mynewproject/
│           ├── entity/          # 实体类
│           └── mapper/          # Mapper接口
└── my-project-service/          # 业务服务层
    └── src/main/java/
        └── com/mynewproject/
            ├── config/          # 配置类
            ├── controller/      # 控制器
            ├── mq/              # 消息队列
            │   ├── producer/    # 生产者
            │   └── consumer/    # 消费者
            └── service/         # 业务服务
                └── impl/        # 服务实现
```

## 🎯 核心功能

### 1. 统一响应和异常处理

```java
// 成功响应
return Result.success(data);

// 失败响应
throw new BusinessException(ErrorCode.USER_NOT_EXIST);

// 全局异常处理器会自动捕获并处理
```

### 2. Redis工具类

```java
@Autowired
private RedisUtils redisUtils;

// String操作
redisUtils.set("key", "value");
Object value = redisUtils.get("key");

// 分布式锁
@Autowired
private RedisLockUtils redisLockUtils;

boolean locked = redisLockUtils.lock("lockKey");
try {
    // 业务逻辑
} finally {
    redisLockUtils.unlock("lockKey");
}
```

### 3. RabbitMQ消息队列

```java
@Autowired
private MQProducer mqProducer;

// 发送消息
mqProducer.sendToDirect(message);

// 消费者会自动监听并处理
```

### 4. MyBatis Plus

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 继承ServiceImpl，拥有基础CRUD方法
    // 自定义业务方法
}
```

## 🔧 配置说明

### 多环境配置

- `application.yml` - 主配置文件
- `application-dev.yml` - 开发环境
- `application-test.yml` - 测试环境
- `application-prod.yml` - 生产环境

激活环境：
```yaml
spring:
  profiles:
    active: dev  # dev|test|prod
```

### 日志配置

日志文件位置：`${user.home}/Logs/{artifactId}-boot/`

- `{artifactId}-dev.log` - 开发日志
- `{artifactId}-error.log` - 错误日志

## 📚 代码规范

本项目严格遵循**阿里巴巴Java开发规范**：

1. ✅ 命名规范：驼峰命名、常量大写下划线
2. ✅ 注释规范：所有公共方法必须有JavaDoc
3. ✅ 异常处理：使用自定义异常，全局处理器统一处理
4. ✅ 日志规范：使用SLF4J，关键操作记录日志
5. ✅ 工具类：私有构造函数，静态方法
6. ✅ 事务管理：Service层使用@Transactional注解

## 🧪 测试

### 单元测试

```bash
mvn test
```

### 集成测试

```bash
mvn verify
```

## ❓ 常见问题

### Q1: `${package}` 没有被替换？

**A:** 必须在生成命令中指定 `-Dpackage` 参数：

```bash
mvn archetype:generate ... -Dpackage=com.mynewproject
```

### Q2: 依赖下载失败？

**A:** 检查Maven配置文件settings.xml，配置阿里云镜像：

```xml
<mirror>
  <id>aliyun</id>
  <mirrorOf>central</mirrorOf>
  <name>Aliyun Maven</name>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

### Q3: 启动报错连接不上数据库？

**A:** 检查 `application-dev.yml` 中的数据库配置，确保MySQL服务已启动。

## 🗺️ 后续规划

- [ ] 集成Dubbo 3实现RPC调用
- [ ] 集成Swagger/Knife4j接口文档
- [ ] 集成Spring Security安全框架
- [ ] 集成Spring Cloud微服务组件
- [ ] 添加单元测试和集成测试
- [ ] 集成CI/CD流程


## 🤝 贡献

欢迎提交Issue和Pull Request！

## 📮 联系方式

- 作者：louis

---

**注意：本项目是脚手架模板，生成后需要根据实际业务进行调整。**
