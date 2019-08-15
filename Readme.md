## Please check the branches for different template engines reference usage

## Please note when we use tomcat plugin to run the application we change the servlet maven dependency to provided as it is also provided by the plugin

## We have defined spring dispatcher servlet in the web.xml and there is spring beans xml file which dispatcher servlet reads to create additional beans, here we create thymeleaf beans ,
## Please name of the spring context where we define additional beans should be SERVLET-NAME-IN-WEB.XML-servlet.xml   

## to run the app use mvn clean package tomcat7:run


### we got html page in the root on the webapp folder and views are defined in the webapp/WEB-INF/view/thymeleaf

## to debug the application set the MAVEN_OPTS environment variable as below then run  mvn clean package tomcat7:run

### export MAVEN_OPTS=-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 
### above configuration contains the port no 8000 which is the remote debug port to connect it from intellij or eclipse 
### check thymeleaf-debug-cfg.png for the debug configuration, refer to link for setup https://docs.alfresco.com/5.2/tasks/sdk-debug-intellij.html