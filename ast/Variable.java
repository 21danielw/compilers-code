package ast;
import environment.Environment;

/**
 * Variable is a class representing a variable parsed by this parser.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: Variable variable = new Variable(<variable name>);
 *        int value = variable.eval(<environment>);
 */
public class Variable extends Expression
{
    /**
     * Instance variable
     */
    private String name;
    
    /**
     * Constructs a Variable object using the given variable name.
     * Usage: Variable variable = new Variable(<variable name>);
     * @param s the given variable name
     * @postcondition The instance field name is set to s.
     */
    public Variable(String s)
    {
        name = s;
    }
    
    /**
     * Evaluates the variable represented by this Variable object using 
     * the given environment.
     * @param env the given environment
     * @return the value of the variable represented by this Variable 
     *         object
     */
    public int eval(Environment env)
    {
        int value = env.getVariable(name);
        
        return value;
    }
    
    /**
     * Compiles the variable represented by this Variable object using 
     * the given emitter.
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        if (e.isLocalVariable(name))
        {
            int offset = e.getOffset(name);
            
            e.emit("# Loads " + "local variable " + name + " into $v0");
            e.emit("lw $v0 " + offset + "($sp)");
            e.emit("");
        }
        else
        {
            e.emit("# Loads " + "var" + name + " into $v0");
            e.emit("la $t0 " + "var" + name);
            e.emit("lw $v0 ($t0)");
            e.emit("");
        }
    }
}
