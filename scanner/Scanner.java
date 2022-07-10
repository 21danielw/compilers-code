package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters 
 * lab exercise 1.
 * @author Ms. Datar
 * @author Daniel Wang
 * @version January 30, 2020
 * Usage: FileInputStream inStream = 
 *            new FileInputStream(new File(<file name>);
 *        Scanner scanner = new Scanner(inStream);
 *        String nextToken = scanner.nextToken();
 */
public class Scanner
{
    /**
     * Instance variables
     */
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    
    /**
     * Constructs a Scanner object that uses the given InputStream 
     * object for input.
     * Usage: FileInputStream inStream = 
     *            new FileInputStream(new File(<file name>);
     *        Scanner scanner = new Scanner(inStream);
     * @param inStream the given InputStream object
     * @postcondition The instance field in is set to a BufferedReader 
     *                object constructed using inStream.
     *              - The instance field eof is set to false.
     *              - The instance field currentChar is set to the 
     *                first character in inStream.
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
        
        if (currentChar == '.')
        {
            eof = true;
        }
    }
    
    /**
     * Constructs a Scanner object that scans the given string.
     * Usage: Scanner scanner = new Scanner(input_string);
     * @param inString the given string
     * @postcondition The instance field in is set to a BufferedReader 
     *                object constructed using inString.
     *              - The instance field eof is set to false.
     *              - The instance field currentChar is set to the 
     *                first character in inString.
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
        
        if (currentChar == '.')
        {
            eof = true;
        }
    }
    
    /**
     * The getNextChar method attempts to get the next character from 
     * the BufferedReader. It sets the instance field eof to true if 
     * the end of the BufferedReader is reached. Otherwise, it reads 
     * the next character from the BufferedReader and stores it in the 
     * instance field currentChar.
     * @postcondition If the end of the BufferedReader is not reached, 
     *                the BufferedReader is advanced one character, and 
     *                the instance field currentChar is set to the 
     *                character read from the BufferedReader.
     *              - Otherwise, the instance field eof is set to true.
     */
    private void getNextChar()
    {
        try
        {
            int num = in.read();
            
            if (num == -1)
            {
                eof = true;
            }
            else
            {
                currentChar = (char) num;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * Calls getNextChar if the given character is equal to currentChar
     * @param expected the given character
     * @throws ScanErrorException if expected does not equal 
     *                            currentChar
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal character - " + 
                "expected " + currentChar + " and found " + expected);
        }
    }
    
    /**
     * Checks whether the BufferedReader is exhausted.
     * @return true if the BufferedReader is not exhausted, false 
     *         otherwise
     */
    public boolean hasNext()
    {
        return ! eof;
    }
    
    /**
     * Checks whether the given character is a digit.
     * @param c the given character
     * @return true if c is a character, false otherwise
     */
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }
    
    /**
     * Checks whether the given character is a letter.
     * @param c the given character
     * @return true if c is a lowercase or uppercase letter, false 
     *         otherwise
     */
    public static boolean isLetter(char c)
    {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }
    
    /**
     * Checks whether the given character is a white space character.
     * @param c the given character
     * @return true if c is a white space character, false otherwise
     */
    public static boolean isWhiteSpace(char c)
    {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }
    
    /**
     * Checks whether the given character is an operand character.
     * @param c the given character
     * @return true if c is an operand character, false otherwise
     */
    public static boolean isOperandChar(char c)
    {
        return c == '=' || c == '+' || c == '-' || 
            c == '*' || c == '/' || c == '%' || 
            c == '(' || c == ')' || 
            c == '<' || c == '>' || 
            c == ':';
    }
    
    /**
     * Checks whether the given character is a separator character.
     * @param c the given character
     * @return true if c is a separator character, false otherwise
     */
    public static boolean isSeparator(char c)
    {
        return c == ';' || c == ',';
    }
    
    /**
     * Scans a number from the BufferedReader.
     * @return a string representing the scanned number
     * @throws ScanErrorException if a character other than a digit or 
     *                            a whitespace character is encountered
     * @postcondition The BufferedReader is advanced to the character 
     *                after the scanned number.
     */
    private String scanNumber() throws ScanErrorException
    {
        String s = "";
        
        if (isDigit(currentChar))
        {
            s += currentChar;
            eat(currentChar);
            
            if (currentChar == '.')
            {
                eof = true;
            }
            
            while (hasNext() && isDigit(currentChar))
            {
                s += currentChar;
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                }
            }
            
            if (hasNext() && isLetter(currentChar))
            {
                while (hasNext() && 
                    (isDigit(currentChar) || isLetter(currentChar)))
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                    }
                }
                
                throw new ScanErrorException("Illegal character - " + 
                    "number cannot contain letter");
            }
            else
            {
                return s;
            }
        }
        else
        {
            throw new ScanErrorException("Illegal character - " + 
                "expected digit but did not find digit");
        }
    }
    
    /**
     * Scans an identifier from the BufferedReader.
     * @return a string representing the scanned identifier
     * @throws ScanErrorException if a character other than a letter, 
     *                            a digit, or a whitespace character 
     *                            is encountered
     * @precondition The BufferedReader is advanced to the character 
     *               after the scanned identifier.
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String s = "";
        
        if (isLetter(currentChar))
        {
            s += currentChar;
            eat(currentChar);
            
            if (currentChar == '.')
            {
                eof = true;
            }
            
            while (hasNext() && (isLetter(currentChar) || 
                isDigit(currentChar)))
            {
                s += currentChar;
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                }
            }
            
            return s;
        }
        else
        {
            throw new ScanErrorException("Illegal character - " + 
                "expected letter but did not find letter");
        }
    }
    
    /**
     * Scans an operand from the BufferedReader.
     * @return a string representing the scanned operand
     * @throws ScanErrorException if a character other than an operand 
     *                            character is encountered
     * @precondition The BufferedReader is advanced to the character 
     *               after the scanned operand.
     */
    private String scanOperand() throws ScanErrorException
    {
        if (isOperandChar(currentChar))
        {
            if (currentChar == '=')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "=";
                }
                else
                {
                    return "=";
                }
            }
            else if (currentChar == '+')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "+";
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return "+=";
                    }
                    else
                    {
                        return "+=";
                    }
                }
                else
                {
                    return "+";
                }
            }
            else if (currentChar == '-')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "-";
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return "-=";
                    }
                    else
                    {
                        return "-=";
                    }
                }
                else
                {
                    return "-";
                }
            }
            else if (currentChar == '*')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "*";
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return "*=";
                    }
                    else
                    {
                        return "*=";
                    }
                }
                else
                {
                    return "*";
                }
            }
            else if (currentChar == '%')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "%";
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return "%=";
                    }
                    else
                    {
                        return "%=";
                    }
                }
                else
                {
                    return "%";
                }
            }
            else if (currentChar == '(')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "(";
                }
                else
                {
                    return "(";
                }
            }
            else if (currentChar == ')')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return ")";
                }
                else
                {
                    return ")";
                }
            }
            else if (currentChar == '<')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return "<";
                }
                else if (currentChar == '>')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return "<>";
                    }
                    else
                    {
                        return "<>";
                    }
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return "<=";
                    }
                    else
                    {
                        return "<=";
                    }
                }
                else
                {
                    return "<";
                }
            }
            else if (currentChar == '>')
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return ">";
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return ">=";
                    }
                    else
                    {
                        return ">=";
                    }
                }
                else
                {
                    return ">";
                }
            }
            else
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                    
                    return ":";
                }
                else if (currentChar == '=')
                {
                    eat(currentChar);
                    
                    if (currentChar == '.')
                    {
                        eof = true;
                        
                        return ":=";
                    }
                    else
                    {
                        return ":=";
                    }
                }
                else
                {
                    return ":";
                }
            }
        }
        else
        {
            throw new ScanErrorException("Illegal character - " + 
                "expected operand character but did not find " + 
                "operand character");
        }
    }
    
    /**
     * Scans a separator from the BufferedReader.
     * @return a string representing the scanned separator
     * @throws ScanErrorException if a character other than a 
     *                            separator character is encountered
     * @postcondition The BufferedReader is advanced to the character 
     *                after the scanned separator.
     */
    private String scanSeparator() throws ScanErrorException
    {
        String s = "";
        
        if (isSeparator(currentChar))
        {
            s += currentChar;
            eat(currentChar);
            
            if (currentChar == '.')
            {
                eof = true;
            }
            
            return s;
        }
        else
        {
            throw new ScanErrorException("Illegal character - " + 
                "expected separator but did not find separator");
        }
    }
    
    /**
     * Scans a lexeme from the BufferedReader.
     * @return a string representing the scanned lexeme
     * @throws ScanErrorException if a character other than a digit, 
     *                            letter, whitespace character, 
     *                            operand character, or separator 
     *                            character is encountered
     * @postcondition The BufferedReader is advanced to the character 
     *                after the scanned lexeme.
     */
    public String nextToken() throws ScanErrorException
    {
        int state = 0;
        
        while (hasNext() && 
            (state == 0 && 
                (currentChar == '/' || isWhiteSpace(currentChar)) || 
            state == 1 || 
            state == 2))
        {
            if (hasNext())
            {
                if (state == 0)
                {
                    if (currentChar == '/')
                    {
                        state = 1;
                    }
                    else if (isWhiteSpace(currentChar))
                    {
                        state = 0;
                    }
                }
                else if (state == 1)
                {
                    if (currentChar == '/')
                    {
                        state = 2;
                    }
                    else if (currentChar == '=')
                    {
                        eat(currentChar);
                        
                        return "/=";
                    }
                    else
                    {
                        return "/";
                    }
                }
                else
                {
                    if (currentChar == '\n')
                    {
                        state = 0;
                    }
                    else
                    {
                        state = 2;
                    }
                }
            }
            
            eat(currentChar);
            
            if ((state == 0 || state == 1) && currentChar == '.')
            {
                eof = true;
            }
        }
        
        if (hasNext())
        {
            if (isDigit(currentChar))
            {
                return scanNumber();
            }
            else if (isLetter(currentChar))
            {
                return scanIdentifier();
            }
            else if (isOperandChar(currentChar))
            {
                return scanOperand();
            }
            else if (isSeparator(currentChar))
            {
                return scanSeparator();
            }
            else
            {
                eat(currentChar);
                
                if (currentChar == '.')
                {
                    eof = true;
                }
                
                throw new ScanErrorException("Unknown character");
            }
        }
        else
        {
            if (state == 0)
            {
                return ".";
            }
            else if (state == 1)
            {
                return "/";
            }
            else
            {
                return ".";
            }
        }
    }
}
