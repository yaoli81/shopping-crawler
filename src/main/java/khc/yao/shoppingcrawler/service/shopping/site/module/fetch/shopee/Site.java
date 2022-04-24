package khc.yao.shoppingcrawler.service.shopping.site.module.fetch.shopee;

import khc.yao.shoppingcrawler.common.constant.CommonConstant;
import khc.yao.shoppingcrawler.service.shopping.site.persistent.ProductDO;
import khc.yao.shoppingcrawler.service.shopping.site.module.FetchSiteModule;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Site extends FetchSiteModule {
    private static final String parseUrl = "https://shopee.tw/api/v4/search/search_items?by=relevancy&keyword={0}&limit=60&newest={1}&order=desc&page_type=search&scenario=PAGE_GLOBAL_SEARCH&version=2";

    public static void main(String[] args) {
        showParseResultInfo(new Site().parsing("衛生紙", 1));
    }

    @Override
    protected List<ProductDO> parsing(String keyword, Integer page) {
        List<ProductDO> productDOList = new ArrayList<>();
        try {
            // 第 1 頁筆數從 0 開始
            page = page - 1;
            // 蝦皮網站預設 1 頁 60 筆資料，URL 需要帶第幾筆開始，所以將頁數乘上 60
            page = page * 60;
            // 擷取
            Document doc = Jsoup.connect(MessageFormat.format(parseUrl, keyword, page))
                    .ignoreContentType(true)
                    .userAgent(CommonConstant.userAgent)
                    .timeout(CommonConstant.timeout)
                    .get();
            // 解析
            JSONObject bodyJsonObject = new JSONObject(doc.text());
            JSONArray itemsJsonArray = bodyJsonObject.getJSONArray("items");
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject dataJson = itemsJsonArray.getJSONObject(i);
                JSONObject itemBasicJson = dataJson.getJSONObject("item_basic");
                String name = itemBasicJson.getString("name");
                String priceMin = String.valueOf(itemBasicJson.getInt("price_min"));
                priceMin = priceMin.substring(0, priceMin.length() - 5);
                String priceMax = String.valueOf(itemBasicJson.getInt("price_max"));
                priceMax = priceMax.substring(0, priceMax.length() - 5);
                String imageURL = "https://cf.shopee.tw/file/" + itemBasicJson.getString("image") + "_tn";
                if (Integer.parseInt(priceMin) > 0) {
                    // 價格 > 0 才回傳
                    productDOList.add(new ProductDO(
                            name,
                            priceMin,
                            priceMax,
                            imageURL));
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return productDOList;
    }
}
