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



