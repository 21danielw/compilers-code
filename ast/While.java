package ast;
import environment.Environment;

/**
 * While is a class representing a WHILE statement parsed by the parser.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: While whileStatement = new While(<boolean condition>, 
 *            <statement>);
 *        whileStatement.exec(<environment>);
 */
public class While extends Statement
{
    /**
     * Instance variables
     */
    private Condition cond;
    private Statement stmt;
    
    /**
     * Constructs a While object using the given boolean condition and 
     * statement.
     * @param c the given boolean condition
     * @param s the given statement
     * @postcondition The instance field cond is set to c.
     *              - The instance field stmt is set to s.
     */
    public While(Condition c, Statement s)
    {
        cond = c;
        stmt = s;
    }
    
    /**
     * Executes the WHILE statement represented by this While object 
     * using the given environment.
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        boolean ended = false;
        while (!ended)
        {
            int value = cond.eval(env);
            
            if (value == 0)
            {
                ended = true;
            }
            else
            {
                stmt.exec(env);
            }
        }
    }
    
    /**
     * Compiles the WHILE statement represented by this While object 
     * using the given emitter.
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        String startLabel = "startwhile" + e.nextLabelID();
        String endLabel = "endwhile" + e.nextLabelID();
        
        e.emit(startLabel + ":");
        cond.compile(e, endLabel);
        stmt.compile(e);
        e.emit("# Jumps to " + startLabel);
        e.emit("j " + startLabel);
        e.emit("");
        e.emit(endLabel + ":");
    }
}
