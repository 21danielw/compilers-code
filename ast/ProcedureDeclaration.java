package ast;
import environment.Environment;
import java.util.List;

/**
 * ProcedureDeclaration is a class representing a procedure declaration 
 * parsed by the parser. A procedure declaration consists of a header 
 * containing the name of the procedure and the parameters of the 
 * procedure, followed by a body consisting of a single statement.
 * @author Daniel Wang
 * @version March 30, 2020
 */
public class ProcedureDeclaration extends Statement
{
    /**
     * Instance variables
     */
    private String name;
    private List<String> parameterNames;
    private Statement stmt;
    private List<String> localVariableNames;
    
    /**
     * Constructs a ProcedureDeclaration object using the given 
     * procedure name, list of parameter names, and procedure body.
     * @param s a string representing the procedure name
     * @param pn a list of strings representing the parameter names
     * @param st a Statement object representing the procedure body
     * @param lvn a list of strings representing the local variable 
     *            names
     * @postcondition The instance variable name is set to s.
     *              - The instance variable parameterNames is set to pn.
     *              - The instance variable stmt is set to st.
     *              - The instance variable localVariableNames is set 
     *                to lvn.
     */
    public ProcedureDeclaration(String s, List<String> pn, 
            Statement st, List<String> lvn)
    {
        name = s;
        parameterNames = pn;
        stmt = st;
        localVariableNames = lvn;
    }
    
    /**
     * Executes the procedure declaration represented by this 
     * ProcedureDeclaration object.
     * @param env the given environment
     * @postcondition The procedure declaration represented by this 
     *                ProcedureDeclaration object is added to the 
     *                environment env.
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }
    
    /**
     * Gets the body of this procedure.
     * @return a statement representing the body of this procedure
     */
    public Statement getStatement()
    {
        return stmt;
    }
    
    /**
     * Gets the names of the parameters of this procedure.
     * @return a list containing the names of the parameters
     */
    public List<String> getParameterNames()
    {
        return parameterNames;
    }
    
    /**
     * Compiles the procedure declaration represented by this 
     * ProcedureDeclaration object using the given emitter.
     * @param e the given emitter
     * @postcondition MIPS code for this procedure declaration is 
     *                printed using the given emitter.
     */
    public void compile(Emitter e)
    {
        e.emit("proc" + name + ":");
        e.emit("# Load 0 into $v0");
        e.emit("li $v0 0");
        e.emit("");
        e.emitPush("$v0");
        
        for (String localVariable : localVariableNames)
        {
            e.emit("# Load 0 into $v0");
            e.emit("li $v0 0");
            e.emit("");
            e.emitPush("$v0");
        }
        
        e.setProcedureContext(this);
        
        stmt.compile(e);
        
        e.clearProcedureContext();
        
        for (String localVariable : localVariableNames)
        {
            e.emitPop("$a0");
        }
        
        e.emit("# Pops the return value off the stack");
        e.emitPop("$v0");
        
        e.emit("# Jump return to $ra");
        e.emit("jr $ra");
        e.emit("");
    }
    
    /**
     * Gets the name of this procedure.
     * @return a String representing the name of this procedure
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the names of the local variables of this procedure.
     * @return a list containing the names of the local variables
     */
    public List<String> getLocalVariableNames()
    {
        return localVariableNames;
    }
}
