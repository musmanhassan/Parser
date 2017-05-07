package au.com.redenergy.rules;

import au.com.redenergy.model.BaseDTO;
import au.com.redenergy.model.LineDTO;

/**
 * Created by Usman on 8/05/2017.
 */
public class LastRecordRule implements MeterReadRule {
    @Override
    public void validate(BaseDTO baseDTO) {
        if(baseDTO instanceof LineDTO && !((LineDTO) baseDTO).getLine().equals("900")){

            throw new IllegalArgumentException("Last Line is not 900");

        }
    }
}
