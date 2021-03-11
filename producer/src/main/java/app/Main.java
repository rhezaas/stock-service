package app;

import common.File;
import common.Log;

import app.models.ProducerModel;

public class Main {
    public static void main(String[] args) {
        String message = null;
        File file = new File("../test3.txt");
        ProducerModel producer = new ProducerModel();

        try {
            file.OpenForRead();

            Log.info("Broadcasting Messgages...");
            while((message = file.readLine()) != null) {
                producer.broadcast("stockPrice", message);
            }
            Log.info("Done...");
        } catch (Exception error) {
            Log.error(error.getMessage());
        } finally {
            file.Close();
        }
    }
}
