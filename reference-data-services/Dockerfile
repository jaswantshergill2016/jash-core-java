FROM leofoto/alpine-java:base
MAINTAINER reactivestax.com
COPY  target/Rest-EndPoints-ExpenseModel-0.0.1-SNAPSHOT.jar /opt/spring-cloud/lib/Rest-EndPoints-ExpenseModel-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/Rest-EndPoints-ExpenseModel-0.0.1-SNAPSHOT.jar"]
EXPOSE 8282


