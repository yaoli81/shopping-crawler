package khc.yao.shoppingcrawler.service.shopping.site.module;

import com.google.appengine.repackaged.com.google.gson.Gson;
import khc.yao.shoppingcrawler.service.shopping.site.persistent.ProductDO;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public abstract class FetchSiteModule {
    protected abstract List<ProductDO> parsing(String keyword, Integer page);

    // for 開發時使用 (方便確認抓取是否正常)
    public static void showParseResultInfo(List<ProductDO> productDOList) {
        Gson gson = new Gson();
        for (ProductDO productDO : productDOList) {
            System.out.println(gson.toJson(productDO));
        }
    }

    public static List<ProductDO> fetchShoppingSite(String site, String keyword, Integer page) throws ClassNotFoundException {
        List<ProductDO> productDOList = new ArrayList<>();
        try {
            Class triggerClass = Class.forName(
                    "khc.yao.shoppingcrawler.service.shopping.site.module.fetch."
                            + site.toLowerCase() + ".Site");
            // 透過反射機制取得要執行的類別
            FetchSiteModule resultModule = (FetchSiteModule) triggerClass.getConstructor().newInstance();
            // 爬取來源網站並解析成結果資訊
            productDOList = resultModule.parsing(keyword, page);
        } catch (ClassNotFoundException e) {
            log.info("反射機制找不到類別 - Site: " + site);
            throw e;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            log.info("反射機制 Exception - Site: " + site);
        }
        return productDOList;
    }
}
