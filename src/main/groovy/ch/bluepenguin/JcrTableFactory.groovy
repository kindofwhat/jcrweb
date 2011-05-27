package ch.bluepenguin

import org.groovyvadin.factories.TableFactory

import com.vaadin.ui.Component


public class JcrTableFactory extends TableFactory {
	@Override protected Component createComponent() {
		return new JcrTable()
	}

	
}
