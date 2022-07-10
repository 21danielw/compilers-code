package ast;
import environment.Environment;

/**
 * Number is a class representing an integer parsed by the parser.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: Number number = new Number(<integer>);
 *        int value = number.eval(<environment>);
 */
public class Number extends Expression
{
    /**
     * Instance variable
     */
    private int value;
    
    /**
     * Constructs a Number object using the given integer.
     * Usage: Number number = new Number(<integer value>);
     * @param num the given integer
     * @postcondition The instance field value is set to num.
     */
    public Number(int num)
    {
        value = num;
    }
    
    /**
     * Evaluates the integer represented by this Number object using 
     * the given environment.
     * @param env the given environment
     * @return the integer represented by this Number object
     */
    public int eval(Environment env)
    {
        return value;
    }
    
    /**
     * Compiles the integer represented by this Number object using 
     * the given emitter.
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        e.emit("# Loads " + value + " into $v0");
        e.emit("li $v0 " + value);
        e.emit("");
    }
}
