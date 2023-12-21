package fact.it.preferenceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "preference")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Preference {
    private String id;
    //private String code;
    private String name;
    private String userId;
}
