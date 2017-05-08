package au.com.redenergy.rules;

import au.com.redenergy.model.EnergyUnit;
import au.com.redenergy.model.MeterReadDTO;
import au.com.redenergy.model.MeterVolumeDTO;
import au.com.redenergy.model.Quality;
import au.com.redenergy.util.RuleUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Usman on 6/05/2017.
 */
public class Test {
    MeterReadDTO meterReadDTO;

   public void test1(){

       String[] array = {"a", "b", "c", "d", "e"};

       Supplier<Stream<String>> streamSupplier1 = () -> Stream.of(array);

       //get new stream
       streamSupplier1.get().forEach(x -> System.out.println(x));

       //get another new stream
       long count = streamSupplier1.get().filter(x -> "b".equals(x)).count();
       System.out.println(count);
       String fileName = "SimpleNem12.csv";

       try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
           //   lines.skip(1L);

           List<String> meterDataList = Files.readAllLines(Paths.get(fileName));
           Supplier<Stream<String>> streamSupplier = () -> meterDataList.stream();;
           Optional<String> last = streamSupplier.get().reduce((first, second) -> second);
           System.out.print("last"+last);

           List<List<String>> values = streamSupplier.get().map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
           values.forEach(value -> System.out.println(value));
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void test2(){

   List<MeterReadDTO> meterReadList = new ArrayList<MeterReadDTO>();
       String fileName = "SimpleNem12.csv";
       try{
           List<String> meterDataList = Files.readAllLines(Paths.get(fileName));
           if(meterDataList.size()>1){
               RuleUtil.applyFileValidationRule(meterDataList);
           }else{
               throw new IllegalArgumentException("No Start or End Record");
           }
      //     System.out.println("First"+ meterDataList.get(meterDataList.size()-1));
       //    Supplier<Stream<String>> streamSupplier = () -> meterDataList.stream();
           meterReadList = meterDataList.stream().map(mapToItem).distinct().filter(p -> p != null).collect(Collectors.toList());

           // Uncomment below to try out test harness.
//    Collection<MeterReadDTO> meterReads = new SimpleNem12ParserImpl().parseSimpleNem12(simpleNem12File);

           RuleUtil.applyDomainValidationRules(meterReadList);
//
   MeterReadDTO read6123456789 = meterReadList.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
    System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume()));  // Should be -36.84
//
    MeterReadDTO read6987654321 = meterReadList.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
    System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));  // Should be 14.33

          // meterReadList.forEach(value -> System.out.println(value.getVolumes()));
       }catch (IOException ex){

       }

   }


    public void test3(){

        List<MeterReadDTO> meterReadList = new ArrayList<MeterReadDTO>();
        String fileName = "SimpleNem12.csv";

        try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))){
            Supplier<Stream<String>> streamSupplier = () -> br.lines();
            Optional<String> last = streamSupplier.get().reduce((first, second) -> second);
            System.out.print("last"+last);

           System.out.println("First"+streamSupplier.get().findFirst().get());



           /* List<String> meterDataList = Files.readAllLines(Paths.get(fileName));
            if(meterDataList.size()>1){
                RuleUtil.applyFileValidationRule(meterDataList);
            }else{
                throw new IllegalArgumentException("No Start or End Record");
            }*/
            //     System.out.println("First"+ meterDataList.get(meterDataList.size()-1));
            //    Supplier<Stream<String>> streamSupplier = () -> meterDataList.stream();
            meterReadList = streamSupplier.get().map(mapToItem).distinct().filter(p -> p != null).collect(Collectors.toList());


            // Uncomment below to try out test harness.
//    Collection<MeterReadDTO> meterReads = new SimpleNem12ParserImpl().parseSimpleNem12(simpleNem12File);

            RuleUtil.applyDomainValidationRules(meterReadList);
//
            MeterReadDTO read6123456789 = meterReadList.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
            System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume()));  // Should be -36.84
//
            MeterReadDTO read6987654321 = meterReadList.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
            System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));  // Should be 14.33

            // meterReadList.forEach(value -> System.out.println(value.getVolumes()));
        }catch (IOException ex){

        }

    }
    private Function<String, MeterReadDTO> mapToItem = (line) -> {
        String[] p = line.split(",");// a CSV has comma separated lines

        if(p[0].equals("200")){
            if(p[2].equals(EnergyUnit.KWH.toString())){
                 meterReadDTO = new MeterReadDTO(p[1],EnergyUnit.KWH);
            }else{
                 meterReadDTO = new MeterReadDTO(p[1],EnergyUnit.OTHER);
            }
            //EnergyUnit = EnergyUnit.valueOf(p[1]);
     //      item = new MeterReadDTO(p[1],);
        }else if(p[0].equals("300") && meterReadDTO!=null){
            MeterVolumeDTO meterVolumeDTO = null;
           if(p[3].equals(Quality.A.toString()) || p[3].equals(Quality.E.toString())) {
               meterVolumeDTO = new MeterVolumeDTO(new BigDecimal(p[2]),Quality.valueOf(p[3]));
           }else{
               meterVolumeDTO = new MeterVolumeDTO(new BigDecimal(p[2]),Quality.valueOf(p[3]));
           }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");


//convert String to LocalDate
            LocalDate localDate = LocalDate.parse(p[1], formatter);

            meterReadDTO.appendVolume(localDate,meterVolumeDTO);
        }

        /*item.setItemNumber(p[0]);//<-- this is the first column in the csv file
        if (p[3] != null && p[3].trim().lenpgth() > 0) {
            item.setSomeProeprty(p[3]);
        }*/
        //more initialization goes here
        return meterReadDTO;
    };

    public static void main(String[] args) {

       new Test().test3();
    }
}
