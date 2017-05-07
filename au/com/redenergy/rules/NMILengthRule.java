package au.com.redenergy.rules;

import au.com.redenergy.model.BaseDTO;
import au.com.redenergy.model.MeterReadDTO;

/**
 * Created by Usman on 8/05/2017.
 */
public class NMILengthRule implements MeterReadRule {
    @Override
    public void validate(BaseDTO baseDTO) {

        MeterReadDTO meterReadDTO = (MeterReadDTO) baseDTO;
        if (meterReadDTO.getNmi().length() != 10) {
            throw new IllegalArgumentException("NMI length is not 10");
        }
    }
}
