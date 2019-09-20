FROM leofoto/alpine-java:base
MAINTAINER reactivestax.com
COPY  target/config-server-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/spring-cloud-config-server.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/spring-cloud-config-server.jar"]
EXPOSE 8888