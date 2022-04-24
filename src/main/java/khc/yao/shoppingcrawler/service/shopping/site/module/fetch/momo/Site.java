package khc.yao.shoppingcrawler.service.shopping.site.module.fetch.momo;

import khc.yao.shoppingcrawler.common.constant.CommonConstant;
import khc.yao.shoppingcrawler.common.util.SSLCheckUtil;
import khc.yao.shoppingcrawler.service.shopping.site.module.FetchSiteModule;
import khc.yao.shoppingcrawler.service.shopping.site.persistent.ProductDO;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class Site extends FetchSiteModule {
    private static final String parseUrl = "https://www.momoshop.com.tw/ajax/ajaxTool.jsp?n=2018&t=";

    public static void main(String[] args) {
        showParseResultInfo(new Site().parsing("衛生紙", 1));
    }

    @Override
    protected List<ProductDO> parsing(String keyword, Integer page) {
        long nowTime = System.currentTimeMillis();
        List<ProductDO> productDOList = new ArrayList<>();
        try {
            // 設定 disable SSL Check
            SSLCheckUtil.disableSSLCertCheck();
            // 先到 Momo 首頁取得 cookies
            Connection.Response res = Jsoup.connect("https://www.momoshop.com.tw").timeout(CommonConstant.timeout).execute();
            Map<String, String> cookies = res.cookies();
            // 擷取
            Document doc = Jsoup.connect(parseUrl + nowTime)
                    .ignoreContentType(true)
                    .userAgent(CommonConstant.userAgent)
                    .timeout(CommonConstant.timeout)
                    .data("data", "{\"flag\":2018,\"data\":{\"specialGoodsType\":\"\",\"authorNo\":\"\",\"searchValue\":\"" + keyword + "\",\"cateCode\":\"\",\"cateLevel\":\"-1\",\"cp\":\"N\",\"NAM\":\"N\",\"first\":\"N\",\"freeze\":\"N\",\"superstore\":\"N\",\"tvshop\":\"N\",\"china\":\"N\",\"tomorrow\":\"N\",\"stockYN\":\"N\",\"prefere\":\"N\",\"threeHours\":\"N\",\"showType\":\"chessboardType\",\"curPage\":\"" + page + "\",\"priceS\":\"0\",\"priceE\":\"9999999\",\"searchType\":\"1\",\"reduceKeyword\":\"\",\"isFuzzy\":\"0\",\"rtnCateDatainfo\":{\"cateCode\":\"\",\"cateLv\":\"-1\",\"keyword\":\"" + keyword + "\",\"curPage\":\"" + page + "\",\"historyDoPush\":true,\"timestamp\":" + nowTime + "}}}")
                    .header("referer", "https://www.momoshop.com.tw/search/searchShop.jsp?keyword=" + keyword + "&searchType=1&cateLevel=0&cateCode=&curPage=" + page + "&_isFuzzy=0&showType=chessboardType")
                    .cookies(cookies)
                    .post();
            // 解析
            JSONObject bodyJsonObject = new JSONObject(doc.text());
            JSONObject returnDataJsonObject = bodyJsonObject.getJSONObject("rtnData");
            JSONObject searchResultJsonObject = returnDataJsonObject.getJSONObject("searchResult");
            JSONObject rtnSearchDataJsonObject = searchResultJsonObject.getJSONObject("rtnSearchData");
            JSONArray itemsJsonArray = rtnSearchDataJsonObject.getJSONArray("goodsInfoList");
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject dataJson = itemsJsonArray.getJSONObject(i);
                String name = dataJson.getString("goodsName");
                String priceMin = dataJson.getString("goodsPrice");
                priceMin = priceMin.replace("$", "")
                        .replace(",", "");
                if (priceMin.contains(" ")) {
                    priceMin = priceMin.substring(0, priceMin.indexOf(" "));
                    priceMin = priceMin.replace(" ", "");
                }
                String priceMax = priceMin;
                String imageURL = dataJson.getString("imgUrl");
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
