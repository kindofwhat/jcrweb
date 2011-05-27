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

public class JcrNodeItem implements Item {
	public static String NAME="Name";
	public static String IDENTIFIER="Identifier";
	public static String TYPE="Type";
	public static String CHECKED_OUT="CheckedOut";
	
    public static Collection<String> ALL_PROPERTY_IDS;
    
    static {
    	ALL_PROPERTY_IDS = new ArrayList<String>();
    	ALL_PROPERTY_IDS.add(NAME);
    	ALL_PROPERTY_IDS.add(IDENTIFIER);
    	ALL_PROPERTY_IDS.add(TYPE);
    	ALL_PROPERTY_IDS.add(CHECKED_OUT);
        ALL_PROPERTY_IDS = Collections.unmodifiableCollection(ALL_PROPERTY_IDS);

    }

	/**
	 * 
	 */
	private static final long serialVersionUID = -399451146650589636L;
	
	private Node node;
	private Map<String,Property> properties;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JcrNodeItem(Node node) {
		this.setNode(node);
		properties = new HashMap<String,Property>();
		try {
			properties.put(IDENTIFIER, new ObjectProperty(node.getIdentifier()));
			properties.put(TYPE,new ObjectProperty(node.getDefinition().getDefaultPrimaryTypeName()));
			properties.put(NAME, new ObjectProperty(node.getName()));
			properties.put(CHECKED_OUT, new ObjectProperty(new Boolean(node.isCheckedOut()).toString()));
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

	public void setNode(Node node) {
		this.node = node;
	}

	public Node getNode() {
		return node;
	}
	
	public String toString() {
		return "";
	}

}
