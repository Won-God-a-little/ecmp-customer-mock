# 项目定义
内容中台业务辅助项目,主要提供3方面能力:
1. 提供内容中台接口模拟调用,方便用户理解接口能力;
2. 提供模拟用户侧回调,用于业务场景闭环测试;
3. 提供模拟客户侧环境,支持商务搭建演示Demo;

# 项目说明
访问地址: http://test.yqh5.cn/ecmp/mock/

# 部署说明
部署服务器: 172.16.41.12
部署目录: /data/mock/
部署方式: SpringBoot fatjar
1. gardle bootJar 命令打出jar包
2. 使用jumpserver 上传到服务器 /tmp目录
3. 删掉原jar包,mv 新jar
4. ./stop.sh 停止服务
5. ./start.sh 启动服务
6. ./log.txt 服务日志