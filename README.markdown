# jcrweb: A webbased GUI for JCR

Current state: very much nowhere :)


## Technology

* GUI: [vaadin](http://vaadin.org)
* language: [groovy](http://groovy.codehaus.org)
* JCR implementation: [jackrabbit](http://jackrabbit.apache.org)

Makes use of the vaadinbuilder found unter https://github.com/kindofwhat/vaadinbuilder.git


## Usage

* $ git clone git@github.com:kindofwhat/vaadinbuilder.git
* $ git clone git@github.com:kindofwhat/jcrweb.git
* cd vaadinbuilder
* mvn clean package install
* cd ../jcrweb
* mvn clean package install

==> this gives you a war-file in jcrweb/target/ which then can be lauchned

### The GroovyScriptEngineApplicationServlet
The GroovyScriptEngineApplicationServlet allows to declare some file system locations
where Groovy Files are automatically reloaded. The scriptsPath param is a list
of paths (separated by ; or newline)

Example:

    <servlet>
        <servlet-name>GroovyVaadin</servlet-name>
        <servlet-class>org.groovyvaadin.GroovyScriptEngineApplicationServlet</servlet-class>
        <init-param>
            <param-name>application</param-name>
            <param-value>ch.bluepenguin.JcrWebApplication</param-value>
        </init-param>   
        <init-param>
            <param-name>scriptsPath</param-name>
            <param-value>
    	../jcrweb/src/main/groovy;../vaadinbuilder/src/main/groovy
             </param-value>
        </init-param>   
        
    </servlet>
    <servlet-mapping>
        <servlet-name>GroovyVaadin</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>


