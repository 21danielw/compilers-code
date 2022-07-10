package scanner;
import java.io.*;

/**
 * ScannerTester is a tester for the Scanner class.
 * @author Daniel Wang
 * @version February 4, 2020
 * Usage: Run the main method in this class
 */
public class ScannerTester
{
    /**
     * Driver method for this class.
     * @param args command line arguments
     * @throws FileNotFoundException if the file that the 
     *                               FileInputStream tries to access 
     *                               is not found
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        FileInputStream fis = 
                new FileInputStream(new File("scannerTestAdvanced.txt"));
        
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
        
        if (currentToken == null || ! currentToken.equals("."))
        {
            System.out.println(".");
        }
        
        if (error)
        {
            System.exit(1);
        }
    }
}
