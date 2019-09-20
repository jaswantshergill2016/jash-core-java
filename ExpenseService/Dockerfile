FROM leofoto/alpine-java:base
MAINTAINER reactivestax.com
COPY  target/ExpenseService-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/ExpenseService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/ExpenseService-0.0.1-SNAPSHOT.jar"]
EXPOSE 8282