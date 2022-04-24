package khc.yao.shoppingcrawler.service.shopping.site.persistent;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDO {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("price_min")
    private String priceMin;
    @JsonProperty("price_max")
    private String priceMax;
    @JsonProperty("image_url")
    private String imageURL;
}
