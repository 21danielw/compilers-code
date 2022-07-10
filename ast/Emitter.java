package ast;
import java.util.List;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Emitter is a class that emits lines of MIPS code to a file.
 * @author Anu Datar and Daniel Wang
 * @version April 30, 2020
 * Usage: Emitter emitter = new Emitter(<file name>);
 */
public class Emitter
{
    /**
     * Instance variables
     */
    private PrintWriter out;
    private int whichIf;
    private ProcedureDeclaration currentProcedure;
    private int excessStackHeight;
    
    /**
     * Creates an emitter for writing to a new file with the given name.
     * @param outputFileName the given name
     * @postcondition The instance field out is set to a new 
     *                PrintWriter object for writing to a new file with 
     *                the given name.
     *              - The instance field whichIf is set to 1.
     */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
            whichIf = 1;
            currentProcedure = null;
            excessStackHeight = 0;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Prints one line of code to the file with non-labels indented.
     * @param code the given line of code
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
        {
            code = "\t" + code;
        }
        
        out.println(code);
    }
    
    /**
     * Closes the file. This method should be called after all calls 
     * to emit.
     * @postcondition The PrintWriter of this Emitter object is closed.
     */
    public void close()
    {
        out.close();
    }
    
    /**
     * Emits MIPS code to push the given register onto the stack.
     * @param reg the given register
     */
    public void emitPush(String reg)
    {
        emit("# Pushes " + reg + " onto stack");
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
        emit("");
        
        excessStackHeight++;
    }
    
    /**
     * Emits MIPS code to pop the stack into the given register.
     * @param reg the given register
     */
    public void emitPop(String reg)
    {
        emit("# Pops stack into " + reg);
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4");
        emit("");
        
        excessStackHeight--;
    }
    
    /**
     * Gets the ID for the next label in the MIPS code emitted by this 
     * emitter.
     * @return the ID for the next label
     * @postcondition whichIf is incremented by 1
     */
    public int nextLabelID()
    {
        int currentWhichIf = whichIf;
        
        whichIf++;
        
        return currentWhichIf;
    }
    
    /**
     * Sets the procedure context of this emitter to the given 
     * ProcedureDeclaration object.
     * @param proc the given ProcedureDeclaration object
     * @postcondition currentProcedure is set to proc
     *              - excessStackHeight is set to 0
     */
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        currentProcedure = proc;
        excessStackHeight = 0;
    }
    
    /**
     * Clears the procedure context of this emitter.
     * @postcondition currentProcedure is set to null
     */
    public void clearProcedureContext()
    {
        currentProcedure = null;
    }
    
    /**
     * Checks whether a variable is a local variable.
     * @param varName the name of the variable
     * @return true if the variable is local, false otherwise
     */
    public boolean isLocalVariable(String varName)
    {
        if (currentProcedure == null)
        {
            return false;
        }
        else
        {
            String procedureName = currentProcedure.getName();
            
            if (varName.equals(procedureName))
            {
                return true;
            }
            else
            {
                List<String> ps = currentProcedure.getParameterNames();
                
                if (ps.contains(varName))
                {
                    return true;
                }
                else
                {
                    List<String> vs = 
                            currentProcedure.getLocalVariableNames();
                    
                    return vs.contains(varName);
                }
            }
        }
    }
    
    /**
     * Gets the offset of a local variable.
     * @param localVarName the name of the local variable
     * @return an integer representing the offset of the variable in 
     *         the stack
     */
    public int getOffset(String localVarName)
    {
        List<String> ps = currentProcedure.getParameterNames();
        
        List<String> vs = currentProcedure.getLocalVariableNames();
        
        if (ps.contains(localVarName))
        {
            int index = ps.indexOf(localVarName);
            
            int pSize = ps.size();
            
            int vSize = vs.size();
            
            int offset = excessStackHeight * 4 + vSize * 4 + 4 + 
                    (pSize - 1 - index) * 4;
            
            return offset;
        }
        else
        {
            int index = vs.indexOf(localVarName);
            
            int vSize = vs.size();
            
            int offset = excessStackHeight * 4 + 
                    (vSize - 1 - index) * 4;
            
            return offset;
        }
    }
}
