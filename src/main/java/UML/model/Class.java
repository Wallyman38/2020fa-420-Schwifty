package UML.model;
/*
    Author: Tyler, Cory, Dominic, Drew, Chris. 
    Date: 09/08/2020
    Purpose: This class defines how classes the user creates will function. A class 
    needs at least a name to be created and allows for the addition of attributes that are
    stored in a set. Relationships to the this class, as well as relationships to other classes
    are stored in maps. 
*/
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Holds the options for relationships between classes.


public class Class {

    // The name of the class object.
    private String name;
    // A set containing the fields of a class object.
    private Set<Field> fields;
    // The relationships from this class object to another.
    private Map<String, RelationshipType> relationshipsToOther;
    // The relationships from another class object to this one.
    private Map<String, RelationshipType> relationshipsFromOther;
    // A set containing the class's methods.
    private Set<Method> methods;

    /**
     * Constructs a class object that takes in a parameter for the name of the
     * class.
     */
    public Class(String name) throws IllegalArgumentException {
        // Don't allow empty string/only whitespace
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("The class name cannot cantain a space.");
        }
        this.name = name;
        this.fields = new HashSet<Field>();
        this.relationshipsToOther = new HashMap<String, RelationshipType>();
        this.relationshipsFromOther = new HashMap<String, RelationshipType>();
        this.methods = new HashSet<Method>();
    }

    /**
     * Returns the name of the class object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a set of the fields of the class object.
     */
    public Set<Field> getFields() {
        return this.fields;
    }

    /**
     * Returns a set of the methods of the class object.
     */
    public Set<Method> getMethods() {
        return this.methods;
    }

    /**
     * Returns the relationships from this class object to another.
     */
    public Map<String, RelationshipType> getRelationshipsToOther() {
        return this.relationshipsToOther;
    }

    /**
     * Returns the relationships from another class object to this one.
     */
    public Map<String, RelationshipType> getRelationshipsFromOther() {
        return this.relationshipsFromOther;
    }

    /**
     * Changes the name of the class object.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("The class name cannot be blank.");
        }
        if (name.contains(" ")) {
            throw new IllegalArgumentException("The class name cannot contains spaces.");
        }
        this.name = name;
    }

    /**
     * Adds a field the the class object.  If the field name is already used, return false.
     */
    public boolean addField(String type, String name) throws IllegalArgumentException {
        // If name is found, return false...atrribute already created
        for (Field f : fields) {
            if (f.getName().equals(name)) {
                return false;
            }
        }
        Field newField = new Field(type, name);
        fields.add(newField);
        return true;
    }

    /**
     * Deletes a field from the class object.  If the field isn't found, return false.
     */
    public boolean deleteField(String name) {
        for (Field f : fields) {
            if (f.getName().equals(name)) {
                fields.remove(f);
                return true;
            }
        }
        return false;
    }

    /**
     * Renames a field of the class object.  If it cannot be ranmed, return false.
     */
    public boolean renameField(String oldName, String newName) throws IllegalArgumentException {
        for (Field a : fields) {
            //If the new name name already exists, return false.
            if (a.getName().equals(newName)) {
                return false;
            }
        }
        for (Field f : fields) {
            if (f.getName().equals(oldName)) {
                // Must remove and add so that the Field has correct hashcode.
                String type = f.getType();
                this.fields.remove(f);
                Field toAdd = new Field(type, newName);
                this.fields.add(toAdd);
                return true;
            }
        }
        //If the field wasn't found, return false.
        return false;
    }

    /**
     * Changes the type of a field of the class object.  If the field cannot be found, return false.
     */
    public boolean changeFieldType(String newType, String name) throws IllegalArgumentException {
        for (Field a : fields) {
            if (a.getName().equals(name)) {
                // Must remove and add so that the Field has correct hashcode
                Field toAdd = new Field(newType, name);
                this.fields.remove(a);
                this.fields.add(toAdd);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds method with parameters.  If the method cannot be added, return false.
     */
    public boolean addMethod(String type, String name, ArrayList<Parameter> params) throws IllegalArgumentException {
        Method newMethod = new Method(type, name, params);
        for (Method m : methods) {
            // If the number of parameters doesn't match, no need to check if the parameters are equal.
            if ((params.size() == m.getParams().size())) {
                //If the return type and method name are equal, check if parameters are equal.
                if (m.getType().equals(newMethod.getType()) && m.getName().equals(newMethod.getName())) {
                    boolean typesMatch = true;
                    //Iterate through parameters to check if they are equal.
                    for (int count = 0; count < params.size(); count++) {
                        if (!params.get(count).getType().equals(m.getParams().get(count).getType())) {
                            typesMatch = false;
                        }
                    }
                    //If the method to be added is exactly equal to another, don't add and return false.
                    if (typesMatch)
                        return false;
                }
            }
        }
        methods.add(newMethod);
        return true;
    }

    /**
     * Deletes a method from the class.  If it is not deleted, return false.
     */
    //////////Can rewrite in one line as long as method.equals() works correctly.
    public boolean deleteMethod(String type, String name, ArrayList<Parameter> params) {
        Method method = new Method(type, name, params);
        for (Method m : methods) {
            if (m.getType().equals(type) && m.getName().equals(name) && m.getParams().equals(params)) {
                //This should always be true in this case.
                return methods.remove(method);
            }
        }
        return false;
    }

    /**
     * Renames method with parameters.  If it cannot be renamed, return false.
     */
    public boolean renameMethod(String type, String oldName, ArrayList<Parameter> params, String newName) throws IllegalArgumentException {
        Method methodNew = new Method(type, newName, params);
        for (Method m : methods) {
            //If that exact method already exists, return false.
            if (m.equals(methodNew)) {
                return false;
            }
        }
        Method method = new Method(type, oldName, params);
        boolean deleted = methods.remove(method);
        if (deleted) {
            addMethod(type, newName, params);
        }
        //Returns false if method wasn't found in methods, true if it was found and renamed.
        return deleted;
    }

    /**
     * Chnages the return type of a method.  If it cannot be changed, return false.
     */
    public boolean changeMethodType(String oldType, String methodName, ArrayList<Parameter> params, String newType) throws IllegalArgumentException {
        Method methodNew = new Method(newType, methodName, params);
        for (Method m : methods) {
            //If that exact method already exists, return false.
            if (m.equals(methodNew)) {
                return false;
            }
        }
        Method method = new Method(oldType, methodName, params);
        boolean deleted = methods.remove(method);
        if (deleted) {
            addMethod(newType, methodName, params);
        }
        //Returns false if method wasn't found in methods, true if it was found and retyped.
        return deleted;
    }

    /**
     * Adds a relationship from this class object to another.  Returns false if the relationship couldn't be created.
     */
    public boolean addRelationshipToOther(RelationshipType relation, Class aClass) throws IllegalArgumentException {
        // If trying to create a relationship with a class and itself, throw exception.
        if (this.equals(aClass)) {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");

        }
        // If relationship already exists between the two classes, don't overwrite.
        if (!relationshipsToOther.containsKey(aClass.name) && !relationshipsFromOther.containsKey(aClass.name)) {
            relationshipsToOther.put(aClass.name, relation);
            aClass.relationshipsFromOther.put(this.name, relation);
            return true;
        }
        //This will occur if there is already a relationship between the two classes.
        return false;
    }

    /**
     * Adds a relationship from another class object to this one.  Returns false if the relationship couldn't be created.
     */
    public boolean addRelationshipFromOther(RelationshipType relation, Class aClass) throws IllegalArgumentException {
        // If trying to create a relationship with a class and itself, throw exception.
        if (this.equals(aClass)) {
            throw new IllegalArgumentException("A class cannot have a relationship with itself.");

        }
        // If relationship already exists between the two classes, don't overwrite.
        if (!relationshipsFromOther.containsKey(aClass.name) && !relationshipsToOther.containsKey(aClass.name)) {
            relationshipsFromOther.put(aClass.name, relation);
            aClass.relationshipsToOther.put(this.name, relation);
            return true;
        }
        //This will occur if there is already a relationship between the two classes.
        return false;
    }

    /**
     * Deletes a relationship from this class object to another.  Returns false if a relationship cannot be deleted.
     */
    public boolean deleteRelationshipToOther(RelationshipType relation, Class aClass) {
        //Remove relationship this class has to another.
        boolean removedToOther = relationshipsToOther.remove(aClass.name, relation);
        //Remove a relationship another class has from this one.
        boolean removedFromOther = aClass.relationshipsFromOther.remove(this.name, relation);
        //True if both cases above were true.
        return removedToOther && removedFromOther;
    }

    /**
     * Deletes a relationship from another class object to this one.  Returns false if a relationship cannot be deleted.
     */
    public boolean deleteRelationshipFromOther(RelationshipType relation, Class aClass) {
        //Remove a relationship this class has from another.
        boolean removedFromOther = relationshipsFromOther.remove(aClass.name, relation);
        //Remove relationship another class has to this one.
        boolean removedToOther = aClass.relationshipsToOther.remove(this.name, relation);
        //True if both cases above were true.
        return removedFromOther && removedToOther;
    }

    /**
     * Returns true if two class object are equal and false otehrwise.
     */
    public boolean equals(Object other) {
        boolean result = false;
        if (this == other) {
            result = true;
        } else if (other == null) {
            result = false;
        } else if (!(other instanceof Class)) {
            result = false;
        } else {
            Class object = (Class) other;
            boolean attEqual = true;
            // Check if both sets contain the same objects since equals() won't work
            for (Field att : this.fields) {
                if (!object.fields.contains(att)) {
                    attEqual = false;
                }
            }
            for (Field att : object.fields) {
                if (!this.fields.contains(att)) {
                    attEqual = false;
                }
            }
            if (object.name.equals(this.name) && attEqual
                    && object.relationshipsToOther.equals(this.relationshipsToOther)
                    && object.relationshipsFromOther.equals(this.relationshipsFromOther)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns a string representation of a class object.
     */
    public String toString() {
        String result = "";
        result += "Class name: " + this.name + "\n";
        result += "------------------------------\n";
        result += "Field Names: \n";
        if (!fields.isEmpty()) {
            for (Field field : fields) {
                result += field.toString();
                result += "\n";
            }
        }
        result += "------------------------------\n";

        result += "Methods:  \n";
        if (!methods.isEmpty()) {
            for (Method m : methods) {
                result += m.toString();
                result += "\n";
            }
        }
        result += "\n------------------------------\n";
        result += "Relationships To Others: \n" + relationshipsToOther.toString() + "\n";
        result += "Relationships From Others: \n" + relationshipsFromOther.toString();
        return result;
    }
}