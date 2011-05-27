package ch.bluepenguin.jcrweb.model.vaadin;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import ch.bluepenguin.jcrweb.jcr.util.JcrUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.FilesystemContainer.FileItem;

public class JcrNodePropertiesContainer implements Container{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8533227222123740050L;
	private Node node;
	private Map<String,javax.jcr.Property> propertyMap= new HashMap<String,javax.jcr.Property>();
	
	public JcrNodePropertiesContainer(Node node) {
		this.node=node;
	}
	/**
	 * 
	 */

	public Item getItem(Object itemId) {
        if (!(itemId instanceof String)) {
            return null;
        }
        JcrNodePropertiesItem item = new JcrNodePropertiesItem(getPropertiesMap().get(itemId));
        return item;
	}

	private Map<String, javax.jcr.Property> getPropertiesMap() {
		PropertyIterator iterator;
		try {
			iterator = this.node.getProperties();
			propertyMap = new HashMap<String,javax.jcr.Property>();
			while(iterator.hasNext()) {
				javax.jcr.Property next = iterator.nextProperty();
				this.propertyMap.put(next.getName(), next);
			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return propertyMap;
	}
	public Collection<String> getContainerPropertyIds() {
		return JcrNodePropertiesItem.ALL_PROPERTY_IDS;
	}

	public Collection<String> getItemIds() {
		return Collections.unmodifiableCollection(getPropertiesMap().keySet());
	}

	public Property getContainerProperty(Object itemId, Object propertyId) {
		javax.jcr.Property property= getPropertiesMap().get(itemId);
		return new JcrNodePropertiesItem(property).getItemProperty(propertyId);
	}

	public Class<?> getType(Object propertyId) {
		if(propertyId.equals(JcrNodePropertiesItem.VALUE)) return javax.jcr.Value.class;
		return String.class;
	}

	public int size() {
		return getPropertiesMap().size();
	}

	public boolean containsId(Object itemId) {
			return getPropertiesMap().containsKey(itemId);
	}

	public Item addItem(Object itemId) throws UnsupportedOperationException {
		javax.jcr.Property property;
		try {
			property = node.setProperty((String) itemId, "");
			return new JcrNodePropertiesItem(property);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object addItem() throws UnsupportedOperationException {
		return new UnsupportedOperationException ("Currently, you have to supply a name. Might change");
	}

	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
		try {
			javax.jcr.Property property = node.getProperty((String) itemId);
			property.remove();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("not supported, never will be. or maybe will.");
	}

	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("not supported, never will be. or maybe will.");
	}

	
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("not supported, never will be. or maybe will.");
	}

	
	
}


