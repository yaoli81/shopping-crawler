package khc.yao.shoppingcrawler.service.shopping.site.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import khc.yao.shoppingcrawler.common.constant.CommonConstant;
import khc.yao.shoppingcrawler.common.dto.BasicResponseDTO;
import khc.yao.shoppingcrawler.service.shopping.site.persistent.ProductDO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetchSiteDTO extends BasicResponseDTO {

    private List<ProductDO> data;

    public FetchSiteDTO(List<ProductDO> productDOList) {
        super();
        setSuccess(CommonConstant.TRUE);
        data = productDOList;
    }
}
