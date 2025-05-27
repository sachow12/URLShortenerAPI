package urlshortener.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class URLDTO {
    private String originalUrl;
    private String newUrl;
}