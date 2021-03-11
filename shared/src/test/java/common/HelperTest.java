package common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HelperTest {
    @Test
    public void findIndex() {
        String[] sample = {"kita", "atik", "aku", "tika", "aku", "kia", "makan", "kua"};
        List<String> sampleList = new ArrayList<String>(Arrays.asList(sample));

        int test = Helper.findLatestIndex(sampleList, data -> {
            return data == "kua";
        });

        assertEquals(7, test);
    }
}
