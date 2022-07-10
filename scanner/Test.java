package scanner;
import java.io.*;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        /*
        System.out.println(Scanner.isSeparator('3'));
        */
        
        /*
        System.out.println(Scanner.isOperandChar('>'));
        */
        
        /*
        FileInputStream fis = new FileInputStream(new File("OwnTest.txt"));
        */
        
        FileInputStream fis = new FileInputStream(new File("OwnTest.txt"));
        
        Scanner scanner = new Scanner(fis);
        
        boolean error = false;
        
        String currentToken = null;
        
        while (scanner.hasNext())
        {
            try
            {
                currentToken = scanner.nextToken();
                
                System.out.println(currentToken);
            }
            catch (ScanErrorException see)
            {
                error = true;
                
                System.out.println(see);
            }
        }
        
        if (error)
        {
            System.exit(1);
        }
        
        /*
        while (scanner.hasNext())
        {
            try
            {
                System.out.println(scanner.nextToken());
            }
            catch (ScanErrorException see)
            {
                System.out.println("Bad");
            }
        }
        */
        
        /*
        System.out.println(scanner.nextToken());
        */
        
        /*
        System.out.println(scanner.nextToken());
        */
        
        /*
        System.out.println(scanner.nextToken());
        */
        
        /*
        System.out.println(scanner.scanOperand());
        */
        
        /*
        int n = 5;
        
        System.out.println(n!s);
        */
        
        /*
        StringReader sr = new StringReader("123(x");
        Scanner scanner = new Scanner(sr);
        */
        
        /*
        FileInputStream fis = new FileInputStream(new File("ScannerTest.txt"));
        Scanner scanner = new Scanner(fis);
        */
        
        /*
        while (scanner.hasNext())
        {
            System.out.println(scanner.nextToken());
        }
        */
        
        /*
        Scanner scanner = new Scanner("Well");
        
        System.out.println(scanner.nextToken());
        System.out.println(scanner.nextToken());
        System.out.println(scanner.nextToken());
        System.out.println(scanner.nextToken());
        */
        
        /*
        System.out.println(scanner.scanIdentifier());
        */
        
        /*
        StringReader sr = new StringReader("B988z Abb");
        
        Scanner scanner = new Scanner(sr);
        
        System.out.println(scanner.scanIdentifier());
        */
        
        /*
        System.out.println(Scanner.isLetter('A'));
        System.out.println(Scanner.isWhiteSpace(' '));
        System.out.println(Scanner.isDigit('B'));
        System.out.println(Scanner.isDigit('\n'));
        System.out.println(Scanner.isDigit('5'));
        */
        
        /*
        System.out.println(scanner.isDigit('A'));
        
        System.out.println(scanner.isDigit('0'));
        
        System.out.println(scanner.isDigit('1'));
        
        System.out.println(scanner.isDigit('9'));
        
        System.out.println(scanner.isDigit('*'));
        */
        
        /*
        scanner.eat('H');
        
        scanner.eat('e');
        
        scanner.eat('e');
        */
        
        /*
        while (scanner.hasNext())
        {
            scanner.getNextChar();
        }
        */
    }
}
