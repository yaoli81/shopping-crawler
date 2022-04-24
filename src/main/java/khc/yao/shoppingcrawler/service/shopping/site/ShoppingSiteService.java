package khc.yao.shoppingcrawler.service.shopping.site;

import khc.yao.shoppingcrawler.common.constant.CommonConstant;
import khc.yao.shoppingcrawler.service.shopping.site.dto.FetchSiteDTO;
import khc.yao.shoppingcrawler.service.shopping.site.module.FetchSiteModule;
import khc.yao.shoppingcrawler.service.shopping.site.persistent.ProductDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingSiteService {
    public FetchSiteDTO fetchSite(String site, String keyword, Integer page) throws ClassNotFoundException {
        List<ProductDO> productDOList = FetchSiteModule.fetchShoppingSite(site, keyword, page);
        FetchSiteDTO returnDTO = new FetchSiteDTO(productDOList);
        if (productDOList.size() == 0) {
            returnDTO.setSuccess(CommonConstant.FALSE);
            returnDTO.setMessage("爬取失敗或無資料");
        }
        return returnDTO;
    }
}
