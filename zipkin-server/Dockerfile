FROM leofoto/alpine-java:base
MAINTAINER reactivestax.com
COPY  target/zipkin-server-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/spring-cloud-zipkin-server.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/spring-cloud-zipkin-server.jar"]
EXPOSE 9411
