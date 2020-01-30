/*
 * The MIT License
 *
 * Copyright 2020 Argha Das.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package argha.scrapper;

import argha.scrapper.util.DocumentUtil;
import argha.scrapper.util.JsonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Argha Das
 */
public class ScrapePage {

    private String url;
    private List<CategoryModel> categoryModel = new ArrayList<>();
    private List<PageModel> pageModel = new ArrayList<>();

    public ScrapePage() {

    }

    public String getCategories() {
        try {
            Document document = DocumentUtil.getDocument(new Endpoints().getHomeUrl());
            Elements categories = document.select("div.side_categories > ul.nav-list > li").select("ul> li");
            for (Element category : categories) {
                String title = category.text();
                String link = category.select("a").attr("href");
                categoryModel.add(new CategoryModel(title, link));
            }
            return new JsonUtil().parseCategories(categoryModel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public ScrapePage(String url) {
        this.url = url;
    }

    public String getFirstPage() {
        try {
            Document document = DocumentUtil.getDocument(url);
            Elements products = document.select("article.product_pod");
            for (Element e : products) {
                pageModel.add(new PageModel(getThumbnail(e), getThumbnailLink(e), getTitle(e), getTitleLink(e), getRating(e), getPrice(e), getStock(e)));
            }
            return new JsonUtil().parseFirstPage(pageModel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private String getThumbnail(Element e) {
        return e.select("div.image_container").select("img.thumbnail").attr("src");
    }

    private String getThumbnailLink(Element e) {
        return e.select("div.image_container > a").attr("href");
    }

    private String getStock(Element e) {
        String checkStock = e.select("div.product_price > p.instock").text();
        if (checkStock.equals("In stock")) {
            return checkStock;
        }
        return "No stock";
    }

    private String getPrice(Element e) {
        return e.select("div.product_price > p.price_color").text();
    }

    private int getRating(Element e) {
        String[] rateValue = {"Zero", "One", "Two", "Three", "Four", "Five"};
        int[] rateValueInDigits = {0, 1, 2, 3, 4, 5};
        int ratings = 0;
        for (int i = 0; i < rateValue.length; i++) {
            if (e.select("p.star-rating").hasClass(rateValue[i])) {
                ratings = rateValueInDigits[i];
            }
        }
        return ratings;
    }

    private String getTitle(Element e) {
        return e.select("h3 > a").attr("title");
    }

    private String getTitleLink(Element e) {
        return e.select("h3 > a").attr("href");
    }
}
