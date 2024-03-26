
FROM openjdk:11

# 设置工作目录
WORKDIR /app

ADD build/libs/*.jar /app

EXPOSE 8000

# 启动Java应用程序
CMD java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}${SPRING_PROFILES} -jar *.jar
