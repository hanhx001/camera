
## 项目结构
```text
    └── spring-boot-plus
    ├── admin               SpringBootAdmin Server模块
    ├── bootstrap           spring-boot-plus 启动模块
    ├── config              配置模块
    ├── distribution        打包模块
    ├── docs                文档目录
    ├── example             示例模块，自己的业务可新建多个模块处理
    ├── framework           框架核心模块
    ├── generator           代码生成模块
    ├── scheduled           任务调度模块
    └── system              系统模块
```

### 项目环境 
中间件 | 版本 |  备注
-|-|-
JDK | 1.8+ | JDK1.8及以上 |
MySQL | 5.7+ | 5.7及以上 |
Redis | 3.2+ |  |

### 技术选型 
技术 | 版本 |  备注
-|-|-
Spring Boot | 2.2.0.RELEASE | 最新发布稳定版 |
Spring Framework | 5.2.0.RELEASE | 最新发布稳定版 |
Spring Boot Admin| 2.2.2 | 管理和监控SpringBoot应用程序 |
Mybatis | 3.5.3 | 持久层框架 |
Mybatis Plus | 3.3.1 | mybatis增强框架 |
HikariCP | 3.4.2 | 数据源 |
Fastjson | 1.2.67 | JSON处理工具集 |
Swagger2 | 2.9.2 | api文档生成工具 |
Knife4j | 2.0.2 | api文档生成工具 |
commons-lang3 | 3.9 | 常用工具包 |
commons-io | 2.6 | IO工具包 |
commons-codec | 1.14 | 加密解密等工具包 |
commons-collections4 | 4.4 | 集合工具包 |
reflections | 0.9.9 | 反射工具包 |
hibernate-validator | 6.0.18.Final | 后台参数校验注解 |
Shiro | 1.5.1 | 权限控制 |
JWT | 3.10.1 | JSON WEB TOKEN |
hutool-all | 5.2.4 | 常用工具集 |
lombok | 1.18.12 | 注解生成Java Bean等工具 |
mapstruct | 1.3.1.Final | 对象属性复制工具 |

