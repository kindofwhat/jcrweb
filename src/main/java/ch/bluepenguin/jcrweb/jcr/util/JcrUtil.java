package ch.bluepenguin.jcrweb.jcr.util;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

public class JcrUtil {
	
	public static String inSubPath(String largePath, String subPath) {
		if(largePath == null || subPath==null) return null;
		if(subPath.equals(largePath)) return ".";
		int index = largePath.indexOf(subPath);
		if(index <0) return null;
		return largePath.substring(index+1,largePath.length());
	}
	

	
	public static Map<String,Node> traverseDepthFirst(Node root) throws RepositoryException {
		Map<String,Node> list= new HashMap<String,Node>();
		list.put(root.getPath(),root);
		NodeIterator nodes=root.getNodes();
		
		while(nodes.hasNext()) {
			Node child = nodes.nextNode();
			Map<String, Node> children = traverseDepthFirst(child);
			Iterator<String> iter = children.keySet().iterator();
			while(iter.hasNext()) {
				String key=iter.next();
				Node value = children.get(key);
				list.put(key, value);
			}
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Value ValueForObject(Object value, ValueFactory factory) {
		if(value==null) return null;
		Class clazz= value.getClass();
		if (clazz.isAssignableFrom(String.class)) {
			return factory.createValue((String)value);
		} else if (clazz.isAssignableFrom(Boolean.class)
				|| clazz.isAssignableFrom(boolean.class)) {
			return factory.createValue((Boolean)value);
		} else if (clazz.isAssignableFrom(Long.class)
				|| clazz.isAssignableFrom(long.class)
						||clazz.isAssignableFrom(Integer.class)
						|| clazz.isAssignableFrom(int.class)){
			return factory.createValue((Long)value);
		}else if(clazz.isAssignableFrom(BigDecimal.class)) {
			return factory.createValue((BigDecimal)value);
		} else if(clazz.isAssignableFrom(Date.class) ||
				clazz.isAssignableFrom(Calendar.class)) {
			return factory.createValue((Calendar)value);
		} else if(clazz.isAssignableFrom(InputStream.class)||
				clazz.isAssignableFrom(Binary.class)) {
			return factory.createValue((Binary)value);
		} else if(clazz.isAssignableFrom(Value.class))  {
			return (Value)value;
		} else if(clazz.isAssignableFrom(Node.class))  {
			try {
				return factory.createValue((Node)value);
			} catch (RepositoryException e) {
				return null;
			}
		}
		
		
		return null;
		
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int getPropertyTypeForObject(Object value) {
		if(value==null) return PropertyType.UNDEFINED;
		Class clazz= value.getClass();
		if (clazz.isAssignableFrom(String.class)) {
			return PropertyType.STRING;
		} else if (clazz.isAssignableFrom(Boolean.class)
				|| clazz.isAssignableFrom(boolean.class)) {
			return PropertyType.BOOLEAN;
		} else if (clazz.isAssignableFrom(Long.class)
				|| clazz.isAssignableFrom(long.class)
						||clazz.isAssignableFrom(Integer.class)
						|| clazz.isAssignableFrom(int.class)){
				return PropertyType.LONG;
		}else if(clazz.isAssignableFrom(BigDecimal.class)) {
			return PropertyType.DECIMAL;
		} else if(clazz.isAssignableFrom(Date.class) ||
				clazz.isAssignableFrom(Calendar.class)) {
			return PropertyType.DATE;
		} else if(clazz.isAssignableFrom(InputStream.class)||
				clazz.isAssignableFrom(Binary.class)) {
			return PropertyType.BINARY;
		} else if(clazz.isAssignableFrom(Value.class))  {
			return ((Value)value).getType();
		} else if(clazz.isAssignableFrom(Node.class))  {
			return PropertyType.REFERENCE;
		}
		
		
		return PropertyType.UNDEFINED;
		
	}

	public static Object getObjectForProperty(javax.jcr.Property  property) throws Exception {
		return getObjectForValue(property.getValue());
	}

	public static Object getObjectForValue(javax.jcr.Value  value) throws Exception {
		// TODO: mapping
		
		switch (value.getType()) {
		case (PropertyType.STRING):
			return value.getString();
		case (PropertyType.BOOLEAN):
			return value.getBoolean();
		case (PropertyType.LONG):
			return value.getLong();
		case(PropertyType.DOUBLE):
			return value.getDouble();
		case (PropertyType.DECIMAL):
			return value.getDecimal();
		case (PropertyType.DATE):
			return value.getDate().getTime();
		default:
			return value.getString();
		}
	}

	public static String getStringRepresentation(javax.jcr.Property  property) throws Exception {
		return getStringRepresentation(property.getValue());
	}
	public static String getStringRepresentation(javax.jcr.Value  value) throws Exception {
		// TODO: mapping
		
		switch (value.getType()) {
		case (PropertyType.BINARY):
			return new Long(value.getBinary().getSize()).toString();
		case (PropertyType.DATE):
			return value.getDate().getTime().toString();
		default:
			return value.getString();
		}
	}

	
}
