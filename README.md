j2ee-base-framework (Framework Setting Introduction)
===================
Document Author: Mingzhen Chen		CreatedDate: March 7, 2012<br/>
For details, refer to <b><i>Framework Instroduction.doc</i></b>
### Framework features:
    1.	Spring 3.0.5 + Struts 2.1.6 + Hibernate 3.6.5 Zero Configuration
    2.	Annotation Development Support
    3.	AOP Support
    4.	Transaction Support (Jotm)
    5.	Multiple Data Source Support(Using JNDI Data Source)
    6.	Importing P6spy
    7.	Log4j 1.2.14 Support
    8.	Exception Handler Framework


### In order to make the framework work, you may change the file setting following:

### \WebContent\META-INF\context.xml: 
    a. for data soruce setting, locate at \WebContent\META-INF\context.xml
    
    
    
### \WebContent\WEB-INF\Web.xml:
    a. <resource-ref>…<resource-ref>
    b.
    <filter>
    	<filter-name>OpenSessionInViewFilter1</filter-name>
    	<filter-class>org.springframework.orm.hibernate3.support.OpenSessionInViewFilter</filter-class>
    	<init-param>
    		 <param-name>sessionFactoryBeanName</param-name>
    		 <param-value>sessionFactory1</param-value>
    	</init-param>
    </filter>
    <filter-mapping>
    	<filter-name>OpenSessionInViewFilter1</filter-name>
    	<url-pattern>/*</url-pattern>
    </filter-mapping>	
    
### \WebContent\WEB-INF\classes\applicationContext.xml: 
    a. jndi-name
    b. sessionFactory: dataSource, packagesToScan, configLocation
    c. hibernateTemplate: sessionFactory
    d. <context:component-scan base-package="com.iisi" />
    e. <bean id="jotm" class="com.iisi.util.jta.JotmFactoryBean"/>
    
### \WebContent\WEB-INF\classes\struts.xml:
    a. <constant name="struts.action.extension" value="do,go"/>
    b. User-defined Interceptor
    
### \WebContent\WEB-INF\classes\spy.properties:
    a. realdriver=xxx(i.e. com.microsoft.sqlserver.jdbc.SQLServerDriver)
    b. realdriver2=xxx
    
### \com\iisi\util\FrameworkSetting.java:
    a. The constant fileds setting, which follow @Pointcut definition grammar(reference PointCut语法介绍.doc under folder)
    SPRINGHANDLER_MATCHER
    DAO_LAYER_MATCHER
    SERVICE_LAYER_MATCHER
    ACTION_LAYER_MATCHER