### 项目链路图
![项目调用链路图](https://geekidea.oss-cn-chengdu.aliyuncs.com/spring-boot-plus/img/spring-boot-plus-link-diagram.jpg)

### [CHANGELOG](https://github.com/geekidea/spring-boot-plus/blob/master/CHANGELOG.md)

## 快速开始
### 克隆 spring-boot-plus
```bash
git clone https://gitee.com/geekidea/spring-boot-plus.git
cd spring-boot-plus
```

### Maven 构建
> 默认使用dev环境,对应配置文件：application-dev.yml

```bash
mvn clean package -Pdev
```


## 5分钟完成增删改查

### 1. 创建数据库表
```sql
-- ----------------------------
-- Table structure for foo_bar
-- ----------------------------
DROP TABLE IF EXISTS `foo_bar`;
CREATE TABLE `foo_bar`
(
    `id`            bigint(20)  NOT NULL COMMENT '主键',
    `name`          varchar(20) NOT NULL COMMENT '名称',
    `foo`           varchar(20)          DEFAULT NULL COMMENT 'Foo',
    `bar`           varchar(20) NOT NULL COMMENT 'Bar',
    `remark`        varchar(200)         DEFAULT NULL COMMENT '备注',
    `state`         int(11)     NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
    `version`       int(11)     NOT NULL DEFAULT '0' COMMENT '版本',
    `create_time`   timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp   NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='FooBar';

-- ----------------------------
-- Records of foo_bar
-- ----------------------------
INSERT INTO foo_bar (id, name, foo, bar, remark, state, version, create_time, update_time) 
    VALUES (1, 'FooBar', 'foo', 'bar', 'remark...', 1, 0, '2019-11-01 14:05:14', null);
INSERT INTO foo_bar (id, name, foo, bar, remark, state, version, create_time, update_time) 
    VALUES (2, 'HelloWorld', 'hello', 'world', null, 1, 0, '2019-11-01 14:05:14', null);

```


### 2.使用代码生成器生成增删改查代码
> 代码生成入口类，在generator模块中

```text
spring-boot-plus/generator/src/main/java/io/geekidea/springbootplus/generator/SpringBootPlusGenerator.java
```

```java
/**
 * spring-boot-plus代码生成器入口类
 *
 * @author geekidea
 * @date 2019-10-22
 **/
@Component
public class SpringBootPlusGenerator {

    /**
     * 生成代码
     * @param args
     */
    public static void main(String[] args) {
        GeneratorProperties generatorProperties = new GeneratorProperties();

        // 设置基本信息
        generatorProperties
                .setMavenModuleName("example")
                .setParentPackage("com.example")
                .setModuleName("foobar")
                .setAuthor("geekidea")
                .setFileOverride(true);

        // 设置表信息
        generatorProperties.addTable("foo_bar","id");

        // 数据源配置
        generatorProperties.getDataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/spring_boot_plus?useUnicode=true&characterEncoding=UTF-8&useSSL=false");

        // 生成配置
        generatorProperties.getGeneratorConfig()
                .setGeneratorStrategy(GeneratorStrategy.SINGLE)
                .setGeneratorEntity(true)
                .setGeneratorController(true)
                .setGeneratorService(true)
                .setGeneratorServiceImpl(true)
                .setGeneratorMapper(true)
                .setGeneratorMapperXml(true)
                .setGeneratorPageParam(true)
                .setGeneratorQueryVo(true)
                .setRequiresPermissions(true)
                .setPageListOrder(true)
                .setParamValidation(true)
                .setSwaggerTags(true)
                .setOperationLog(true);

        // 全局配置
        generatorProperties.getMybatisPlusGeneratorConfig().getGlobalConfig()
                .setOpen(true)
                .setSwagger2(true)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.ONLY_DATE);

        // 策略配置
        generatorProperties.getMybatisPlusGeneratorConfig().getStrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setVersionFieldName(GeneratorConstant.VERSION)
                .setLogicDeleteFieldName(GeneratorConstant.DELETED);

        // 生成代码
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generator(generatorProperties);
    }
}
```

#### Code Generator Templates
> 使用Velocity模版生成代码，可自定义修改代码生成模版

```text
spring-boot-plus/generator/src/main/resources
```

```text
└── templates
    ├── controller.java.vm      控制器代码生成模版
    ├── entity.java.vm          实体类代码生成模版
    ├── mapper.java.vm          mapper代码生成模版
    ├── mapper.xml.vm           mapper xml 代码生成模版
    ├── pageParam.java.vm       分页参数代码生成模版
    ├── queryVo.java.vm         查询结果代码生成模版
    ├── service.java.vm         服务接口代码生成模版
    └── serviceImpl.java.vm     服务实现代码生成模版
```

#### 生成的代码结构

```text
└── src
    └── main
        ├── java
        │   └── com
        │       └── example
        │           └── foobar
        │               ├── controller
        │               │   └── FooBarController.java
        │               ├── entity
        │               │   └── FooBar.java
        │               ├── mapper
        │               │   └── FooBarMapper.java
        │               ├── param
        │               │   └── FooBarPageParam.java
        │               ├── service
        │               │   ├── FooBarService.java
        │               │   └── impl
        │               │       └── FooBarServiceImpl.java
        │               └── vo
        │                   └── FooBarQueryVo.java
        └── resources
            └── mapper
                └── foobar
                    └── FooBarMapper.xml
```

### 3. 启动SpringBootAdmin
> SpringBootAdmin Server启动类，在admin模块中  [http://localhost:8000](http://localhost:8000)

```text
spring-boot-plus/admin/src/main/java/io/geekidea/springbootplus/admin/SpringBootPlusAdminApplication
```

```java
/**
 * Spring Boot Admin Bootstrap Main Class
 *
 * @author geekidea
 * @date 2020/3/20
 **/
@Slf4j
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@SpringBootApplication
public class SpringBootPlusAdminApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootPlusAdminApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String serverPort = environment.getProperty("server.port");
        log.info("SpringBootAdmin: http://localhost:" + serverPort);
    }

}
```

### 4. 启动项目
> 项目入口类，在bootstrap模块中  [http://localhost:8888](http://localhost:8888)

```text
spring-boot-plus/bootstrap/src/main/java/io/geekidea/springbootplus/SpringBootPlusApplication.java
```

```java
/**
 * spring-boot-plus 项目启动入口
 *
 * @author geekidea
 * @since 2018-11-08
 */
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties
@ServletComponentScan
@MapperScan({"io.geekidea.springbootplus.**.mapper", "com.example.**.mapper"})
@SpringBootApplication(scanBasePackages = {"io.geekidea.springbootplus", "com.example"})
public class SpringBootPlusApplication {

    public static void main(String[] args) {
        // 启动spring-boot-plus
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootPlusApplication.class, args);
        // 打印项目信息
        PrintApplicationInfo.print(context);
        // 打印项目提示
        PrintApplicationInfo.printTip(context);
    }

}
```

### 5. 访问项目Swagger文档
[http://47.105.159.10:8888/api/swagger-ui.html](http://47.105.159.10:8888/api/swagger-ui.html)
![swagger-ui.png](https://geekidea.oss-cn-chengdu.aliyuncs.com/spring-boot-plus/img/swagger-ui.png)
![swagger-ui-1.png](https://geekidea.oss-cn-chengdu.aliyuncs.com/spring-boot-plus/img/swagger-ui-1.png)

### 6. 访问Knife4j文档
[http://47.105.159.10:8888/api/doc.html](http://47.105.159.10:8888/api/doc.html)
![knife4j.png](https://geekidea.oss-cn-chengdu.aliyuncs.com/spring-boot-plus/img/knife4j.png)
![knife4j-1.png](https://geekidea.oss-cn-chengdu.aliyuncs.com/spring-boot-plus/img/knife4j-1.png)

## CentOS快速安装环境/构建/部署/启动spring-boot-plus项目
### 1. 下载安装脚本
> 安装 `jdk`, `git`, `maven`, `redis`, `mysql`

```bash
wget -O download-install-all.sh https://springboot.plus/bin/download-install-all.sh
```

### 2. 运行安装脚本
```bash
sh download-install-all.sh
```

### 3. 修改MySQL密码
```bash
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Springbootplus666!';
exit
mysql -uroot -pSpringbootplus666!
```

### 4. 导入MySQL脚本
```bash
create database if not exists spring_boot_plus character set utf8mb4;
use spring_boot_plus;
source /root/mysql_spring_boot_plus.sql;
show tables;
exit
```

### 5. 下载部署脚本 `deploy.sh`
```bash
wget -O deploy.sh https://springboot.plus/bin/deploy.sh
```

### 6. 执行脚本
```bash
sh deploy.sh
```

### 7. 查看项目运行日志
```bash
tail -f -n 1000 /spring-boot-plus-server-2.0/logs/spring-boot-plus.log
```

