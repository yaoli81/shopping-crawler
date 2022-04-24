package khc.yao.shoppingcrawler.common.dto;

import khc.yao.shoppingcrawler.common.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BasicResponseDTO {
    private String success;
    private String message;

    public BasicResponseDTO() {
        success = CommonConstant.FALSE;
    }
}
