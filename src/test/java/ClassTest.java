/*
    Author: Chris, Dominic, Tyler, Cory and Drew
    Date: 09/10/2020
    Purpose: Runs tests on the public methods in the class class.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import UML.model.Class;
import UML.model.Field;
import UML.model.Method;
import UML.model.Parameter;
import UML.model.RelationshipType;

public class ClassTest 
{
    @Test
    public void testClass()
    {
        //Make a test class
        Class test = new Class("Test");

        //Make sure it's not null
        assertNotNull(test);

        //ensure all parts of the class are initilized properly.
        //name
        assertEquals("Test", test.getName());

        //Field set
        assertNotNull(test.getFields());
        //Method set 
        assertNotNull(test.getMethods());
        //Relationto set
        assertNotNull(test.getRelationshipsToOther());
        //relation from set
        assertNotNull(test.getRelationshipsFromOther());

        //Loaction should be 0,0
        double x = test.getLocation().getWidth();
        double y = test.getLocation().getHeight();
        assertEquals(0.0,x, 0.1);
        assertEquals(0.0,y, 0.1);
    }

    @Test
    public void testGetName() 
    {
        Class test = new Class("Test");
        //The test class's name should equal "Test".
        assertEquals("Test", test.getName());
    }

    @Test
    public void testGetFields() 
    {
        Class test = new Class("Test");
        test.addField("int", "attribute", "public");
        //The class's call to get fields is nonempty.
        assertTrue(!test.getFields().isEmpty());
        Field att = new Field("int", "attribute", "public");
        //The class's call to get fields should comtain the added field.
        assertTrue(test.getFields().contains(att));
    }

    @Test
    public void testGetRelationshipsFromOther() 
    {
        Class class1 = new Class("class1");
        Class class2 = new Class("class2");
        class1.addRelationshipFromOther(RelationshipType.REALIZATION, class2);
        Map map = new HashMap<String, RelationshipType>();
        map.put("class2", RelationshipType.REALIZATION);
        //Make sure expected map is the same as the actual returned map.
        assertEquals(map, class1.getRelationshipsFromOther());
    }

    @Test
    public void testGetRelationshipsToOther() 
    {
        Class class1 = new Class("class1");
        Class class2 = new Class("class2");
        class1.addRelationshipToOther(RelationshipType.REALIZATION, class2);
        Map map = new HashMap<String, RelationshipType>();
        map.put("class2", RelationshipType.REALIZATION);
        //Make sure expected map is the same as the actual returned map.
        assertEquals(map, class1.getRelationshipsToOther());
    }

    @Test
    public void testSetName() 
    {
        Class test = new Class("name");
        test.setName("newName");
        assertEquals("newName", test.getName());
        //Setting name to whitespace should thorw exception.
        Class empty = new Class("empty");
        assertThrows(IllegalArgumentException.class, () -> {
            empty.setName("");
        });
    }
    
    @Test
    public void testAddField() 
    {
        Class test = new Class("aName");
        test.addField("int", "att", "public");
        test.addField("String", "att2", "private");
        Set<Field> testSet = test.getFields();
        Field attrTest = new Field("int", "att", "public");
        Field attrTestTwo = new Field("String", "att2", "private");
        //The class should contain the added fields.
        assertTrue(test.getFields().contains(attrTest));
        assertTrue(test.getFields().contains(attrTestTwo));
        //Don't allow adding whitespace/empty strings.
        assertThrows(IllegalArgumentException.class, () -> {
            test.addField("", "   ","   ");
        });
    }
    
    @Test
    public void testDeleteField() 
    {
        Class test = new Class("name");
        //When field doesn't exist.
        test.addField("int", "att", "private");
        test.addField("String", "att2", "protected");
        assertFalse(test.deleteField("name"));
        test.deleteField("att");
        test.deleteField("att2");
        //Set should be empty.
        assertTrue(test.getFields().isEmpty());

        assertFalse(test.deleteField("att"));
        assertFalse(test.deleteField("att2"));
    }

    @Test
    public void testRenameField() 
    {
        Class test = new Class("name");
        test.addField("int", "att", "public"); 
        test.renameField("att", "newAtt");
        Field newAtt = new Field("int", "newAtt", "public");
        //Should contain the new field.
        assertTrue(test.getFields().contains(newAtt));
        
        //Renaming an field name that doesn't exist should return false.
        assertFalse(test.renameField("att", "att2"));

        assertFalse(test.renameField("newAtt", "newAtt"));
        //Rename to empty or whitespace expects exception.
        assertThrows(IllegalArgumentException.class, () -> {
            test.renameField("newAtt", " ");
        });
    }

    @Test
    public void testChangeFieldType()
    {
        Class test = new Class("name");
        test.addField("int", "att", "public"); 
        test.changeFieldType("att", "String");
        Field newAtt = new Field("String", "att", "public");
        assertTrue(test.getFields().contains(newAtt));
        //Changing a field type that doesn't exist should return false.
        assertFalse(test.changeFieldType("double", "att2"));
        //Rename to empty or whitespace expects exception.
        assertThrows(IllegalArgumentException.class, () -> {
            test.changeFieldType("att", "");
        });
    }

    @Test
    public void testChangeFieldAccess()
    {
        Class test = new Class("name");
        test.addField("int", "att", "public");
        //If change works, should return true.
        assertTrue(test.changeFieldAccess("att", "private"));
        assertTrue(test.changeFieldAccess("att","protected"));
        //If wrong field name given, shgould return false.
        assertFalse(test.changeFieldAccess("att1","public"));
        //Test class should only contain int att protected field.
        Field field = new Field("int", "att", "protected");
        assertTrue(test.getFields().contains(field));
        assertEquals(1, test.getFields().size());
    }

    @Test
    public void testAddRelationshipToOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        Map map = new HashMap<String, RelationshipType>();
        Map map2 = new HashMap<String, RelationshipType>();
        test.addRelationshipToOther(RelationshipType.REALIZATION, test2);
        map.put("name2", RelationshipType.REALIZATION);
        map2.put("name", RelationshipType.REALIZATION);
        //Should have the correctly oriented relationship in both classes.
        assertEquals(map, test.getRelationshipsToOther());
        assertEquals(map2, test2.getRelationshipsFromOther());
        //Should not have incorrectly oriented relationships in either class.
        assertTrue(test.getRelationshipsFromOther().isEmpty());
        assertTrue(test2.getRelationshipsToOther().isEmpty());
        //Don't allow replacing the key's value or adding second relationship between two classes.
        assertFalse(test.addRelationshipToOther(RelationshipType.AGGREGATION, test2));
        assertFalse(test2.addRelationshipToOther(RelationshipType.GENERALIZATION, test));
    }

    @Test
    public void testAddRelationshipFromOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        Map map = new HashMap<String, RelationshipType>();
        Map map2 = new HashMap<String, RelationshipType>();
        test.addRelationshipFromOther(RelationshipType.REALIZATION, test2);
        map.put("name2", RelationshipType.REALIZATION);
        map2.put("name", RelationshipType.REALIZATION);
        //Should have the correctly oriented relationship in both classes.
        assertEquals(map, test.getRelationshipsFromOther());
        assertEquals(map2, test2.getRelationshipsToOther());
        //Should not have incorrectly oriented relationships in either class.
        assertTrue(test.getRelationshipsToOther().isEmpty());
        assertTrue(test2.getRelationshipsFromOther().isEmpty());
        //Don't allow replacing the key's value.
        assertFalse(test.addRelationshipFromOther(RelationshipType.AGGREGATION, test2));
        assertFalse(test2.addRelationshipFromOther(RelationshipType.GENERALIZATION, test));

        assertThrows(IllegalArgumentException.class, () -> {
            test.addRelationshipFromOther(RelationshipType.REALIZATION, test);
        });
    }

    @Test
    public void testDeleteRelationshipToOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        //Deleting from empty should return false.
        assertFalse(test.deleteRelationshipToOther(RelationshipType.REALIZATION, test2));

        test.addRelationshipToOther(RelationshipType.REALIZATION, test2);
        test.deleteRelationshipToOther(RelationshipType.REALIZATION, test2);
        //Relationship should be gone from both classes.
        assertTrue(test.getRelationshipsToOther().isEmpty());
        assertTrue(test2.getRelationshipsFromOther().isEmpty());

        //Deleting relationship where relationship doesn't exist returns false.
        Class extra = new Class("extra");
        Class extra2 = new Class("extra2");
        extra.addRelationshipToOther(RelationshipType.REALIZATION, test);
        assertFalse(test.deleteRelationshipToOther(RelationshipType.REALIZATION, extra));
        assertFalse(extra.deleteRelationshipToOther(RelationshipType.REALIZATION, extra2));
    }

    @Test
     public void testDeleteRelationshipFromOther() 
    {
        Class test = new Class("name");
        Class test2 = new Class("name2");
        //Deleting from empty should return false.
        assertFalse(test.deleteRelationshipFromOther(RelationshipType.REALIZATION, test2));
        test.addRelationshipFromOther(RelationshipType.REALIZATION, test2);
        test.deleteRelationshipFromOther(RelationshipType.REALIZATION, test2);
        //Relationship should be gone from both classes
        assertTrue(test.getRelationshipsFromOther().isEmpty());
        assertTrue(test2.getRelationshipsToOther().isEmpty());

        //Deleting relationship where relationship doesn't exist returns false.
        Class extra = new Class("extra");
        Class extra2 = new Class("extra2");
        extra.addRelationshipFromOther(RelationshipType.REALIZATION, test);
        assertFalse(test.deleteRelationshipFromOther(RelationshipType.REALIZATION, extra));
        assertFalse(extra.deleteRelationshipFromOther(RelationshipType.REALIZATION, extra2));
    }

    @Test
    public void testEquals() 
    {
        Class test1 = new Class("name");
        test1.addField("int", "5", "private");
        Class test2 = new Class("name");
        test2.addField("int", "5", "private");
        Class extra = new Class("extra");
        test1.addRelationshipToOther(RelationshipType.REALIZATION, extra);
        test2.addRelationshipToOther(RelationshipType.REALIZATION, extra);
        Class extra1 = new Class("extra1");
        test1.addRelationshipFromOther(RelationshipType.AGGREGATION, extra1);
        test2.addRelationshipFromOther(RelationshipType.AGGREGATION, extra1);
        //Tests that equals works for two classes that have had equal items added.
        assertTrue(test1.equals(test2));
        Class test3 = null;
        assertNull(test3);
        assertFalse(test1.equals(test3));
        test3 = new Class("aClass");
        assertFalse(test1.equals(test3));
        assertFalse(test3.equals(new Field("a", "b", "public")));

        //Fail on equals if name, fields, mothods or relationships are not equal.
        //Name
        Class name1 = new Class("name1");
        Class name2 = new Class("name2");
        assertFalse(name1.equals(name2));

        //Field
        Class field1 = new Class("field");
        Class field2 = new Class("field");
        field1.addField("type", "name", "public");
        assertFalse(field1.equals(field2));

        //Method
        Class method1 = new Class("method");
        Class method2 = new Class("method");
        method1.addMethod("type", "name", new ArrayList<Parameter>(), "private");
        assertFalse(method1.equals(method2));

        //Relationships to
        Class relateTo1 = new Class("relateTo");
        Class relateTo2 = new Class("relateTo");
        Class relatedTo = new Class("relatedTo");
        relateTo1.addRelationshipToOther(RelationshipType.REALIZATION, relatedTo);
        relateTo2.addRelationshipToOther(RelationshipType.GENERALIZATION, relatedTo);
        assertFalse(relateTo1.equals(relateTo2));

        //Relationships from
        Class relateFrom1 = new Class("relateFrom");
        Class relateFrom2 = new Class("relateFrom");
        Class relatedFrom = new Class("relatedFrom");
        relateFrom1.addRelationshipFromOther(RelationshipType.AGGREGATION, relatedFrom);
        relateFrom2.addRelationshipFromOther(RelationshipType.COMPOSITION, relatedFrom);
        assertFalse(relateFrom1.equals(relateFrom2));
    }

    @Test
    public void testToString() 
    {
        Class test1 = new Class("name");
        test1.addField("attribute", "type", "public");
        Class extra = new Class("extra");
        Class extra2 = new Class("extra2");
        Class test2 = test1;
        test1.addRelationshipToOther(RelationshipType.REALIZATION, extra);
        extra2.addRelationshipFromOther(RelationshipType.REALIZATION, test1);
        Set<Field> testSet = test1.getFields();
        String result = "";
        //Tests that the strings are equal.
        assertEquals(test1.toString(), test2.toString());
    }

    @Test
    public void testGetMethods()
    {
        Class test = new Class("Test");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        test.addMethod("int", "attribute", params, "private");

        //The class's call to get methods is nonempty.
        assertTrue(!test.getMethods().isEmpty());
        Method att1 = new Method("int", "attribute", params, "private");
        test.addMethod("int", "attribute", params, "private");
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("Type", "Name"));
        Method att2 = new Method("int", "attribute", params2, "private");
        test.addMethod("int", "attribute", params2, "private");
        
        //The class's call to get methods should comtain the added methods.
        assertTrue(test.getMethods().contains(att1));
        assertTrue(test.getMethods().contains(att2));
    }

    @Test
    public void testAddMethod()
    {
        Class test = new Class("Test");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter("Type", "Name"));

        test.addMethod("int", "attribute", params, "private");
        assertTrue(test.getMethods().size() == 1);
        
        //We should not be able to add dulicate methods
        test.addMethod("int", "attribute", params, "private");
        assertFalse(test.addMethod("int", "attribute", params, "private"));
        assertTrue(test.getMethods().size() == 1);


        ArrayList<Parameter> params2 = new ArrayList<Parameter>();

        test.addMethod("int", "attribute", params2, "private");
        assertTrue(test.getMethods().size() == 2);

        ArrayList<Parameter> params3 = new ArrayList<Parameter>();
        params3.add(new Parameter("type", "ok"));
        assertTrue(test.addMethod("int", "attribute", params3, "private"));

        //Shouldn't allow adding method with name containing space.
        assertThrows(IllegalArgumentException.class, () -> {
            test.addMethod("int", "new method", params, "private");
        });

        //Should be able to add method with same type or name as another.
        assertTrue(test.addMethod("int", "differentName", params3, "private"));
        assertTrue(test.addMethod("differentType", "attribute", params3, "private"));
    }

    @Test
    public void testDeleteMethod()
    {
        Class test = new Class("Test");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter("Type", "Name"));
        //Deleting from empty set should return false.
        assertFalse(test.deleteMethod("int", "attribute", params, "private"));
        test.addMethod("int", "attribute", params, "private");
        ArrayList<Parameter> params2 = new ArrayList<Parameter>();
        params2.add(new Parameter("type", "name"));

        //Should fail to delete method if type, name, or params are wrong.
        assertFalse(test.deleteMethod("wrongType", "attribute", params2, "private"));
        assertFalse(test.deleteMethod("int", "wrongName", params2, "private"));
        assertFalse(test.deleteMethod("int", "attribute", new ArrayList<Parameter>(), "private"));

        test.deleteMethod("int", "attribute", params2, "private");
        //Call to delete method with wrong params shouldn't change methods set.
        assertTrue(test.getMethods().size() == 1);
        test.deleteMethod("int", "attribute", params, "private");
        //Call to delete methods with correct params should delete the method.
        assertTrue(test.getMethods().size() == 0);
    }

    @Test
    public void testRenameMethod()
    {
        Class test = new Class("Test");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        test.addMethod("int", "attribute", params, "protected");
        test.renameMethod("int", "attribute", params, "protected", "newName");
        Method method = new Method("int", "newName", params, "protected");
        assertTrue(test.getMethods().contains(method));

        //Should not be able to rename if the methods are the same
        assertFalse(test.renameMethod("int", "attribute", params, "protected", "newName"));
        //Renaming using old name shouldn't work.
        assertFalse(test.renameMethod("int", "attribute", params, "protected", "newName2"));
        //Shouldn't allow names containg space.
        assertThrows(IllegalArgumentException.class, () -> {
            test.renameMethod("int", "newName", params, "protected", " ");
        });
    }

    @Test
    public void testChangeMethodType()
    {
        Class test = new Class("name");
        test.addMethod("int", "att", new ArrayList<Parameter>(), "public");

        //Should return true if type changed.
        assertTrue(test.changeMethodType("int", "att", new ArrayList<Parameter>(), "public", "String"));
        
        Method oldAtt = new Method("int", "att", new ArrayList<Parameter>(), "public");
        Method newAtt = new Method("String", "att", new ArrayList<Parameter>(), "public");
        
        //Check that it contains the method with the correct type and not the old.
        assertTrue(test.getMethods().contains(newAtt));
        assertFalse(test.getMethods().contains(oldAtt));

        //Test that if the same exact method passed it, it fails. 
        assertFalse(test.changeMethodType("int", "att", new ArrayList<Parameter>(), "public", "String"));
        //Test bad calls to changeMethodType(bad name)
        assertFalse(test.changeMethodType("String", "at", new ArrayList<Parameter>(), "public", "int"));

        //Test bad calls to changeMethodType(bad access)
        assertFalse(test.changeMethodType("String", "att", new ArrayList<Parameter>(), "private", "int"));

        //Test bad calls to changeMethodType(bad type)
        assertFalse(test.changeMethodType("double", "att", new ArrayList<Parameter>(), "public", "int"));

        //Check the new att is still there
        assertTrue(test.getMethods().contains(newAtt));
    }

    @Test
    public void testChangeMethodAccess()
    {
        Class test = new Class("name");
        test.addMethod("int", "att", new ArrayList<Parameter>(), "public");
        //Should return true if access changed.
        assertTrue(test.changeMethodAccess("int", "att", new ArrayList<Parameter>(), "public", "private"));
        //Try to repeat, should fail. 
        assertFalse(test.changeMethodAccess("int", "att", new ArrayList<Parameter>(), "public", "private"));
        Method newAtt = new Method("int", "att", new ArrayList<Parameter>(), "private");
        //Changing name of field that doesn;t exist should return false.
        assertFalse(test.changeMethodAccess("int", "att1", new ArrayList<Parameter>(), "private", "protected"));
        //Should not change value if access if bad value.
        test.changeMethodAccess("int", "att", new ArrayList<Parameter>(), "private", "nonsense");
        assertTrue(test.getMethods().contains(newAtt));
    }
    
    @Test
    public void testDeleteParamter()
    {
        Class test = new Class("name");
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        params.add(new Parameter("int","num"));

        test.addMethod("int", "att", params, "public");
        assertTrue(test.deleteParameter("int", "att", params, "public", "int", "num"));
        assertTrue(test.addMethod("int", "att", params, "public"));
        assertFalse(test.deleteParameter("int", "att", params, "public", "int", "num"));

    }
}