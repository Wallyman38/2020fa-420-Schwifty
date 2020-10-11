package UML.controllers;
/*
    Author: Chris, Cory, Dominic, Drew, Tyler. 
    Date: 10/06/2020
    Purpose: Creates the action listeners for the field and method buttons.
 */
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import UML.model.Class;
import UML.model.Store;
import UML.views.View;


public class FieldClickController implements ActionListener {
    private Store store;
    private View view;
    private Controller controller;

    public FieldClickController(Store s, View v, Controller c) {
        this.view = v;
        this.store = s;
        this.controller = c;
    }

    public void actionPerformed(ActionEvent e) {
        // Create a drop down list of created classes.
        ArrayList<String> classList = store.getClassList();

        String className = view.getChoiceFromUser("Create Field for this class", "Create atrribute", classList);

        String cmd = e.getActionCommand();
        if (cmd.equals("CreateField")) {
            String type = view.getInputFromUser("Type: ");
            String name = view.getInputFromUser("Name: ");
            // Add field to the class using received params.
            controller.createField(className, type, name);

        } else if (cmd.equals("DeleteField")) {
            // Get class from storage.
            Class classToDeleteFrom = store.findClass(className);

            // Get the atrributes to be placed in a combo box.
            ArrayList<String> fieldList = store.getFieldList(classToDeleteFrom.getFields());

            // Get field to delete.
            String field = view.getChoiceFromUser("Delete this atrribute", "Delete atrribute", fieldList);
            String[] f = field.split("");
            controller.deleteField(className, f[1]);

        } else if (cmd.equals("RenameField")) {
            // Get class from storage.
            Class classToRenameFrom = store.findClass(className);

            // Get a list of atrributes from the class.
            ArrayList<String> fieldList = store.getFieldList(classToRenameFrom.getFields());

            // Get field to rename.
            String field = view.getChoiceFromUser("Rename this field", "Rename atrribute", fieldList);

            // Open text input for new name.
            String newField = view.getInputFromUser("Enter new field name: ");
            // Old field. 
            String[] att = field.split(" ");
            // Rename the field.
            controller.renameField(className, att[1], newField);

            
        } else if (cmd.equals("ChangeFieldType")) {
            // Get class from storage.
            Class classToChangeTypeFrom = store.findClass(className);

            // Get a list of atrributes from the class.
            ArrayList<String> fieldList = store.getFieldList(classToChangeTypeFrom.getFields());

            // Get field to change it's type.
            String field = view.getChoiceFromUser("Change this field's type", "Change Field Type", fieldList);

            // get the new type from the user and let the store know it can change.
            String newType = view.getInputFromUser("New type: ");
            String[] att = field.split(" ");
            controller.changeFieldType(className, newType, att[1]);

        } else if (cmd.equals("CreateMethod")) {
            // Get Type from user.
            String returnType = view.getInputFromUser("Return Type: ");
            // Get name from user.
            String name = view.getInputFromUser("Method Name: ");
            // Get number of parameters from user.
            boolean valid = false;
            int paramNum = getNumberFromInput(view.getInputFromUser("Number of Parameters: "));
            while(!valid)
            {
                if(paramNum < 0)
                {
                    JOptionPane.showMessageDialog(new JFrame(), "Specify a nonnegative number of parameters.", "Error", JOptionPane.ERROR_MESSAGE);
                    paramNum = getNumberFromInput(view.getInputFromUser("Number of Parameters: "));
                }
                else
                {
                    valid = true;
                }
            }

            ArrayList<String> params = new ArrayList<String>();

            for (int count = 0; count < paramNum; ++count) {
                String paramType = view.getInputFromUser("Parameter Type: ");
                String paramName = view.getInputFromUser("Parameter Name: ");
                params.add(paramType + " " + paramName);
            }
            // Add field to the class using received params.
            controller.createMethod(className, returnType, name, params);
        } 
        else if (cmd.equals("DeleteMethod")) {
            // Get a list of methods to show user in a combo box.
            ArrayList<String> methods = store.getMethodList(store.findClass(className).getMethods());
            // Get their choice
            String methodString = view.getChoiceFromUser("Delete method", "DeleteMethod", methods);
            //Get the needed componets from the method string
            String[] splitStr = methodString.split(" ");
            String returnType = splitStr[1];
            String methodName = splitStr[2];
            
            controller.deleteMethod(className, returnType, methodName, store.getMethodParamString(className, methodString));
        } 
        else if (cmd.equals("RenameMethod")) {
            ArrayList<String> methodList = store.getMethodList(store.findClass(className).getMethods());
            String methodString = view.getChoiceFromUser("Rename this method", "Rename method", methodList);

            String newMethod = view.getInputFromUser("Enter new method name: ");
            
            String[] splitStr = methodString.split(" ");
            String returnType = splitStr[1];
            String methodName = splitStr[2];

            controller.renameMethod(className, returnType, methodName, store.getMethodParamString(className, methodString), newMethod);
        }
    }

    private int getNumberFromInput(String input) 
    {
        int paramNum;
        // Ensure we get a number, defaults to zero on bad input.
        try 
        {
            paramNum = Integer.parseInt(input);
        }
        catch (NumberFormatException e) 
        {
            paramNum = 0;
        }
        return paramNum;
    }
}