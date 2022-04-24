package khc.yao.shoppingcrawler.controller.shopping.site;

import khc.yao.shoppingcrawler.service.shopping.site.ShoppingSiteService;
import khc.yao.shoppingcrawler.service.shopping.site.dto.FetchSiteDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fetch/shopping/site")
public class ShoppingSiteController {
    private final ShoppingSiteService service;

    public ShoppingSiteController(ShoppingSiteService service) {
        this.service = service;
    }

    @GetMapping("/search/{site}/{keywod}/{page}")
    public FetchSiteDTO fetchCBID(
            @PathVariable(value = "site") String site,
            @PathVariable(value = "keywod") String keywod,
            @PathVariable(value = "page") Integer page) throws ClassNotFoundException {
        return service.fetchSite(site, keywod, page);
    }
}
