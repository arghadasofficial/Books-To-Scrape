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
package argha.scrapper.engine;

import argha.scrapper.CategoryModel;
import argha.scrapper.Endpoints;
import argha.scrapper.PageModel;
import argha.scrapper.util.DocumentUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Argha Das
 */
public class Engine {

    private ExtractorEventListener listener;
    private List<CategoryModel> categories;
    private List<PageModel> page;
    private Document document;
    private String nextPageToken;

    public Engine(ExtractorEventListener extractorEventListener) {
        this.listener = extractorEventListener;
        categories = new ArrayList<>();
        page = new ArrayList<>();
    }

    public void startExtracting() {
        if (isFirstCall()) {
            try {

                fetchUrl(new Endpoints().getHomeUrl());
                listener.currentlyExtracting(new Endpoints().getHomeUrl());
                extractCategories();
                extractFirstHomePage();
                storeNextPageToken();

                startExtracting();
            } catch (IOException ex) {
            }
        }
        if (!nextPageToken.isBlank()) {
            try {
                fetchUrl(new Endpoints().getUrlPrefix() + nextPageToken);
                listener.currentlyExtracting(new Endpoints().getUrlPrefix() + nextPageToken);
                extractListOfBooks();
                storeNextPageToken();
                startExtracting();
            } catch (IOException ex) {
                try {
                    fetchUrl(new Endpoints().getUrlPrefixWorkaround() + nextPageToken);
                    listener.currentlyExtracting(new Endpoints().getUrlPrefixWorkaround() + nextPageToken);
                    extractListOfBooks();
                } catch (IOException ex1) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex1);
                }
                storeNextPageToken();
                startExtracting();
            }
        } else {
            for (PageModel model : page) {
                System.out.println("Title: " + model.getTitle() + "  |   Price: " + model.getPrice() + "  |   Cover: " + new Endpoints().getUrlPrefix() + 
                        model.getThumbnail().replace("..", ""));
                System.out.println("--------------------------------------------------------------------------------------------------------------");
            }
        }
    }

    private boolean isFirstCall() {
        if (categories.isEmpty()) {
            return true;
        }
        return false;
    }

    private void fetchUrl(String url) throws IOException {
        document = DocumentUtil.getDocument(url);
    }

    private void extractCategories() {
        Elements categoriesElement = document.select("div.side_categories > ul.nav-list > li").select("ul> li");
        for (Element category : categoriesElement) {
            String title = category.text();
            String link = category.select("a").attr("href");
            categories.add(new CategoryModel(title, link));
        }
    }

    private void extractFirstHomePage() {
        extractListOfBooks();
    }

    private void extractListOfBooks() {
        Elements products = document.select("article.product_pod");
        for (Element e : products) {
            page.add(new PageModel(getThumbnail(e), getThumbnailLink(e), getTitle(e), getTitleLink(e), getRating(e), getPrice(e), getStock(e)));
        }
    }

    private void storeNextPageToken() {
        if (document.select("ul").select("li").hasClass("next")) {
            nextPageToken = document.select("ul").select("li.next").select("a").attr("href");
        } else {
            nextPageToken = "";
        }
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
