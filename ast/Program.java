package ast;
import environment.Environment;
import java.util.List;

/**
 * Program is a class representing a program parsed by the parser. A 
 * program consists of a series of procedure declarations, followed by 
 * a body consisting of a single statement.
 * @author Daniel Wang
 * @version March 30, 2020
 */
public class Program extends Statement
{
    /**
     * Instance variables
     */
    private List<String> globalVariables;
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;
    
    /**
     * Constructs a Program object using the given list of global 
     * variable names, list of procedure declarations, and program body.
     * @param gv a list of the names of the global variables
     * @param p a list of ProcedureDeclaration objects representing 
     *          the program declarations
     * @param s a Statement object representing the program body
     * @postcondition The instance variable globalVariables is set 
     *                to gv.
     *              - The instance variable procedures is set to p.
     *              - The instance variable stmt is set to s.
     */
    public Program(List<String> gv, List<ProcedureDeclaration> p, 
            Statement s)
    {
        globalVariables = gv;
        procedures = p;
        stmt = s;
    }
    
    /**
     * Executes the program represented by this Program object.
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        for (String var : globalVariables)
        {
            env.declareVariable(var, 0);
        }
        
        for (ProcedureDeclaration pd : procedures)
        {
            pd.exec(env);
        }
        
        stmt.exec(env);
    }
    
    /**
     * Compiles the program represented by this Program object into the 
     * given file.
     * @param filename the name of the file
     */
    public void compile(String filename)
    {
        Emitter e = new Emitter(filename);
        
        e.emit("# @author Daniel Wang");
        e.emit("# @version May 1, 2020");
        e.emit(".data");
        e.emit("newline:");
        e.emit(".asciiz \"\\n\"");
        
        for (String varName : globalVariables)
        {
            e.emit("var" + varName + ":");
            e.emit(".word 0");
        }
        
        e.emit("");
        e.emit(".text");
        e.emit(".globl main");
        e.emit("");
        e.emit("main:");
        
        stmt.compile(e);
        
        e.emit("# Normal termination");
        e.emit("li $v0 10");
        e.emit("syscall");
        e.emit("");
        
        for (ProcedureDeclaration pd : procedures)
        {
            pd.compile(e);
        }
        
        e.close();
    }
}
