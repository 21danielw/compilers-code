package ast;
import environment.Environment;

/**
 * Condition is a class representing a boolean condition parsed by the 
 * parser. The boolean condition is equals, not equals, less than, 
 * greater than, less than or equals, or greater than or equals.
 * @author Daniel Wang
 * @version April 30, 2020
 * Usage: Condition condition = new Condition(<condition type>, 
 *            <left expression>, <right expression>);
 */
public class Condition extends Expression
{
    /**
     * Instance variables
     */
    private String relop;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * Constructs a Condition object using the given boolean condition, 
     * left expression, and right expression.
     * Usage: Condition condition = new Condition(<condition type>, 
     *            <left expression>, <right expression>);
     * @param s the given boolean condition
     * @param e1 the given left expression
     * @param e2 the given right expression
     * @postcondition The instance relop is set to s.
     *              - The instance exp1 is set to e1.
     *              - The instance exp2 is set to e2.
     */
    public Condition(String s, Expression e1, Expression e2)
    {
        relop = s;
        exp1 = e1;
        exp2 = e2;
    }
    
    /**
     * Evaluates the boolean condition represented by this Condition 
     * object using the given environment.
     * @param env the given environment
     * @return 0 if the boolean condition evaluates to false, 
     *         1 otherwise
     */
    public int eval(Environment env)
    {
        int value1 = exp1.eval(env);
        int value2 = exp2.eval(env);
        
        boolean b;
        
        if (relop.equals("="))
        {
            b = value1 == value2;
        }
        else if (relop.equals("<>"))
        {
            b = value1 != value2;
        }
        else if (relop.equals("<"))
        {
            b = value1 < value2;
        }
        else if (relop.equals(">"))
        {
            b = value1 > value2;
        }
        else if (relop.equals("<="))
        {
            b = value1 <= value2;
        }
        else
        {
            b = value1 >= value2;
        }
        
        if (b)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Compiles the boolean condition represented by this Condition 
     * object using the given emitter and label.
     * @param e the given emitter
     * @param label the given label
     * @postcondition MIPS code for this boolean condition is printed 
     *                using the given emitter and label.
     */
    public void compile(Emitter e, String label)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        
        if (relop.equals("="))
        {
            e.emit("# If $t0 <> $v0 then jump to " + label);
            e.emit("bne $t0 $v0 " + label);
        }
        else if (relop.equals("<>"))
        {
            e.emit("# If $t0 = $v0 then jump to " + label);
            e.emit("beq $t0 $v0 " + label);
        }
        else if (relop.equals("<"))
        {
            e.emit("# If $t0 >= $v0 then jump to " + label);
            e.emit("bge $t0 $v0 " + label);
        }
        else if (relop.equals(">"))
        {
            e.emit("# If $t0 <= $v0 then jump to " + label);
            e.emit("ble $t0 $v0 " + label);
        }
        else if (relop.equals("<="))
        {
            e.emit("# If $t0 > $v0 then jump to " + label);
            e.emit("bgt $t0 $v0 " + label);
        }
        else
        {
            e.emit("# If $t0 < $v0 then jump to " + label);
            e.emit("blt $t0 $v0 " + label);
        }
        
        e.emit("");
    }
}
