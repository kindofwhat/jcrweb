package ch.bluepenguin

import org.apache.jackrabbit.webdav.jcr.version.report.JcrPrivilegeReport;

import ch.bluepenguin.jcrweb.jcr.util.JcrUtil;

import com.vaadin.data.Property
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Field
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory

class JcrTable extends Table {
	@Override
	protected String formatPropertyValue(Object rowId,
	Object colId, Property property) {
		// Format by property type
		if (javax.jcr.Value.class.isAssignableFrom( property.getType())) {
			return JcrUtil.getStringRepresentation(property.getValue())
		}

		return super.formatPropertyValue(rowId, colId, property);
	}

	@Override
	protected Object getPropertyValue(Object rowId, Object colId,
	Property property) {
		Property innerProperty=property
		if(javax.jcr.Value.class.isAssignableFrom(property.getType()) ) {
			innerProperty = new ObjectProperty(JcrUtil.getObjectForValue(property.getValue()))
		}
		return super.getPropertyValue(rowId, colId, innerProperty)
	}


}
