package app.models;

import app.dto.Output;
import app.dto.Stock;
import common.File;
import common.Helper;
import common.Log;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class ProcessorModel {
    private BlockingQueue<Stock> processQueue;

    public ProcessorModel() {
        processQueue = new ArrayBlockingQueue<>(5);
    }

    public void addToProcessQueue(Stock stock) {
        try {
            this.processQueue.put(stock);
        } catch (Exception error) {
            Log.error(error);
        }
    }

    public Runnable processData() {
        Runnable run = () -> {
            try {
                Stock stock = null;

                while (true) {
                    stock = this.processQueue.take();
                    this.proccess(stock);
                }
            } catch (Exception error) {
                Log.error(error.getMessage());
            }
        };

        return run;
    }

    private void proccess(Stock stock) {
        File reader = null;
        File writer = null;
        String line = null;
        Output output = null;
        boolean isUpdate = false;

        StringBuffer inpuBuffer = null;

        try {
            reader = new File("../output.txt");
            writer = new File("../output.txt");

            reader.OpenForRead();

            inpuBuffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                output = this.outputParser(line);

                if (
                    output.getSymbol().equals(stock.getSymbol()) &&
                    Helper.timeToString(output.getTime(), "mm").equals(Helper.dateToString(stock.getDate(), "mm"))
                ) {
                    output.setLowPrice(Math.min(output.getLowPrice(), stock.getPrice()));
                    output.setHighPrice(Math.max(output.getHighPrice(), stock.getPrice()));

                    inpuBuffer.append("" +
                        Helper.timeToString(output.getTime(), "HH:mm:00") + "|" +
                        output.getSymbol() + "|" +
                        "high;" + output.getHighPrice() + "|" +
                        "low;" + output.getLowPrice() + "\n"
                    );

                    isUpdate = true;
                } else {
                    inpuBuffer.append(line + "\n");
                }
            }

            if (line == null && (inpuBuffer.length() == 0 || !isUpdate)) {
                inpuBuffer.append("" +
                    Helper.timeToString(LocalTime.parse(Helper.dateToString(stock.getDate(), "HH:mm:ss")), "HH:mm:00") + "|" +
                    stock.getSymbol() + "|" +
                    "high;" + stock.getPrice() + "|" +
                    "low;" + stock.getPrice() + "\n"
                );
            }

            writer.OpenForWrite();
            writer.writeBuffer(inpuBuffer);
        } catch(Exception error) {
            Log.error(error.getMessage());
        } finally {
            reader.Close();
            writer.Close();
        }
    }

    private Output outputParser(String str) {
        // parse message
        Pattern pattern = Pattern.compile("[^|;highlow]+");
        Matcher matcher = pattern.matcher(str);

        Output output = null;

        // matches array
        List<String> matches = new ArrayList<>();        

        while (matcher.find()) {
            matches.add(matcher.group(0));
        }

        try {
            output = new Output(
                LocalTime.parse(matches.get(0)),
                matches.get(1),
                Math.max(Integer.parseInt(matches.get(2)), Integer.parseInt(matches.get(3))),
                Math.min(Integer.parseInt(matches.get(2)), Integer.parseInt(matches.get(3)))
            );
        } catch (Exception err) {
            Log.error(err.getMessage());
        }

        return output;
    }
}
