# 1、项目介绍
本项目是一个spring boot web的集成后端项目，主要是验证集成GraaLVM。
> spring boot
> 
> https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/#native-image
>
> graalvm
> 
> https://www.graalvm.org/jdk23/docs/
> 
> paketo
>
> https://paketo.io/docs/howto/java/
>
## 1.1、为什么选择Graalvm
1. 速度快
2. 编译好的项目提供其他人私有化部分，不拍源码被盗

## 缺点
1. 打包速度慢的出奇

电脑情况 2021 macPro I9  32G
```text
打包时间 Total time:  16:38 min
```

2. 第三方一些jar 支持不是很好
   1. easyexcel
   2. apache.poi
   3. mybatisPlus
   4. json的序列化  **老项目升级：已做好maven插件，快速找到所有的json序列化代码（付费）**
   5. spring的BeanUtils.copy的反射  **老项目升级：已做好maven插件，快速找到所有的json序列化代码（付费）**

# 2、如何使用

## 2.1、前期准备
### 2.1.1. 安装SDKMAN 软件。
```shell
# 官网 https://sdkman.io/install
```
### 2.1.1.1 安装java 21版本
```shell
sdk install java 23.1.3.r21-nik
sdk use java 23.1.3.r21-nikq
sdk current #查看当前sdk
# Using:
# java: 23.1.3.r21-nik

```
### 2.2 下载依赖父项目
    
需要下载 [framework-parent](https://github.com/graalvm-samples/framework-parent)。并`mvn install`安装到maven本地仓库

## 2.2、下载本项目
```shell
# 在项目根目录 执行命令。将打包项目为jar
mvn -DskipTests package 

# 在framword-sample-web-starter项目目录下 执行命令
cd framword-sample-web-starter
mvn -Pnative -DskipTests spring-boot:build-image

# 第二种 编译为exe
mvn -Pnative -DskipTests native:compile

```

## 2.3 运行项目
```shell
docker run --rm -p 8080:8080 \
-e spring.data.redis.host=192.168.199.199 \
-v ./demo-db.trace.db:/workspace/demo-db.trace.db \
-v ./demo-db2.trace.db:/workspace/demo-db2.trace.db \
-v ./demo-db.mv.db:/workspace/demo-db.mv.db \
-v ./demo-db2.mv.db:/workspace/demo-db2.mv.db \
docker.io/library/framword-sample-web-starter:2.1.0-SNAPSHOT
```

# 3、 启动时间
```text
# java启动时间
Started DemoApplication in 14.559 seconds (process running for 16.9)
# Graalvm 启动时间
Started DemoApplication in 5.396 seconds (process running for 5.407)
```