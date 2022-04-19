package com.siretreader.ms.common.properties;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "siretreader")
@Getter
@Setter
@NoArgsConstructor
public class SiretReaderProperties {

    private String url;

    private List<String> siretNumbers;
}
