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
package argha.scrapper.util;

import argha.scrapper.CategoryModel;
import argha.scrapper.Endpoints;
import argha.scrapper.PageModel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Argha Das
 */
public class JsonUtil {

    public JsonUtil() {

    }

    public String parseCategories(List<CategoryModel> model) {
        String urlPrefix = new Endpoints().getUrlPrefix();
        JSONObject root = new JSONObject();
        root.put("total_categories", model.size());
        JSONArray categories = new JSONArray();
        for (int i = 0; i < model.size(); i++) {
            JSONObject category = new JSONObject();
            category.put("title", model.get(i).getTitle());
            category.put("link", urlPrefix + model.get(i).getLink());
            categories.put(category);
        }
        root.put("categories", categories);
        return root.toString();
    }

    public String parseFirstPage(List<PageModel> model) {
        String urlPrefix = new Endpoints().getUrlPrefix();
        JSONObject root = new JSONObject();
        root.put("total_products", model.size());
        JSONArray products = new JSONArray();
        for (int i = 0; i < model.size(); i++) {
            JSONObject product = new JSONObject();
            product.put("thumbnail", urlPrefix + model.get(i).getThumbnail());
            product.put("thumbnailClick", urlPrefix + model.get(i).getThumbnailLink());
            product.put("title", model.get(i).getTitle());
            product.put("titleClick", urlPrefix + model.get(i).getTitleLink());
            product.put("price", model.get(i).getPrice());
            product.put("rating", model.get(i).getRatings());
            product.put("stock", model.get(i).getStock());
            
            products.put(product);
        }
        root.put("books", products);
        return root.toString();
    }
}
