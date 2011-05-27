package ch.bluepenguin.jcrweb.model.vaadin;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import ch.bluepenguin.jcrweb.jcr.util.JcrUtil;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.FilesystemContainer.FileItem;

public class JcrNodeHierarchyContainer implements Container, Container.Hierarchical {

	private Node rootNode;
	private boolean completeLoad;
	private Map<String,Node> loadedNodes;
	
	public JcrNodeHierarchyContainer(Node rootNode, boolean completeLoad) {
		this.rootNode=rootNode;
		this.completeLoad=completeLoad;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 812397324944448933L;

	public Item getItem(Object itemId) {
        if (!(itemId instanceof String)) {
            return null;
        }
        JcrNodeItem item = new JcrNodeItem(getLoadedNodes().get(itemId));
        return item;
	}

	public Collection<String> getContainerPropertyIds() {
		return JcrNodeItem.ALL_PROPERTY_IDS;
	}

	public Collection<String> getItemIds() {
		if(completeLoad) {
			return Collections.unmodifiableCollection(getLoadedNodes().keySet());
		} else {
			System.out.println("only complete loading supported atm");
			return null;
		}
	}

	public Property getContainerProperty(Object itemId, Object propertyId) {
		if(completeLoad) {
			Node node = getLoadedNodes().get(itemId);
			return new JcrNodeItem(node).getItemProperty(propertyId);
			
		} else {
			System.out.println("only complete loading supported atm");
			return null;
		}
	}

	public Class<?> getType(Object propertyId) {
		// TODO Auto-generated method stub
		return String.class;
	}

	public int size() {
		return getLoadedNodes().size();
	}

	public boolean containsId(Object itemId) {
		if(completeLoad) {
			return getLoadedNodes().containsKey(itemId);
		} else {
			System.out.println("only complete loading supported atm");
			return false;
		}
	}

	public Item addItem(Object itemId) throws UnsupportedOperationException {
		try {
			Node node = rootNode.addNode( JcrUtil.inSubPath((String)itemId,rootNode.getPath()));
			return new JcrNodeItem(node);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Object addItem() throws UnsupportedOperationException {
		return new UnsupportedOperationException ("Currently, you have to supply a path. Might change");
	}

	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
		try {
			rootNode.getNode( JcrUtil.inSubPath( (String)itemId, rootNode.getPath())).remove();
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
		this.loadedNodes.clear();
		return true;
	}

	public Collection<String> getChildren(Object itemId) {
		try {
			Node node = rootNode.getNode( JcrUtil.inSubPath((String)itemId, rootNode.getPath()));
			List<String> childrenPaths = new ArrayList<String>();
			javax.jcr.NodeIterator iter = node.getNodes();
			while(iter.hasNext()){
				Node child = iter.nextNode();
				childrenPaths.add(child.getPath());
			}
			return Collections.unmodifiableCollection(childrenPaths);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Object getParent(Object itemId) {
		try {
			Node node=rootNode.getSession().getNode((String) itemId);
			return node.getParent().getPath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Collection<String> rootItemIds() {
		// TODO Auto-generated method stub
		List<String> ids = new ArrayList<String>();
		try {
			ids.add(rootNode.getPath());
			return Collections.unmodifiableCollection(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}		

	public boolean setParent(Object itemId, Object newParentId)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("not yet implemented");
	}

	public boolean areChildrenAllowed(Object itemId) {
		// TODO finer distinction
		return true;
	}

	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("not yet implemented");
	}

	public boolean isRoot(Object itemId) {
		if(itemId ==null) return false;
		try {
			String path = rootNode.getPath();
			return(path.equals(itemId));
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean hasChildren(Object itemId) {
		try {
			Node node = rootNode.getNode(JcrUtil.inSubPath( (String)itemId, rootNode.getPath()));
			return (node.getNodes().getSize()>0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private Map<String,Node> getLoadedNodes(){
		try {
			if(loadedNodes==null || loadedNodes.size()==0) {
				this.loadedNodes=JcrUtil.traverseDepthFirst(rootNode);
			}
			return loadedNodes;
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}


