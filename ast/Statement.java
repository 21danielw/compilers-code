package ast;
import environment.Environment;

/**
 * Statement is an abstract class representing a statement parsed by 
 * the parser. A statement is a WRITELN statement, a variable 
 * assignment statement, a block, an if statement, or a while loop.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: Instantiate a subclass of this class.
 */
public abstract class Statement
{
    /**
     * Executes the statement represented by this Statement object 
     * using the given environment
     * @param env the given environment
     */
    public abstract void exec(Environment env);
    
    /**
     * Compiles the statement represented by this Statement object 
     * using the given emitter.
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!!!!!");
    }
}
