package khc.yao.shoppingcrawler.controller.freemarker;

import com.google.appengine.repackaged.com.google.gson.Gson;
import khc.yao.shoppingcrawler.service.shopping.site.ShoppingSiteService;
import khc.yao.shoppingcrawler.service.shopping.site.dto.FetchSiteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class ShowFetchSite {
    private final ShoppingSiteService service;

    public ShowFetchSite(ShoppingSiteService service) {
        this.service = service;
    }

    @GetMapping
    public String index(ModelMap map) {
        map.addAttribute("isSearch", false);
        map.addAttribute("site", "shopee");
        map.addAttribute("keyword", "");
        map.addAttribute("page", 1);
        return "/index";
    }

    @GetMapping(value = {"/{site}/{keywod}/{page}"})
    public String index(ModelMap map,
                        @PathVariable(value = "site") String site,
                        @PathVariable(value = "keywod") String keyword,
                        @PathVariable(value = "page") Integer page) throws ClassNotFoundException {
        map.addAttribute("isSearch", true);
        if ("***".equals(site) && "***".equals(keyword) && page == 0) {
            map.addAttribute("productDOList", new ArrayList<>());
        } else {
            FetchSiteDTO fetchSiteDTO = service.fetchSite(site, keyword, page);
            map.addAttribute("productDOList", fetchSiteDTO.getData());
            Gson gson = new Gson();
            map.addAttribute("JsonData", gson.toJson(fetchSiteDTO));
            String api = "/api/fetch/shopping/site/search/" + site + "/" + keyword + "/" + page;
            map.addAttribute("API", api);
            map.addAttribute("site", site);
            map.addAttribute("keyword", keyword);
            map.addAttribute("page", page);
        }
        return "/index";
    }
}
