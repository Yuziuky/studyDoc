# SpringCloud介绍

## 微服务架构涉及的技术

1. 服务调用
2. 服务降级
3. 服务注册与发现
4. 服务熔断
5. 负载均衡
6. 消息队列
7. 网关
8. 配置中心
9. 服务监控
10. 全链追踪
11. 服务总线



## SpringCloud组件停更及替换

![image.5g6chrwarzs0](cloud-imagaes/image.5g6chrwarzs0.png)

# 注册中心-Eureka

## Eureka介绍

Eureka采用C-S架构，分为Eureka-server和Eureka-client，并且Eureka-client包含服务提供者和服务消费者两种角色。

* Eureka-client：应用启动后会将信息注册到server。每个一段时间（默认30秒）向server发送发送心跳，证明当前服务可用。
* Eureka-server：提供服务注册服务，各节点启动后会在server注册，server存储所有可用服务节点信息。90秒没收到节点心跳，将节点移除

![image-20220830225106318](cloud-imagaes/image-20220830225106318.png)

## 配置文件

```yaml
eureka:
	instance:
		hostname: #主机名，默认为操作系统主机名
		instance-id: #唯一实例ID，不能与其他服务重复
		prefer-ip-address: #是否显示ip地址，为true时，hostname失效
	client: 
	server:
	dashboard:
```

* instance:当前Eureka Instance实例信息配置
* client：客户端信息配置
* server：注册中心信息配置
* dashboard：注册中心仪表盘配置

参考：https://www.cnblogs.com/jpfss/p/11308300.html

## 案例

### Eureka-Server

1. 添加依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.3</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<dependencies>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter</artifactId>
        <!--3.1.3-->
	</dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        <!--3.1.3-->
    </dependency>
</dependencies>
```



2. 启动代码添加@EnableEurekaServer注解

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String [] args){
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}
```



3.配置文件

```yaml
server:
  port: 7000

spring:
  application:
    name: EurekaServer

eureka:
  client:
    #注册中心不需要自己注册自己
    #默认true
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://localhost:${server.port}/eureka/
```

* eureka.client.register-with-eureka: 是否将自己注册到EurekaServer，默认true
* eureka.client.fetch-registry：是否从注册中心拉取注册信息表，默认true
* eureka.client.service-url.defaultZone: EurekaServer的地址，多个用逗号（，）分隔
* spring.application.name的内容会成为该服务的名字注册到EurekaServer



4. 启动后界面

启动后访问http://localhost:7000/

![image-20220831224734405](cloud-imagaes/image-20220831224734405.png)

### 集群

单点注册中心若遇到故障，对整个系统而言是毁灭性的，所以为维持其可用行，集群是很好的解决方案。Eureka通过相互注册来实现高可用的部署。

多注册中心配置步骤同上，需要改动的地方在于配置文件：

1. A-Server的配置文件，service-url需指向B-Server：

```yaml
server:
  port: 7001

spring:
  application:
    name: EurekaServer-A

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7000/eureka/
```

2. B-Server的配置文件，service-url需指向A-Server：

```yaml
server:
  port: 7000

spring:
  application:
    name: EurekaServer-B

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7001/eureka/
```

![image-20220903175956360](cloud-imagaes/image-20220903175956360.png)

### Eureka-Client

1. 引入依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

2. 启动代码添加@EnableEurekaClient注解

```java
@SpringBootApplication
@EnableEurekaClient
public class EurekaProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaProviderApplication.class, args);
    }
}
```

3. 配置文件

```yaml
spring:
  application:
    name: provider
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7000/eureka/,http://localhost:7001/eureka/
```

4. 启动后

![image-20220905212006737](cloud-imagaes/image-20220905212006737.png)

### 获取注册服务信息表

获取注册中心的注册服务列表

1. 启动代码添加@EnableDiscoveryClient

```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class EurekaProviderBApplication {
    public static void main(String[] args){
        SpringApplication.run(EurekaProviderBApplication.class,args);
    }
}
```

2. 获取注册服务信息表

```java
@Resource
private DiscoveryClient discoveryClient;

```

## 自我保护模式

当Eureka服务器每分钟收到的心跳数量低于一个阈值时，会触发自我保护模式，不会将服务注册表中的服务实例信息删除。当收到的心跳重新恢复到阈值时，才自动退出自我保护模式。

计算公式：服务实例总数量×（60/每个实例心跳间隔秒数）×自我保护系数（0.85）



# 注册中心-zookeeper

可通过https://downloads.apache.org/zookeeper/ 下载zookeeper，然后安装到服务器上。



## 案例

