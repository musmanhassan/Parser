package au.com.redenergy.parser;

import au.com.redenergy.model.MeterReadDTO;
import au.com.redenergy.service.FileParserService;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by Usman on 6/05/2017.
 */
public class MeterDataParserFileImpl implements MeterDataParser {

    @Override
    public Collection<MeterReadDTO> parseMeterData(File meterDataFile) {

        Collection<MeterReadDTO> meterReadList = null;
        meterReadList = new FileParserService().getMeterData(meterDataFile);

        return meterReadList;
    }
}
