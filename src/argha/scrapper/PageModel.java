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

/**
 *
 * @author Argha Das
 */
public class PageModel {

    private String thumbnail;
    private String thumbnailLink;
    private String title;
    private String titleLink;
    private int ratings;
    private String price;
    private String stock;

    public PageModel(String thumbnail, String thumbnailLink, String title, String titleLink, int ratings, String price, String stock) {
        this.thumbnail = thumbnail;
        this.thumbnailLink = thumbnailLink;
        this.title = title;
        this.titleLink = titleLink;
        this.ratings = ratings;
        this.price = price;
        this.stock = stock;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleLink() {
        return titleLink;
    }

    public int getRatings() {
        return ratings;
    }

    public String getPrice() {
        return price;
    }

    public String getStock() {
        return stock;
    }
}
