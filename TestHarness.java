// Copyright Red Energy Limited 2017


import au.com.redenergy.model.MeterReadDTO;
import au.com.redenergy.parser.MeterDataParserFileImpl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Simple test harness for trying out SimpleNem12Parser implementation
 */
public class TestHarness {

    public static void main(String[] args) {
        File simpleNem12File = new File("SimpleNem12.csv");

        // Uncomment below to try out test harness.
        Collection<MeterReadDTO> meterReads = new MeterDataParserFileImpl().parseMeterData(simpleNem12File);

        MeterReadDTO read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6123456789 is %.2f", read6123456789.getTotalVolume()));  // Should be -36.84

        MeterReadDTO read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6987654321 is %.2f", read6987654321.getTotalVolume()));  // Should be 14.33
    }
}
