package parser;
import ast.Program;
import environment.Environment;
import scanner.Scanner;
import java.io.*;

public class TestEmitter
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the name of the file containing the program to be compiled.");
        String inputFileName = br.readLine();
        System.out.println("Enter the name of the file to which to output the assembly code.");
        String outputFileName = br.readLine();
        
        FileInputStream fis = new FileInputStream(new File(inputFileName));
        
        Scanner scanner = new Scanner(fis);
        
        Parser parser = new Parser(scanner);
        
        Environment environment = new Environment(null);
        
        Program program = parser.parseProgram();
        
        program.compile(outputFileName);
    }
}
