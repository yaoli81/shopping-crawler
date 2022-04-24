# 購物網小爬蟲

這是一個簡單的購物網站的爬蟲，可以在一個介面搜尋不同的購物網站的商品。

目前提供搜尋 [蝦皮](https://shopee.tw) 與 [momo](https://www.momoshop.com.tw) 這 2 個台灣常用的購物網站，可以查詢商品關鍵字與頁數，將爬取的結果回傳統一的 Json 格式，並將此功能做成 Restful 的 API。



## 開始使用

目前在 Google 雲端平台中的 Google App Engine 服務上提供 Demo 網站 (如下連結)，可直接使用。

https://shopping-crawler-dot-gcp-various-demo.uc.r.appspot.com

P.S. 第一次進入可能需要等待 10 餘秒雲端 Tomcat 的啟動

## 使用技術


| 類別          | 技術               |
|-------------|:----------------------- |
| 後端          | Java (Spring Boot)   |
| 前端          | Bootstrap、jQuery、FreeMarker (類似 Thymeleaf 的模版引擎)     |
| 架設平台        | Google雲端平台(GCP) - Google App Engine     |

## Restful API 介紹

- 查詢特定購物網的 API
  /api/fetch/shopping/site/search/{購物網站}/{商品關鍵字}/{頁數}
    - {購物網站}：shopee/momo (目前只提供蝦皮與 momo)
    - 範例：/api/fetch/shopping/site/search/shopee/電風扇/2


- 回傳 Json 格式
 ```json
 {
	"success": "true/false",
	"data": [
		{
			"product_name": "商品名稱",
			"price_min": "最低金額",
			"price_max": "最高金額",
			"image_url": "商品圖片連結"
        }
	]
}
 ```

## 簡易操作介面介紹

- 由於如果只提供 API 較難以進行測試，本系統提供一簡易介面進行操作。
- 介面可選擇購物平台，輸入商品關鍵字與頁數進行查詢。
  ![](https://i.imgur.com/bzygEuA.png)
- 搜尋後可點擊 API (Json 格式) 的連結，來取得 Json 資料。
  ![](https://i.imgur.com/UtZfVFL.png)
