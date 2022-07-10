package ast;
import environment.Environment;
import java.util.List;

/**
 * Block is a class representing a block statement parsed by the 
 * parser. A block statement consists of the keyword BEGIN, followed by 
 * a series of statements, followed by the keyword END.
 * @author Daniel Wang
 * @version April 30, 2020
 * Usage: Block block = new Block(<list of statements>);
 *        block.exec(<environment>);
 */
public class Block extends Statement
{
    /**
     * Instance variable
     */
    private List<Statement> stmts;
    
    /**
     * Creates a Block object using the given list of statements.
     * Usage: Block block = new Block(<list of statements>);
     * @param list the given list of statements
     * @postcondition The instance field stmts is set to list.
     */
    public Block(List<Statement> list)
    {
        stmts = list;
    }
    
    /**
     * Executes the block statement represented by this Block object 
     * using the given environment.
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        for (Statement s : stmts)
        {
            s.exec(env);
        }
    }
    
    /**
     * Compiles the block statement represented by this Block object 
     * using the given emitter.
     * @param e the given emitter
     * @postcondition MIPS code for this block statement is printed 
     *                using the given emitter.
     */
    public void compile(Emitter e)
    {
        for (Statement s : stmts)
        {
            s.compile(e);
        }
    }
}
