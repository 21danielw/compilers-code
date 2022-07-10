package environment;
import ast.ProcedureDeclaration;
import java.util.Map;
import java.util.HashMap;

/**
 * Environment is a class representing an environment that a program 
 * runs in. The environment may be either the global environment or a 
 * local environment associated with a procedure.
 * @author Daniel Wang
 * @version March 30, 2020
 * Usage: Environment environment = new Environment(<parent environment>);
 *        environment.setVariable(<variable name>, <integer>);
 */
public class Environment
{
    /**
     * Instance variables
     */
    private Environment parent;
    private Map<String, Integer> variableMap;
    private Map<String, ProcedureDeclaration> procedureMap;
    
    /**
     * Constructs a new Environment object with the given Environment 
     * object as its parent.
     * @param p the given Environment object
     * @postcondition The instance variable parent is set to p.
     *              - The instance variable variableMap is set to an 
     *                empty HashMap with String keys and Integer values.
     *              - The instance variable procedureMap is set to an 
     *                empty HashMap with String keys and values that 
     *                are ProcedureDeclaration objects.
     */
    public Environment(Environment p)
    {
        parent = p;
        variableMap = new HashMap<String, Integer>();
        procedureMap = new HashMap<String, ProcedureDeclaration>();
    }
    
    /**
     * Gets the parent of this Environment object.
     * @return the instance variable parent
     */
    public Environment getParent()
    {
        return parent;
    }
    
    /**
     * Declares a variable in this environment.
     * @param variable a string representing the variable's name
     * @param value an integer representing the variable's value
     * @postcondition The given string is associated with the given 
     *                integer in variableMap.
     */
    public void declareVariable(String variable, int value)
    {
        variableMap.put(variable, value);
    }
    
    /**
     * Sets a variable to a value.
     * @param variable a string representing the variable's name
     * @param value an integer representing the variable's new value
     * @postcondition If variableMap in this environment contains the 
     *                given string as a key, the given string is 
     *                associated with the given integer in variableMap 
     *                in this environment.
     *                Otherwise, the given string is associated with 
     *                the given integer in variableMap in the global 
     *                environment.
     */
    public void setVariable(String variable, int value)
    {
        if (parent == null)
        {
            variableMap.put(variable, value);
        }
        else
        {
            if (variableMap.containsKey(variable))
            {
                variableMap.put(variable, value);
            }
            else
            {
                Map<String, Integer> pVariableMap = parent.variableMap;
                
                pVariableMap.put(variable, value);
            }
        }
    }
    
    /**
     * Gets the value of a variable.
     * @param variable a string representing the variable's name
     * @return the integer associated with the given string in 
     *         variableMap in this environment if variableMap in this 
     *         environment contains the given string as a key
     *       - the integer associated with the given string in 
     *         variableMap in the global environment otherwise
     */
    public int getVariable(String variable)
    {
        if (parent == null)
        {
            int value = variableMap.get(variable);
            
            return value;
        }
        else
        {
            if (variableMap.containsKey(variable))
            {
                int value = variableMap.get(variable);
                
                return value;
            }
            else
            {
                Map<String, Integer> pVariableMap = parent.variableMap;
                
                int value = pVariableMap.get(variable);
                
                return value;
            }
        }
    }
    
    /**
     * Associates a procedure name with a procedure declaration.
     * @param name a string representing the procedure name
     * @param pd a ProcedureDeclaration object representing the 
     *           procedure declaration
     * @postcondition The given string is associated with the given 
     *                ProcedureDeclaration object in procedureMap in 
     *                the global environment.
     */
    public void setProcedure(String name, ProcedureDeclaration pd)
    {
        if (parent == null)
        {
            procedureMap.put(name, pd);
        }
        else
        {
            Map<String, ProcedureDeclaration> pPM = parent.procedureMap;
            
            pPM.put(name, pd);
        }
    }
    
    /**
     * Gets the procedure declaration associated with a procedure name.
     * @param name a string representing the procedure name
     * @return the ProcedureDeclaration object associated with the 
     *         given string in procedureMap in the global environment.
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        if (parent == null)
        {
            ProcedureDeclaration pd = procedureMap.get(name);
            
            return pd;
        }
        else
        {
            Map<String, ProcedureDeclaration> pPM = parent.procedureMap;
            
            ProcedureDeclaration pd = pPM.get(name);
            
            return pd;
        }
    }
}
