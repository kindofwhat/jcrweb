package ch.bluepenguin.jcrweb.model.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;

import ch.bluepenguin.jcrweb.jcr.util.JcrUtil;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

public class JcrNodePropertiesItem implements Item {
	public static String NAME="Name";
	public static String TYPE="Type";
	public static String VALUE="Value";
	
    public static Collection<String> ALL_PROPERTY_IDS;
    
    static {
    	ALL_PROPERTY_IDS = new ArrayList<String>();
    	ALL_PROPERTY_IDS.add(NAME);
    	ALL_PROPERTY_IDS.add(VALUE);
    	ALL_PROPERTY_IDS.add(TYPE);
        ALL_PROPERTY_IDS = Collections.unmodifiableCollection(ALL_PROPERTY_IDS);

    }

	/**
	 * 
	 */
	private static final long serialVersionUID = -399451146650589636L;
	
	private javax.jcr.Property property;
	private Map<String,Property> properties;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JcrNodePropertiesItem(javax.jcr.Property property) {
		this.setProperty(property);
		properties = new HashMap<String,Property>();
		try {
			properties.put(TYPE,new ObjectProperty(PropertyType.nameFromValue(property.getType())));
			properties.put(NAME, new ObjectProperty(property.getName()));
			properties.put(VALUE, new ObjectProperty(property.getValue()));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Property getItemProperty(Object id) {
		return properties.get(id);
	}

	public Collection<?> getItemPropertyIds() {
		return properties.keySet();
	}

	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("no need to add meta-properties to node metadata");
	}

	
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("no need to remove meta-properties to node metadata");
	}

	public void setProperty(javax.jcr.Property property) {
		this.property = property;
	}

	public javax.jcr.Property getPropery() {
		return property;
	}
	
	public String toString() {
		return "";
	}

}
