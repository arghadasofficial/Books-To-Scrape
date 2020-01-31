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
public class Endpoints {

    private String homeUrl = "http://books.toscrape.com/index.html";
    private String urlPrefixWorkaround = "http://books.toscrape.com/catalogue/";
    private String urlPrefix = "http://books.toscrape.com/";
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0";

    public Endpoints() {
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getUrlPrefixWorkaround() {
        return urlPrefixWorkaround;
    }

    
}
