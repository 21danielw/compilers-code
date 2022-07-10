package parser;
import scanner.Scanner;
import scanner.ScanErrorException;
import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Expression;
import ast.If;
import ast.Number;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.Statement;
import ast.Variable;
import ast.While;
import ast.Writeln;
import ast.WritelnTimes;
import java.util.List;
import java.util.ArrayList;

/**
 * Parser is a parser for Compilers and Interpreters.
 * The purpose of the Parser is to read in a program consisting of a 
 * stream of tokens and generate an abstract syntax tree (AST) 
 * for the program. An abstract syntax tree is a tree that shows the 
 * relationships between the statements, expressions, and 
 * subexpressions in the program.
 * @author Daniel Wang
 * @version March 30, 2020
 * Usage: FileInputStream inStream = 
 *            new FileInputStream(new File(<file name>);
 *        Scanner scanner = new Scanner(inStream);
 *        Parser parser = new Parser(scanner);
 *        Environment environment = new Environment(null);
 *        Program program = parser.parseProgram();
 *        program.exec(environment);
 */
public class Parser
{
    /**
     * Instance variables
     */
    private Scanner scanner;
    private String currentToken;
    
    /**
     * Constructs a Parser object that uses the given Scanner object 
     * for input.
     * Usage: FileInputStream inStream = 
     *            new FileInputStream(new File(<file name>);
     *        Scanner scanner = new Scanner(inStream);
     *        Parser parser = new Parser(scanner);
     * @param sc the given Scanner object
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered by the 
     *                            Scanner
     *  @postcondition The instance field scanner is set to sc
     *               - The instance field currentToken is set to the 
     *                 first token in sc
     */
    public Parser(Scanner sc) throws ScanErrorException
    {
        scanner = sc;
        currentToken = sc.nextToken();
    }
    
