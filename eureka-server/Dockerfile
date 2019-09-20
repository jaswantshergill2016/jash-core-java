FROM leofoto/alpine-java:base
MAINTAINER reactivestax.com
COPY  target/eureka-server-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/spring-cloud-eureka-server.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/spring-cloud-eureka-server.jar"]
EXPOSE 8761
