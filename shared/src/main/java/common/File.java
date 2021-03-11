package common;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

public class File {
    private String filename;
    private BufferedReader reader;
    private FileOutputStream writer;

    public File(String filename) {
        this.filename = filename;
    }

    public void OpenForWrite() throws IOException {
        this.writer = new FileOutputStream(this.filename);
    }

    public void writeBuffer(StringBuffer str) throws IOException {
        this.writer.write(str.toString().getBytes());
    }

    public void OpenForRead() throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(this.filename));;
    }

    public String readLine() throws IOException {
        return this.reader.readLine();
    }

    public void Close() {
        try {
            if (this.reader != null) {
                this.reader.close();
            }

            if (this.writer != null) {
                this.writer.close();
            }
        } catch (IOException ioError) {
            System.out.println(ioError);
        }
    }
}