    /**
     * Advances currentToken to the next token if currentToken is 
     * equal to the given String object.
     * @param expectedToken the given String object
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered by the 
     *                            Scanner
     */
    private void eat(String expectedToken) throws ScanErrorException
    {
        if (currentToken.equals(expectedToken))
        {
            currentToken = scanner.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Expected " + 
                expectedToken + " but found " + currentToken);
        }
    }
    
    /**
     * Parses an integer.
     * @return a Number object representing the parsed integer
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered by the 
     *                            Scanner
     */
    private Number parseNumber() throws ScanErrorException
    {
        int num = Integer.parseInt(currentToken);
        
        eat(currentToken);
        
        Number n = new Number(num);
        
        return n;
    }
    
    /**
     * Parses a factor. A factor is a single integer, a procedure call, 
     * or a sequence of tokens enclosed in parentheses.
     * @return an Expression object representing the parsed factor
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered by the 
     *                            Scanner
     */
    private Expression parseFactor() throws ScanErrorException
    {
        if (currentToken.equals("("))
        {
            eat("(");
            Expression e = parseExpression();
            eat(")");
            
            return e;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            Expression e = parseFactor();
            Number zero = new Number(0);
            BinOp b = new BinOp("-", zero, e);
            
            return b;
        }
        else
        {
            char c = currentToken.charAt(0);
            
            if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z')
            {
                String name = currentToken;
                
                eat(currentToken);
                
                if (currentToken.equals("("))
                {
                    eat("(");
                    
                    List<Expression> list = new ArrayList<Expression>();
                    
                    while (!currentToken.equals(")"))
                    {
                        Expression exp = parseExpression();
                        
                        list.add(exp);
                        
                        if (!currentToken.equals(")"))
                        {
                            eat(",");
                        }
                    }
                    
                    eat(")");
                    
                    ProcedureCall pc = new ProcedureCall(name, list);
                    
                    return pc;
                }
                else
                {
                    Variable v = new Variable(name);
                    
                    return v;
                }
            }
            else
            {
                Number n = parseNumber();
                return n;
            }
        }
    }
    
    /**
     * Parses a term. A term is a sequence of tokens consisting of a 
     * chain of contiguous multiplication or division operations.
     * @return an Expression object representing the parsed term
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered by the 
     *                            Scanner
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression e = parseFactor();
        
        if (!currentToken.equals("*") && !currentToken.equals("/"))
        {
            return e;
        }
        
        Expression currentExp = e;
        
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                Expression e2 = parseFactor();
                
                currentExp = new BinOp("*", currentExp, e2);
            }
            else
            {
                eat("/");
                Expression e2 = parseFactor();
                
                currentExp = new BinOp("/", currentExp, e2);
            }
        }
        
        return currentExp;
    }
    
    /**
     * Parses an expression. An expression is a sequence of tokens 
     * consisting of a chain of contiguous addition and subtraction 
     * operations.
     * @return an Expression object representing the parsed expression
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered by the 
     *                            Scanner
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression e = parseTerm();
        
        if (!currentToken.equals("+") && !currentToken.equals("-"))
        {
            return e;
        }
        
        Expression currentExp = e;
        
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                Expression e2 = parseTerm();
                
                currentExp = new BinOp("+", currentExp, e2);
            }
            else
            {
                eat("-");
                Expression e2 = parseTerm();
                
                currentExp = new BinOp("-", currentExp, e2);
            }
        }
        
        return currentExp;
    }
    
    /**
     * Parses a statement.
     * @return a Statement object representing the parsed statement
     * @throws Exception if either of these two conditions is 
     *                   encountered:
     *                 - a character other than a digit, 
     *                   letter, whitespace character, 
     *                   operand character, or separator 
     *                   character is encountered by the 
     *                   Scanner
     *                 - the statement is not WRITELN, WRITELNTIMES, 
     *                   BEGIN/END, IF, WHILE, or variable assignment
     */
    public Statement parseStatement() throws Exception
    {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression e = parseExpression();
            eat(")");
            eat(";");
            
            Writeln w = new Writeln(e);
            
            return w;
        }
        else if (currentToken.equals("WRITELNTIMES"))
        {
            eat("WRITELNTIMES");
            eat("(");
            Expression e = parseExpression();
            eat(",");
            Expression times = parseExpression();
            eat(")");
            eat(";");
            
            WritelnTimes wt = new WritelnTimes(e, times);
            
            return wt;
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            
            List<Statement> list = new ArrayList<Statement>();
            
            boolean ended = false;
            
            while (!ended)
            {
                if (currentToken.equals("END"))
                {
                    eat("END");
                    eat(";");
                    ended = true;
                }
                else
                {
                    Statement s = parseStatement();
                    
                    list.add(s);
                }
            }
            
            Block b = new Block(list);
            
            return b;
        }
        else if (currentToken.equals("IF"))
        {
            eat("IF");
            
            Condition c = parseCondition();
            
            eat("THEN");
            
            Statement s = parseStatement();
            
            If ifStatement = new If(c, s);
            
            return ifStatement;
        }
        else if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            
            Condition c = parseCondition();
            
            eat("DO");
            
            Statement s = parseStatement();
            
            While whileStatement = new While(c, s);
            
            return whileStatement;
        }
        else
        {
            char c = currentToken.charAt(0);
            
            if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z')
            {
                String s = currentToken;
                eat(currentToken);
                eat(":=");
                Expression e = parseExpression();
                eat(";");
                
                Assignment a = new Assignment(s, e);
                
                return a;
            }
            else
            {
                throw new Exception("Invalid statement");
            }
        }
    }
    
    /**
     * Parses a boolean condition.
     * @return a Condition object representing the parsed boolean 
     *         condition
     * @throws Exception if either of these two conditions is 
     *                   encountered:
     *                 - a character other than a digit, 
     *                   letter, whitespace character, 
     *                   operand character, or separator 
     *                   character is encountered by the 
     *                   Scanner
     *                 - the operator is not a boolean operator
     */
    public Condition parseCondition() throws Exception
    {
        Expression e1 = parseExpression();
        
        if (currentToken.equals("=") || currentToken.equals("<>") || 
                currentToken.equals("<") || currentToken.equals(">") || 
                currentToken.equals("<=") || currentToken.equals(">="))
        {
            String relop = currentToken;
            
            eat(relop);
            
            Expression e2 = parseExpression();
            
            Condition c = new Condition(relop, e1, e2);
            
            return c;
        }
        else
        {
            throw new Exception("Operator not relop");
        }
    }
    
    /**
     * Parses a program, which includes global variables, procedure 
     * declarations, and local variables.
     * @return a Program object representing the parsed program
     * @throws Exception if one of these three conditions is 
     *                   encountered:
     *                 - a character other than a digit, 
     *                   letter, whitespace character, 
     *                   operand character, or separator 
     *                   character is encountered by the 
     *                   Scanner
     *                 - the name of a procedure is not an ID
     *                 - the name of a parameter is not an ID
     */
    public Program parseProgram() throws Exception
    {
        List<String> vars = new ArrayList<String>();
        
        List<ProcedureDeclaration> ps = new ArrayList<ProcedureDeclaration>();
        
        while (currentToken.equals("VAR"))
        {
            eat("VAR");
            
            String var = parseID();
            
            vars.add(var);
            
            while (!currentToken.equals(";"))
            {
                eat(",");
                
                var = parseID();
                
                vars.add(var);
            }
            
            eat(";");
        }
        
        while (currentToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            
            String name = parseID();
            
            eat("(");
            
            List<String> list = new ArrayList<String>();
            
            while (!currentToken.equals(")"))
            {
                String parameterName = parseID();
                
                list.add(parameterName);
                
                if (!currentToken.equals(")"))
                {
                    eat(",");
                }
            }
            
            eat(")");
            
            eat(";");
            
            List<String> varList = new ArrayList<String>();
            
            if (currentToken.equals("VAR"))
            {
                eat("VAR");
                
                while (!currentToken.equals(";"))
                {
                    String varName = parseID();
                    
                    varList.add(varName);
                    
                    if (!currentToken.equals(";"))
                    {
                        eat(",");
                    }
                }
                
                eat(";");
            }
            
            Statement stmt = parseStatement();
            
            ProcedureDeclaration p = new ProcedureDeclaration(name, 
                    list, stmt, varList);
            
            ps.add(p);
        }
        
        Statement stmt = parseStatement();
        
        eat(".");
        
        Program program = new Program(vars, ps, stmt);
        
        return program;
    }
    
    /**
     * Parses an ID.
     * @return a String representing the parsed ID
     * @throws Exception if either of these two conditions is 
     *                   encountered:
     *                 - a character other than a digit, 
     *                   letter, whitespace character, 
     *                   operand character, or separator 
     *                   character is encountered by the 
     *                   Scanner
     *                 - the parsed token is not an ID
     */
    public String parseID() throws Exception
    {
        char c = currentToken.charAt(0);
        
        if (!('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z'))
        {
            throw new Exception("Not id");
        }
        
        String id = currentToken;
        
        eat(currentToken);
        
        return id;
    }
}
