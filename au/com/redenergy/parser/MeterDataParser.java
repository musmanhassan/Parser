package au.com.redenergy.parser;

import au.com.redenergy.model.MeterReadDTO;

import java.io.File;
import java.util.Collection;

/**
 * Created by Usman on 5/05/2017.
 */
public interface MeterDataParser {

    /**
     * Parses Simple NEM12 file.
     *
     * @param meterDataFile file in Simple NEM12 format
     * @return Collection of <code>MeterReadDTO</code> that represents the data in the given file.
     */

    Collection<MeterReadDTO> parseMeterData(File meterDataFile);
}
