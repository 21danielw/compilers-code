package ast;
import environment.Environment;
import java.util.List;
import java.util.ArrayList;

/**
 * ProcedureCall is a class representing a procedure call parsed by the 
 * parser.
 * @author Daniel Wang
 * @version March 30, 2020
 */
public class ProcedureCall extends Expression
{
    /**
     * Instance variables
     */
    private String name;
    private List<Expression> parameters;
    
    /**
     * Constructs a ProcedureCall object using the procedure name and 
     * list of parameters.
     * @param s a string representing the procedure name
     * @param params a list of Expression objects representing the 
     *               parameters
     * @postcondition The instance field name is to s.
     *              - The instance field parameters is set to params.
     */
    public ProcedureCall(String s, List<Expression> params)
    {
        name = s;
        parameters = params;
    }
    
    /**
     * Evaluates the procedure call represented by this ProcedureCall 
     * object using the given environment.
     * @param env the given environment
     * @return the value returned by the procedure call represented by 
     *         this ProcedureCall object
     */
    public int eval(Environment env)
    {
        ProcedureDeclaration pd = env.getProcedure(name);
        
        List<Integer> parameterValues = new ArrayList<Integer>();
        
        for (Expression exp : parameters)
        {
            int value = exp.eval(env);
            
            parameterValues.add(value);
        }
        
        List<String> parameterNames = pd.getParameterNames();
        
        Environment parentEnv = env.getParent();
        
        Environment newEnv;
        
        if (parentEnv == null)
        {
            newEnv = new Environment(env);
        }
        else
        {
            newEnv = new Environment(parentEnv);
        }
        
        int pnSize = parameterNames.size();
        
        for (int i = 0; i < pnSize; i++)
        {
            String paramName = parameterNames.get(i);
            Integer paramValue = parameterValues.get(i);
            
            newEnv.declareVariable(paramName, paramValue);
        }
        
        newEnv.declareVariable(name, 0);
        
        Statement stmt = pd.getStatement();
        
        stmt.exec(newEnv);
        
        int nameVariableValue = newEnv.getVariable(name);
        
        return nameVariableValue;
    }
    
    /**
     * Compiles the procedure call represented by this ProcedureCall 
     * object using the given emitter.
     * @param e the given emitter
     * @postcondition MIPS code for this procedure call is printed 
     *                using the given emitter.
     */
    public void compile(Emitter e)
    {
        e.emitPush("$ra");
        
        for (Expression exp : parameters)
        {
            exp.compile(e);
            e.emitPush("$v0");
        }
        
        e.emit("# Jump and link to " + "proc" + name);
        e.emit("jal " + "proc" + name);
        e.emit("");
        
        for (Expression exp : parameters)
        {
            e.emitPop("$a0");
        }
        
        e.emitPop("$ra");
    }
}
