package ch.bluepenguin

import com.vaadin.ui.*
import com.vaadin.ui.Button.ClickListener
import org.apache.jackrabbit.commons.JcrUtils
import javax.jcr.Repository
import javax.jcr.SimpleCredentials
import javax.jcr.Session 
import com.vaadin.ui.Button.ClickEvent

class JcrWebApplication extends com.vaadin.Application implements ClickListener{

  void init() {
    CssLayout myLayout = new CssLayout()

    Window window = new Window("Hello Vade", myLayout)
    setMainWindow window

    myLayout.addComponent(new Label("Url Connection:"))
    TextField tf = new TextField()
    myLayout.addComponent tf
    Button button = new Button("Login")
    button.addListener this
    myLayout.addComponent button
//    Repository repository = JcrUtils.getRepository("rmi://localhost:1099/jackrabbit")
    Repository repository = JcrUtils.getRepository("http://localhost:9090/server")
    Session session=repository.login(new SimpleCredentials('admin', 'admin'.toCharArray()))
    def node = session.rootNode
    Label label2 = new Label("la${node.depth}la", Label.CONTENT_TEXT)
    window.addComponent label2

    setTheme "reindeer"
  }

  void buttonClick(ClickEvent event) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}