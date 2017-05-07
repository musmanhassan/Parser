package au.com.redenergy.rules;

import au.com.redenergy.model.BaseDTO;
import au.com.redenergy.model.LineDTO;

/**
 * Created by Usman on 7/05/2017.
 */
public class FirstRecordRule implements MeterReadRule{


    @Override
    public void validate(BaseDTO baseDTO) {

        if(baseDTO instanceof LineDTO && !((LineDTO) baseDTO).getLine().equals("100")){

            throw new IllegalArgumentException("First Line is not 100");

        }

    }
}
