package ast;
import environment.Environment;

/**
 * WritelnTimes is a class representing a WRITELNTIMES statement parsed 
 * by the parser. A WRITELNTIMES statement prints the value of its 
 * first parameter for a number of times given by its second parameter.
 * @author Daniel Wang
 * @version May 19, 2020
 * Usage: WritelnTimes writelnTimes = new WritelnTimes(<expression>, 
 *            <expression>);
 *        writelnTimes.exec(<environment>);
 */
public class WritelnTimes extends Statement
{
    /**
     * Instance variables
     */
    private Expression exp;
    private Expression times;
    
    /**
     * Constructs a WritelnTimes object using the given expressions. 
     * The first expression represents the value to be printed out, and 
     * the second expression represents the number of times to print 
     * out the value.
     * @param e an expression representing the value to be printed out
     * @param t an expression representing the number of times to print 
     *          out the value
     * @postcondition The instance field exp is set to e.
     *              - The instance field times is set to t.
     */
    public WritelnTimes(Expression e, Expression t)
    {
        exp = e;
        times = t;
    }
    
    /**
     * Executes the WRITELNTIMES statement represented by this 
     * WritelnTimes object using the given environment.
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        int value = exp.eval(env);
        int numTimes = times.eval(env);
        
        for (int i = 0; i < numTimes; i++)
        {
            System.out.println(value);
        }
    }
}
