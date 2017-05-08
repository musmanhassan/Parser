package au.com.redenergy.util;

import au.com.redenergy.model.LineDTO;
import au.com.redenergy.model.MeterReadDTO;
import au.com.redenergy.rules.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usman on 8/05/2017.
 */
public class RuleUtil {


    public  static void applyFirstRecordRule(String firstRecord){

        LineDTO firstLineDTO = new LineDTO(firstRecord);
        FirstRecordRule firstRecordRule = new FirstRecordRule();
        firstRecordRule.validate(firstLineDTO);
    }

    public  static void applyLastRecordRule(String lastRecord){
        LineDTO lastLineDTO = new LineDTO(lastRecord);
        LastRecordRule lastRecordRule = new LastRecordRule();
        lastRecordRule.validate(lastLineDTO);
    }

    public static void applyDomainValidationRules(List<MeterReadDTO> meterReadList){

        List<MeterReadRule> meterReadRuleList = new ArrayList<MeterReadRule>();
        meterReadRuleList.add(new NMILengthRule());
        meterReadRuleList.add(new EnergyUnitRule());
        meterReadRuleList.add(new QualityRule());
        for(MeterReadDTO meterReadDTO:meterReadList){

            for(MeterReadRule meterDataRule:meterReadRuleList ){

                meterDataRule.validate(meterReadDTO);
            }

        }

    }
}
