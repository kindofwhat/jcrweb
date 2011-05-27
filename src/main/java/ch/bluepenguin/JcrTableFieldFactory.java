package ch.bluepenguin;

import java.util.Date;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.jcr.nodetype.NodeType;


import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.DefaultFieldFactory;



public class JcrTableFieldFactory implements TableFieldFactory{

	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = createFieldByProperty(item.getItemProperty(propertyId));
		field.setCaption(createCaptionByPropertyId(propertyId));
		return field;
	}

	public Field createField(Container container, Object itemId,
	Object propertyId, Component uiContext) {
		Property containerProperty = container.getContainerProperty(itemId,
				propertyId);
		Field field = createFieldByProperty(containerProperty);
		field.setCaption(createCaptionByPropertyId(propertyId));
		return field;
	}

	/**
	 * If name follows method naming conventions, convert the name to spaced
	 * upper case text. For example, convert "firstName" to "First Name"
	 *
	 * @param propertyId
	 * @return the formatted caption string
	 */
	public static String createCaptionByPropertyId(Object propertyId) {
		return DefaultFieldFactory.get().createCaptionByPropertyId( propertyId);
	}

	/**
	 * Creates fields based on the property type.
	 * <p>
	 * The default field type is {@link TextField}. Other field types generated
	 * by this method:
	 * <p>
	 * <b>Boolean</b>: {@link CheckBox}.<br/>
	 * <b>Date</b>: {@link DateField}(resolution: day).<br/>
	 * <b>Item</b>: {@link Form}. <br/>
	 * <b>default field type</b>: {@link TextField}.
	 * <p>
	 *
	 * @param type
	 *            the type of the property
	 * @return the most suitable generic {@link Field} for given type
	 */
	public static Field createFieldByProperty(Property property) {
		if(javax.jcr.Value.class.isAssignableFrom(property.getType())) {
			javax.jcr.Value value = (javax.jcr.Value)property.getValue();
			if(value.getType()==PropertyType.DATE)  {
	            final DateField df = new DateField();
	            df.setResolution(DateField.RESOLUTION_DAY);
	            return df;
			}
	        return new TextField();
			
		} else {
			return DefaultFieldFactory.createFieldByPropertyType(property.getType());
		}
	}
}
