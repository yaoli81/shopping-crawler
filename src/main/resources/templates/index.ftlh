<#include "/header.ftlh"/>
<script>
    $(document).ready(function() {
        $("#btnSearch").click(function(){
            var site = $("input[name='btnradio']:checked").attr("value");
            var keywod = $("#inputKeyword").val();
            var page = $("#inputPage").val();
            if (keywod.trim() === "") {
                alert("請輸入商品關鍵字");
            } else {
                location.href = "/" + site + "/" + keywod + "/" + page;
            }
        });
    });
</script>
<body>
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <form class="d-flex">
                <div style="margin-right: 10px" class="btn-group" role="group">
                    <input type="radio" class="btn-check" name="btnradio" id="btnShopee" value="shopee" autocomplete="off"
                            <#if (site == "shopee")>checked</#if>>
                    <label class="btn btn-outline-primary" for="btnShopee">蝦皮</label>
                    <input type="radio" class="btn-check" name="btnradio" id="btnMomo" value="momo" autocomplete="off"
                           <#if (site == "momo")>checked</#if>>
                    <label class="btn btn-outline-primary" for="btnMomo">momo</label>
                </div>
                <input class="form-control me-2" type="search" placeholder="商品關鍵字" id="inputKeyword" value="${keyword}">
                頁數：<input class="form-control me-2" type="number" min="1" id="inputPage" value="${page}">
                <button id="btnSearch" class="btn btn-outline-success" type="button">Search</button>
            </form>
        </div>
    </nav>

    <#if isSearch == false>

    <#else>
        <h4>&nbsp;
            <span class="badge rounded-pill bg-success">API (Json 格式)</span>
            <a href="${API}">${API}</a>
        </h4>&nbsp;
        <table class="table table-sm">
            <thead class="text-center">
            <tr>
                <th scope="col">商品圖</th>
                <th scope="col">商品名</th>
                <th scope="col">價格</th>
            </tr>
            </thead>
            <tbody>
            <#list productDOList as productDO>
                <tr>
                    <td><img src="${productDO.imageURL}"></td>
                    <td>${productDO.productName}</td>
                    <td>
                        <#if productDO.priceMin == productDO.priceMax>
                            ${productDO.priceMin}
                        <#else>
                            ${productDO.priceMin}~${productDO.priceMax}
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</body>