1. 引入依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
</dependency>
```

![image-20220907220046356](cloud-imagaes/image-20220907220046356.png)

该依赖的版本需与安装的zookeeper版本一致

若版本不一致，可以将该依赖排除，然后引入对应版本的zookeeper依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>xx.xx.xx</version>
</dependency>
```



2. 启动类注解

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ZKProvider_A_Application
{
    public static void main( String[] args )
    {
        SpringApplication.run(ZKProvider_A_Application.class,args);
    }
}
```

3. 配置文件

```yaml
server:
  port: 8004

spring:
  application:
    name: ZK-provider-A
  cloud:
    zookeeper:
      connect-string: #zk的IP地址
```

## ZK节点

### 节点类型

* 持久性节点：节点创建后一只存在于注册中心，客户端断连/注册中心重启，节点还存在。可以创建子节点，子节点可以临时也可以永久。不可同名
* 临时性节点：客户端失去与Server的连接后，Server会立刻将该节点删除。不能创建子节点，不能同名
* 顺序性节点
  * 持久顺序节点：同名节点会在后面添加序号，其余与持久节点一致
  * 临时顺序节点：同名节点会在后面添加序号，其余与临时节点一致

# 注册中心-consul

## 介绍

HashiGorp公司用go语言开发的一套开源的分布式服务发现和配置管理系统。

提供服务治理、配置中心、控制总线等功能，且每个功能可以单独使用。

下载地址：https://www.consul.io/downloads

## 案例

1. 引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

2. 配置文件

```yaml
server:
  port: 8005

spring:
  application:
    name: Consul-provider-A

  cloud:
    consul:
      host: #consul server host
      port: #consul server port
      discovery:
        service-name: ${spring.application.name}
```

3. 启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProvider_A_Application {
    public static void main(String[] args){
        SpringApplication.run(ConsulProvider_A_Application.class,args);
    }
}
```

使用到的启动类注解与ZK的注解一致

# Ribbon

## 负载均衡

将访问服务的请求，均衡的分配到每个服务上，避免服务因请求压力过大而崩溃。

负载均衡分为两种：

* 集中式LB()--在服务消费者和服务提供者之间有一个独立的负载均衡系统来承担负载均衡功能
* 进程式LB--将负载均衡功能整合进服务消费端，会从注册中心拉取注册信息表，并缓存到本地。

Ribbon是一个软负载均衡的客户端组件，可以和其他所需请求的客户端结合使用。

![img](cloud-imagaes/20180401083228491)

Ribbon工作时先从Eureka Server(注册中心)获取服务信息列表，优先选择同一个区域内负载较少的Server；然后根据用户指定的策略，将请求发送给服务提供者。

## Ribbon核心组件IRule及负载均衡策略

IRule接口：根据特定的算法从服务列表中选择一个要访问的服务

|                 实现类                  |                         负载均衡算法                         |
| :-------------------------------------: | :----------------------------------------------------------: |
| com.netflix.loadbalancer.RoundRobinRule |                             轮询                             |
|   com.netflix.loadbalancer.RandomRule   |                             随机                             |
|   com.netflix.loadbalancer.RetryRule    | 先按照RoundRobinRule的策略获取服务，如果获取失败则在指定时间内进行重试，获取可用的服务 |
|        WeightedResponseTimeRule         | 对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选中 |
|            BestAvailableRule            | 会先过滤掉由于多次访问故障而处于断路跳闸状态的服务，然后选择一个并发量小的服务 |
|        AvailabilityFilteringRule        |          先过滤掉故障实例，然后再选择并发量小的实例          |
|            ZoneAvoidanceRule            | 默认规则，复合判断server所在区域的性能和server的可用性选择服务器 |

Ribbon默认负载轮询算法：

接口第几次请求数%请求服务的集群数量=实际调用服务的位置下标

# openFeign

## 介绍

openFeign是Spring Cloud对feign的再次封装，使其支持MVC注解和HttpMessageConverts。Spring Cloud 集成了 Eureka、Spring Cloud CircuitBreaker 以及 Spring Cloud LoadBalancer，在使用 Feign 时提供负载均衡的 http 客户端

Feign是声明式Web Service客户端，简化了使用RestTemplate调用服务的步骤，通过接口+注解的方式来调用服务。

## 案例

1.引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

2.添加启动类注解

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OpenFeignApplication {
    public static void main(String[] args){
        SpringApplication.run(OpenFeignApplication.class,args);
    }
}
```

2.创建接口

```java
@Component
@FeignClient(value = "provider")
public interface TestService {
    @GetMapping("/hello/port")
    String getHello();
}
```

@FeignClient的value属性对应服务提供者的application.name

## 进阶

