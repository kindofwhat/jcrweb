package ch.bluepenguin

import com.vaadin.ui.*
import com.vaadin.terminal.*;

//import org.zhakimel.vgrails.builder.VaadinBuilder
import org.groovyvaadin.*
import org.groovyvadin.*
import com.vaadin.ui.Button.ClickListener
import org.apache.jackrabbit.commons.JcrUtils
import javax.jcr.Repository
import javax.jcr.SimpleCredentials
import javax.jcr.Session
import com.vaadin.ui.Button.ClickEvent
import ch.bluepenguin.jcrweb.action.JcrAction
import ch.bluepenguin.jcrweb.model.vaadin.JcrNodeHierarchyContainer
import ch.bluepenguin.jcrweb.model.vaadin.JcrNodePropertiesContainer
import ch.bluepenguin.view.ViewState
import com.vaadin.data.util.ObjectProperty
import com.vaadin.data.validator.*
import ch.bluepenguin.jcrweb.model.vaadin.JcrNodeItem
import ch.bluepenguin.JcrTableFieldFactory

public class JcrWebApplication extends com.vaadin.Application {
	def model=[:]
	JcrAction action = new JcrAction()
	VaadinBuilder builder
	ViewState vs
	
	void init() {
		Window myWindow = new Window()
		builder = new VaadinBuilder(myWindow)
		setMainWindow myWindow
		builder.model=model
		builder.registerFactory "jcrtable", new JcrTableFactory()
		
		
		def layout=builder.vlayout() {
			tabsheet() {
				vlayout(caption:'Login') {
					gridlayout('loginGrid', caption:'Login', columns:2, width: 600) {
						label( caption:'URL')
							textfield('url', model:'url')
						label(caption:'Name'); textfield('username', model:'username')
						label(caption:'Password'); textfield('password', secret:true, model:'password')
						label(); button('login', caption:'Login', onclick: {this.login()})
						label(); button('logout', caption:'Logout', onclick: {this.logout()}, enabled:false)
					}
					hlayout() {
						label('welcomeLabel')
					}
				} 
				hlayout('browser', caption:'Browser', readOnly:true, width:1100) {
					tree('browserTree', immediate:true, caption:'Browser Tree', width:200, onchange: { e-> /*println e.source;*/ this.displayNode()})
					hlayout('nodeDetails', visible:false) {
						jcrtable('nodeTable', width:800, caption:'Node Properties', tableFieldFactory: new JcrTableFieldFactory(),editable:true) 
					}
				}
			}
			label('messageLabel', contentMode:Label.CONTENT_PREFORMATTED)
		}
		vs = new ViewState(components:builder.components, model:model)
		vs.toLogout()
		setTheme "reindeer"
	model.url="http://localhost:8090/server"
	model.username="admin"
	model.password="admin"
	login()
		
	}

	private void displayNode() {
		Tree tree =builder.components.browserTree
		model.currentNode = tree.getItem(tree.value).node
		model.propertyModel = new JcrNodePropertiesContainer(model.currentNode)
		builder.components.nodeTable.containerDataSource=model.propertyModel
		vs.toDisplayNode()
	}
	private void logout() {
		action.logout(model.session)
		vs.toLogout()
	}
	private void login()  {
		Session session = action.login(model.url,model.username,model.password)
		if(session!=null) {
			model.session=session
			model.userID = session.userID
			model.workspace=session.workspace.name
			model.browserModel = new JcrNodeHierarchyContainer(model.session.rootNode, true);
			builder.components.browserTree.containerDataSource=model.browserModel
			
			vs.toLogin()
		}
	}

	@Override
	public void terminalError(Terminal.ErrorEvent event) {
		// Call the default implementation.
		builder.components.messageLabel.caption= "Exception thrown : ${event.getThrowable().toString().replaceAll('\n','<br/>')}"
	}
}