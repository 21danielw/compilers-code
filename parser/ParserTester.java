package parser;
import ast.Program;
import scanner.Scanner;
import environment.Environment;
import java.io.File;
import java.io.FileInputStream;

public class ParserTester
{
    public static void main(String[] args) throws Exception
    {
        FileInputStream fis = 
            new FileInputStream(new File("OwnTest.txt"));
        
        Scanner scanner = new Scanner(fis);
        
        Parser parser = new Parser(scanner);
        
        Environment environment = new Environment(null);
        
        Program program = parser.parseProgram();
        
        program.exec(environment);
    }
}
