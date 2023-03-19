package org.mycompany.report.converters.json;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

public class InstantToUnix extends StdConverter<Instant, Long> {


    @Override
    public Long convert(Instant instant) {
        return instant.toEpochMilli();
    }
}
