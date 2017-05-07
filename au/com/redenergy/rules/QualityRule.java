package au.com.redenergy.rules;

import au.com.redenergy.model.BaseDTO;
import au.com.redenergy.model.MeterReadDTO;
import au.com.redenergy.model.MeterVolumeDTO;

import java.time.LocalDate;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by Usman on 8/05/2017.
 */
public class QualityRule implements MeterReadRule {
    @Override
    public void validate(BaseDTO baseDTO) {
        MeterReadDTO meterReadDTO = (MeterReadDTO)baseDTO;
        SortedMap<LocalDate, MeterVolumeDTO> volumes = meterReadDTO.getVolumes();

        for(Map.Entry<LocalDate, MeterVolumeDTO> entry : volumes.entrySet()) {
            MeterVolumeDTO meterVolumeDTO = entry.getValue();
            if(!(meterVolumeDTO.getQuality().toString().equals("A") || meterVolumeDTO.getQuality().toString().equals("E"))){
              throw new IllegalArgumentException("Quality is not A or E");
            }

        }
    }
}
