package ast;
import environment.Environment;

/**
 * Writeln is a class representing a WRITELN statement parsed by the 
 * parser. A WRITELN statement prints the value of its parameter onto 
 * the screen.
 * @author Daniel Wang
 * @version March 21, 2020
 * Usage: Writeln writeln = new Writeln(<expression>);
 *        writeln.exec(<environment>);
 */
public class Writeln extends Statement
{
    /**
     * Instance variable
     */
    private Expression exp;
    
    /**
     * Constructs a Writeln object using the given expression.
     * Usage: Writeln writeln = new Writeln(<expression>);
     * @param e the given expression
     * @postcondition The instance field exp is set to e.
     */
    public Writeln(Expression e)
    {
        exp = e;
    }
    
    /**
     * Executes the WRITELN statement represented by this Writeln 
     * object using the given environment.
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        int value = exp.eval(env);
        
        System.out.println(value);
    }
    
    /**
     * Compiles the WRITELN statement represented by this Writeln 
     * object using the given emitter.
     * @param e the given emitter
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("# Writes $v0 and a newline to the screen");
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0 newline");
        e.emit("li $v0 4");
        e.emit("syscall");
        e.emit("");
    }
}
