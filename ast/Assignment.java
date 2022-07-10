package ast;
import environment.Environment;

/**
 * Assignment is a class representing a variable assignment statement 
 * parsed by the parser.
 * @author Daniel Wang
 * @version April 30, 2020
 * Usage: Assignment assignment = new Assignment(<variable name>, 
 *            <expression>);
 *        assignment.exec(<environment>);
 */
public class Assignment extends Statement
{
    /**
     * Instance variables
     */
    private String var;
    private Expression exp;
    
    /**
     * Constructs an Assignment object using the given variable name 
     * and expression.
     * Usage: Assignment assignment = new Assignment(<variable name>, 
     *            <expression>);
     * @param s the given variable name
     * @param e the given expression
     * @postcondition The instance field var is set to s.
     *              - The instance field exp is set to e.
     */
    public Assignment(String s, Expression e)
    {
        var = s;
        exp = e;
    }
    
    /**
     * Executes the variable assignment statement represented by this 
     * Assignment object using the given environment.
     * @param env the given environment
     * @postcondition The variable var in the environment env is set to 
     *                the value of the expression exp.
     */
    public void exec(Environment env)
    {
        int value = exp.eval(env);
        
        env.setVariable(var, value);
    }
    
    /**
     * Compiles the variable assignment statement represented by this 
     * Assignment object using the given emitter.
     * @param e the given emitter
     * @postcondition MIPS code for this variable assignment statement 
     *                is printed using the given emitter.
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        
        if (e.isLocalVariable(var))
        {
            int offset = e.getOffset(var);
            
            e.emit("# Stores $v0 into " + "local variable " + var);
            e.emit("sw $v0 " + offset + "($sp)");
            e.emit("");
        }
        else
        {
            e.emit("# Stores $v0 into " + "var" + var);
            e.emit("la $t0 " + "var" + var);
            e.emit("sw $v0 ($t0)");
            e.emit("");
        }
    }
}
