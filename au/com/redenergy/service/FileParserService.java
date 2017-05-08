package au.com.redenergy.service;

import au.com.redenergy.model.EnergyUnit;
import au.com.redenergy.model.MeterReadDTO;
import au.com.redenergy.model.MeterVolumeDTO;
import au.com.redenergy.model.Quality;
import au.com.redenergy.util.RuleUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Usman on 6/05/2017.
 */
public class FileParserService {

    MeterReadDTO meterReadDTO;

    public Collection<MeterReadDTO> getMeterData(File meterDataFile) {

        List<MeterReadDTO> meterReadList = new ArrayList<MeterReadDTO>();
        try(BufferedReader meterData = Files.newBufferedReader(Paths.get(meterDataFile.getPath()))) {

            Supplier<Stream<String>> streamSupplier = () -> meterData.lines();
            RuleUtil.applyFirstRecordRule(streamSupplier.get().findFirst().get());
            applyLastRecordRule(meterDataFile);
         //   List<String> meterDataList = Files.readAllLines(meterDataFile.toPath());
           /* if (meterDataList.size() > 1) { // check if the file has more then two lines to validate the first and last line
                RuleUtil.applyFileValidationRule(meterDataList); // validate file if invlad throw exception
            } else {
                throw new IllegalArgumentException("No Start or End Record Found");
            }*/
            meterReadList = streamSupplier.get().map(mapToItem).distinct().filter(p -> p != null).collect(Collectors.toList()); // converting the data in to objects

            RuleUtil.applyDomainValidationRules(meterReadList); // Apply all the bussiness logic rules

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return meterReadList;
    }

    private void applyLastRecordRule(File meterDataFile){

        try(Stream<String> meterData = Files.lines(Paths.get(meterDataFile.getPath()))){

            Optional<String> last = meterData.reduce((first, second) -> second);
            RuleUtil.applyLastRecordRule(last.get());
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
    private Function<String, MeterReadDTO> mapToItem = (line) -> {
        String[] p = line.split(",");// a CSV has comma separated lines

        if (p[0].equals("200")) {
            if (p[2].equals(EnergyUnit.KWH.toString())) {
                meterReadDTO = new MeterReadDTO(p[1], EnergyUnit.KWH);
            } else {
                meterReadDTO = new MeterReadDTO(p[1], EnergyUnit.OTHER);
            }
        } else if (p[0].equals("300") && meterReadDTO != null) {
            MeterVolumeDTO meterVolumeDTO = null;
            if (p[3].equals(Quality.A.toString()) || p[3].equals(Quality.E.toString())) {
                BigDecimal a = new BigDecimal(p[2]);
                a = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                meterVolumeDTO = new MeterVolumeDTO(a, Quality.valueOf(p[3]));
            } else {
                BigDecimal a = new BigDecimal(p[2]);
                a = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                meterVolumeDTO = new MeterVolumeDTO(a, Quality.valueOf(p[3]));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            LocalDate localDate = LocalDate.parse(p[1], formatter);

            meterReadDTO.appendVolume(localDate, meterVolumeDTO);
        }

        return meterReadDTO;
    };

}
