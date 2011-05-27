package ch.bluepenguin.jcrweb.action
import javax.jcr.*
import  org.apache.jackrabbit.commons.JcrUtils


public class JcrAction {
	
	public Session login(url,name,pass) {
		Repository repository = JcrUtils.getRepository(url);
		return repository.login(new SimpleCredentials(name, pass.toCharArray()))
	}
	
	public void logout(Session session) {
		session.logout()
	}

}
