package common;

import org.junit.Test;
import org.junit.Before;

import java.io.FileNotFoundException;

public class FileTest {
    private File file;

    @Before
    public void init() {
        file = new File("../test.txt");
    }

    @Test
    public void read() throws FileNotFoundException {
        file.OpenForRead();
        
        if (file.hasNext()) {
            System.out.println(file.readLine());
        }

        file.Close();
    }
}
