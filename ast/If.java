package ast;
import environment.Environment;

/**
 * If is a class representing an IF statement parsed by the parser.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: If ifStatement = new If(<boolean condition>, <statement>);
 *        ifStatement.exec(<environment>);
 */
public class If extends Statement
{
    /**
     * Instance variables
     */
    private Condition cond;
    private Statement stmt;
    
    /**
     * Constructs an If object using the given boolean condition and 
     * statement.
     * Usage: If ifStatement = new If(<boolean condition>, <statement>);
     * @param c the given boolean condition
     * @param s the given statement
     * @postcondition The instance field cond is set to c.
     *              - The instance field stmt is set to s.
     */
    public If(Condition c, Statement s)
    {
        cond = c;
        stmt = s;
    }
    
    /**
     * Executes the IF statement represented by this If object using 
     * the given environment.
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        int value = cond.eval(env);
        
        if (value == 1)
        {
            stmt.exec(env);
        }
    }
    
    /**
     * Compiles the IF statement represented by this If object using 
     * the given emitter.
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        String label = "endif" + e.nextLabelID();
        
        cond.compile(e, label);
        stmt.compile(e);
        e.emit(label + ":");
    }
}
