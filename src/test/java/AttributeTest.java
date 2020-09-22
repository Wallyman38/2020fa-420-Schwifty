/*
    Author: Chris, Dominic, Tyler, Cory, and Drew
    Date: 09/16/2020
    Purpose: Tests the public methods from the attribute class.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
public class AttributeTest {

     @Test
    public void testGetName() 
    {
        Attribute test = new Attribute("name", "type");
        //Test attribute's name should equal "name".
        assertEquals("name", test.getName());
    }

     @Test
    public void testGetType() 
    {
        Attribute test = new Attribute("name", "type");
        //Test attribute's type should equal "type".
        assertEquals("type", test.getType());
    }

    @Test
    public void testSetName() 
    {
        //Simple name change.
        Attribute test = new Attribute("name", "type");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Change to empty string/white spaces.
        Attribute test2 = new Attribute("name", "type");
        //Don't allow empty/whitespace name.
        assertThrows(IllegalArgumentException.class, () -> {
            test.setName(" ");
        });
    }

    @Test
    public void testSetType() 
    {
        //Simple type change.
        Attribute test = new Attribute("name", "type");
        test.setType("newType");
        assertEquals("newType", test.getType());
        //Change to empty string/white spaces.
        Attribute test2 = new Attribute("name", "type");
        //Don't allow empty/whitespace type.
        assertThrows(IllegalArgumentException.class, () -> {
            test2.setType(" ");
        });
    }

    @Test
    public void testEquals() 
    {
        //Equals method works for equal attributes.
        Attribute test1 = new Attribute("name", "type");
        Attribute test2 = new Attribute("name", "type");
        assertTrue(test1.equals(test2));
        //Equals method does not work for unequal attributes.
        //Deiffrent names.
        Attribute test3 = new Attribute("name", "type");
        Attribute test4 = new Attribute("name1", "type");
        assertFalse(test3.equals(test4));
        //Different types.
        Attribute test5 = new Attribute("name", "type");
        Attribute test6 = new Attribute("name", "type1");
        assertFalse(test5.equals(test6));
    }

     @Test
    public void testToString() 
    {
        Attribute test = new Attribute("name", "type");
        //assertEquals("type : name", test.toString().equals("type : name"));
        assertEquals("type : name", test.toString());
    }
}