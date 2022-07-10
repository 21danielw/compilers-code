package ast;
import environment.Environment;

/**
 * BinOp is a class representing a binary arithmetic operation parsed 
 * by the parser. The operation is addition, subtraction, 
 * multiplication, or division.
 * @author Daniel Wang
 * @version April 30, 2020
 * Usage: BinOp binOp = new BinOp(<operation type>, <left expression>, 
 *            <right expression>);
 *        int value = binOp.eval(<environment>);
 */
public class BinOp extends Expression
{
    /**
     * Instance variables
     */
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * Constructs a BinOp object using the given operation, left 
     * expression, and right expression.
     * Usage: BinOp binOp = new BinOp(<operation type>, 
     *            <left expression>, <right expression>);
     * @param s the given operation
     * @param e1 the given left expression
     * @param e2 the given right expression
     * @postcondition The instance field op is set to s.
     *              - The instance field exp1 is set to e1.
     *              - The instance field exp2 is set to e2.
     */
    public BinOp(String s, Expression e1, Expression e2)
    {
        op = s;
        exp1 = e1;
        exp2 = e2;
    }
    
    /**
     * Evaluates the operation represented by this BinOp object using 
     * the given environment.
     * @param env the given environment
     * @return the value of the operation represented by this BinOp 
     *         object
     */
    public int eval(Environment env)
    {
        int value1 = exp1.eval(env);
        int value2 = exp2.eval(env);
        
        int value;
        
        if (op.equals("+"))
        {
            value = value1 + value2;
        }
        else if (op.equals("-"))
        {
            value = value1 - value2;
        }
        else if (op.equals("*"))
        {
            value = value1 * value2;
        }
        else
        {
            value = value1 / value2;
        }
        
        return value;
    }
    
    /**
     * Compiles the operation represented by this BinOp object using 
     * the given emitter.
     * @param e the given emitter
     * @postcondition MIPS code for this operation is printed using 
     *                the given emitter.
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        
        if (op.equals("+"))
        {
            e.emit("# Stores $t0 + $v0 into $v0");
            e.emit("addu $v0 $t0 $v0");
        }
        else if (op.equals("-"))
        {
            e.emit("# Stores $t0 - $v0 into $v0");
            e.emit("subu $v0 $t0 $v0");
        }
        else if (op.equals("*"))
        {
            e.emit("# Stores $t0 * $v0 into $v0");
            e.emit("mult $t0 $v0");
            e.emit("mflo $v0");
        }
        else
        {
            e.emit("# Stores $t0 / $v0 into $v0");
            e.emit("div $t0 $v0");
            e.emit("mflo $v0");
        }
        
        e.emit("");
    }
}
