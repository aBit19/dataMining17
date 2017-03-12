package data;

import java.util.ArrayList;
import enums.*;

/**
 * The data.Mushroom class is used to contain the data for each mushroom found in the datafile.
 * More info on each attribute in agaricus-lepiotaexplanation.txt.
 *
 */
public class Mushroom {
	
	/**
	 * Returns a list (as .class objects) of the different attribute types.
	 * Note that the class label is not added to this list of attributes.
	 * @return
	 */
	public static ArrayList<Object> getAttributeList()
	{
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(Cap_Shape.class);
		attributes.add(Cap_Surface.class);
		attributes.add(Cap_Color.class);
		attributes.add(Bruises.class);
		attributes.add(Odor.class);
		attributes.add(Gill_Attachment.class);
		attributes.add(Gill_Spacing.class);
		attributes.add(Gill_Size.class);
		attributes.add(Gill_Color.class);
		attributes.add(Stalk_Shape.class);
		attributes.add(Stalk_Root.class);
		attributes.add(Stalk_Surface_Above_Ring.class);
		attributes.add(Stalk_Surface_Below_Ring.class);
		attributes.add(Stalk_Color_Above_Ring.class);
		attributes.add(Stalk_Color_Below_Ring.class);
		attributes.add(Veil_Type.class);
		attributes.add(Veil_Color.class);
		attributes.add(Ring_Number.class);
		attributes.add(Ring_Type.class);
		attributes.add(Spore_Print_Color.class);
		attributes.add(Population.class);
		attributes.add(Habitat.class);
		
		return attributes;
	}

	/**
	 * The attribute to built a classifier for. 
	 * I.e. whether or not the mushroom is poisonous. 
	 */
	public Class_Label m_Class;
	
	public Cap_Shape m_cap_shape;
	
	public Cap_Surface m_cap_surface;
	
	public Cap_Color m_cap_color;
	
	public Bruises m_bruises;
	
	public Odor m_odor;
	
	public Gill_Attachment m_gill_attach;
	
	public Gill_Spacing m_gill_spacing;
	
	public Gill_Size m_gill_size;
	
	public Gill_Color m_gill_color;
	
	public Stalk_Shape m_stalk_shape;
	
	public Stalk_Root m_stalk_root;
	
	public Stalk_Surface_Above_Ring m_stalk_surface_above;
	
	public Stalk_Surface_Below_Ring m_stalk_surface_below;
	
	public Stalk_Color_Above_Ring m_stalk_color_above;
	
	public Stalk_Color_Below_Ring m_stalk_color_below;
	
	public Veil_Type m_veil_type;
	
	public Veil_Color m_veil_color;
	
	public Ring_Number m_ring_number;
	
	public Ring_Type m_ring_type;
	
	public Spore_Print_Color m_spore_color;
		
	public Population m_population;
	
	public Habitat m_habitat;
	
	/***
	 * Returns the value of an Attribute based on its .class type object.
	 * @param Attribute .class type object of its type
	 * @return
	 */
	public Enum getAttributeValue(Object Attribute)
	{
		if(Attribute.equals(Class_Label.class))
			return this.m_Class;
		if(Attribute.equals(Cap_Shape.class))
			return this.m_cap_shape;
		if(Attribute.equals(Cap_Surface.class))
			return this.m_cap_surface;
		if(Attribute.equals(Cap_Color.class))
			return this.m_cap_color;
		if(Attribute.equals(Bruises.class))
			return this.m_bruises;
		if(Attribute.equals(Odor.class))
			return this.m_odor;
		if(Attribute.equals(Gill_Attachment.class))
			return this.m_gill_attach;
		if(Attribute.equals(Gill_Spacing.class))
			return this.m_gill_spacing;
		if(Attribute.equals(Gill_Size.class))
			return this.m_gill_size;
		if(Attribute.equals(Gill_Color.class))
			return this.m_gill_color;
		if(Attribute.equals(Stalk_Shape.class))
			return this.m_stalk_shape;
		if(Attribute.equals(Stalk_Root.class))
			return this.m_stalk_root;
		if(Attribute.equals(Stalk_Surface_Above_Ring.class))
			return this.m_stalk_surface_above;
		if(Attribute.equals(Stalk_Surface_Below_Ring.class))
			return this.m_stalk_surface_below;
		if(Attribute.equals(Stalk_Color_Above_Ring.class))
			return this.m_stalk_color_above;
		if(Attribute.equals(Stalk_Color_Below_Ring.class))
			return this.m_stalk_color_below;
		if(Attribute.equals(Veil_Type.class))
			return this.m_veil_type;
		if(Attribute.equals(Veil_Color.class))
			return this.m_veil_color;
		if(Attribute.equals(Ring_Number.class))
			return this.m_ring_number;
		if(Attribute.equals(Ring_Type.class))
			return this.m_ring_type;
		if(Attribute.equals(Spore_Print_Color.class))
			return this.m_spore_color;
		if(Attribute.equals(Population.class))
			return this.m_population;
		if(Attribute.equals(Habitat.class))
			return this.m_habitat;

		throw new IllegalArgumentException("You are not supposed to reach that point");
	}

	public static Object[] getAttributeOutcomes(Object attribute) {
        if(attribute.equals(Class_Label.class))
            return Class_Label.values();
        if(attribute.equals(Cap_Shape.class))
            return Cap_Shape.values();
        if(attribute.equals(Cap_Surface.class))
            return Cap_Surface.values();
        if(attribute.equals(Cap_Color.class))
            return Cap_Color.values();
        if(attribute.equals(Bruises.class))
            return Bruises.values();
        if(attribute.equals(Odor.class))
            return Odor.values();
        if(attribute.equals(Gill_Attachment.class))
            return Gill_Attachment.values();
        if(attribute.equals(Gill_Spacing.class))
            return Gill_Spacing.values();
        if(attribute.equals(Gill_Size.class))
            return Gill_Size.values();
        if(attribute.equals(Gill_Color.class))
            return Gill_Color.values();
        if(attribute.equals(Stalk_Shape.class))
            return Stalk_Shape.values();
        if(attribute.equals(Stalk_Root.class))
            return Stalk_Root.values();
        if(attribute.equals(Stalk_Surface_Above_Ring.class))
            return Stalk_Surface_Above_Ring.values();
        if(attribute.equals(Stalk_Surface_Below_Ring.class))
            return Stalk_Surface_Below_Ring.values();
        if(attribute.equals(Stalk_Color_Above_Ring.class))
            return Stalk_Color_Above_Ring.values();
        if(attribute.equals(Stalk_Color_Below_Ring.class))
            return Stalk_Color_Below_Ring.values();
        if(attribute.equals(Veil_Type.class))
            return Veil_Type.values();
        if(attribute.equals(Veil_Color.class))
            return Veil_Color.values();
        if(attribute.equals(Ring_Number.class))
            return Ring_Number.values();
        if(attribute.equals(Ring_Type.class))
            return Ring_Type.values();
        if(attribute.equals(Spore_Print_Color.class))
            return Spore_Print_Color.values();
        if(attribute.equals(Population.class))
            return Population.values();
        if(attribute.equals(Habitat.class))
            return Habitat.values();
		throw new IllegalArgumentException("A case has been forgotten"+ attribute.toString());
	}

    public double computeDistance(Mushroom that) {
        double difference = 0;
        ArrayList<Object> attList = getAttributeList();
        for (int i = 0; i < attList.size(); i++)
            difference += this.getAttributeValue(attList.get(i)).equals(that.getAttributeValue(attList.get(i))) ? 0 : 1;
        return Math.sqrt(difference);

    }
}
