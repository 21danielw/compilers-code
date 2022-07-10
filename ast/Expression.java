package ast;
import environment.Environment;

/**
 * Expression is an abstract class representing an expression parsed 
 * by the parser. An expression is a number, a variable, a binary 
 * arithmetic operation, or a boolean condition.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: Instantiate a subclass of this class.
 */
public abstract class Expression
{
    /**
     * Evaluates the expression represented by this Expression object 
     * using the given environment.
     * @param env the given environment
     * @return an integer representing the value of the expression
     */
    public abstract int eval(Environment env);
    
    /**
     * Compiles the expression represented by this Expression object 
     * using the given emitter
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!!!!!");
    }
}
