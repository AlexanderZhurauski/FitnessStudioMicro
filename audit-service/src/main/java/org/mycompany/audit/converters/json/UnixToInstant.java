package org.mycompany.audit.converters.json;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

public class UnixToInstant extends StdConverter<Long, Instant> {

    @Override
    public Instant convert(Long unixTimestamp) {
        return Instant.ofEpochMilli(unixTimestamp);
    }
}
