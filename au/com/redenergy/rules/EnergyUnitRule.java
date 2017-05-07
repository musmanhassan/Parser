package au.com.redenergy.rules;

import au.com.redenergy.model.BaseDTO;
import au.com.redenergy.model.MeterReadDTO;

/**
 * Created by Usman on 8/05/2017.
 */
public class EnergyUnitRule implements MeterReadRule {
    @Override
    public void validate(BaseDTO baseDTO) {
        MeterReadDTO meterReadDTO = (MeterReadDTO)baseDTO;
        if(!meterReadDTO.getEnergyUnit().toString().equals("KWH")){
            throw new IllegalArgumentException("Energy Unit is not in KWH");
        }
    }
}
