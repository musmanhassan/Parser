// Copyright Red Energy Limited 2017

package au.com.redenergy.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents a meter read, corresponds to RecordType 200 in SimpleNem12
 * <p>
 * The volumes property is a map that holds the date and associated <code>MeterVolumeDTO</code> on that date, values come from RecordType 300.
 */
public class MeterReadDTO extends BaseDTO {

    private String nmi;
    private EnergyUnit energyUnit;
    private SortedMap<LocalDate, MeterVolumeDTO> volumes = new TreeMap<>();

    public MeterReadDTO(String nmi, EnergyUnit energyUnit) {
        this.nmi = nmi;
        this.energyUnit = energyUnit;
    }

    public String getNmi() {
        return nmi;
    }

    public void setNmi(String nmi) {
        this.nmi = nmi;
    }

    public EnergyUnit getEnergyUnit() {
        return energyUnit;
    }

    public void setEnergyUnit(EnergyUnit energyUnit) {
        this.energyUnit = energyUnit;
    }

    public SortedMap<LocalDate, MeterVolumeDTO> getVolumes() {
        return volumes;
    }

    public void setVolumes(SortedMap<LocalDate, MeterVolumeDTO> volumes) {
        this.volumes = volumes;
    }

    MeterVolumeDTO getMeterVolume(LocalDate localDate) {
        return volumes.get(localDate);
    }

    public void appendVolume(LocalDate localDate, MeterVolumeDTO meterVolume) {
        volumes.put(localDate, meterVolume);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterReadDTO meterRead = (MeterReadDTO) o;
        return Objects.equals(getNmi(), meterRead.getNmi());
    }

    public int hashCode() {
        return Objects.hash(getNmi());
    }

    public BigDecimal getTotalVolume() {

        return volumes.values().stream()
                .map(mr -> mr.getVolume())
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
