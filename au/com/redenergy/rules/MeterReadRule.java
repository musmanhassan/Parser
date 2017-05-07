package au.com.redenergy.rules;

import au.com.redenergy.model.BaseDTO;

/**
 * Created by Usman on 7/05/2017.
 */
public interface MeterReadRule {

    void validate(BaseDTO baseDTO);
}
