package web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Http request header parameters:
 * 
 * <table>
 * <tr>
 * <td>
 * <tr>
 * <td><b> Accept <b>
 * <td>Content-Types that are acceptable Accept: text/plain
 * <tr>
 * <td><b> Accept-Charset <b>
 * <td>Character sets that are acceptable Accept-Charset: utf-8
 * <tr>
 * <td><b> Accept-Encoding <b>
 * <td>Acceptable encodings. See HTTP compression. Accept-Encoding: gzip, deflate
 * <tr>
 * <td><b> Accept-Language <b>
 * <td>Acceptable languages for response Accept-Language: en-US
 * <tr>
 * <td><b> Accept-Datetime <b>
 * <td>Acceptable version in time Accept-Datetime: Thu, 31 May 2007 20:35:00 GMT
 * <tr>
 * <td><b> Authorization <b>
 * <td>Authentication credentials for HTTP authentication Authorization: Basic
 * QWxhZGRpbjpvcGVuIHNlc2FtZQ==
 * <tr>
 * <td><b> Cache-Control <b>
 * <td>Used to specify directives that MUST be obeyed by all caching mechanisms along the
 * request/response chain Cache-Control: no-cache
 * <tr>
 * <td><b> Connection <b>
 * <td>What type of connection the user-agent would prefer Connection: keep-alive
 * <tr>
 * <td><b> Cookie <b>
 * <td>an HTTP cookie previously sent by the server with Set-Cookie (below) Cookie: $Version=1;
 * Skin=new;
 * <tr>
 * <td><b> Content-Length <b>
 * <td>The length of the request body in octets (8-bit bytes) Content-Length: 348
 * <tr>
 * <td><b> Content-MD5 <b>
 * <td>A Base64-encoded binary MD5 sum of the content of the request body Content-MD5:
 * Q2hlY2sgSW50ZWdyaXR5IQ==
 * <tr>
 * <td><b> Content-Type <b>
 * <td>The MIME type of the body of the request (used with POST and PUT requests) Content-Type:
 * application/x-www-form-urlencoded
 * <tr>
 * <td><b> Date <b>
 * <td>The date and time that the message was sent Date: Tue, 15 Nov 1994 08:12:31 GMT
 * <tr>
 * <td><b> Expect <b>
 * <td>Indicates that particular server behaviors are required by the client Expect: 100-continue
 * <tr>
 * <td><b> From <b>
 * <td>The email address of the user making the request From: user@example.com
 * <tr>
 * <td><b> Host <b>
 * <td>The domain name of the server (for virtual hosting), and the TCP port number on which the
 * server is listening. The port number may be omitted if the port is the standard port for the
 * service requested.[5] Mandatory since HTTP/1.1. Although domain name are specified as
 * case-insensitive,[6][7] it is not specified whether the contents of the Host field should be
 * interpreted in a case-insensitive manner[8] and in practice some implementations of virtual
 * hosting interpret the contents of the Host field in a case-sensitive manner.[citation needed]
 * Host: en.wikipedia.org:80 Host: en.wikipedia.org
 * <tr>
 * <td><b> If-Match <b>
 * <td>Only perform the action if the client supplied entity matches the same entity on the server.
 * This is mainly for methods like PUT to only update a resource if it has not been modified since
 * the user last updated it. If-Match: "737060cd8c284d8af7ad3082f209582d"
 * <tr>
 * <td><b> If-Modified-Since <b>
 * <td>Allows a 304 Not Modified to be returned if content is unchanged If-Modified-Since: Sat, 29
 * Oct 1994 19:43:31 GMT
 * <tr>
 * <td><b> If-None-Match <b>
 * <td>Allows a 304 Not Modified to be returned if content is unchanged, see HTTP ETag
 * If-None-Match: "737060cd8c284d8af7ad3082f209582d"
 * <tr>
 * <td><b> If-Range <b>
 * <td>If the entity is unchanged, send me the part(s) that I am missing; otherwise, send me the
 * entire new entity If-Range: "737060cd8c284d8af7ad3082f209582d"
 * <tr>
 * <td><b> If-Unmodified-Since <b>
 * <td>Only send the response if the entity has not been modified since a specific time.
 * If-Unmodified-Since: Sat, 29 Oct 1994 19:43:31 GMT
 * <tr>
 * <td><b> Max-Forwards <b>
 * <td>Limit the number of times the message can be forwarded through proxies or gateways.
 * Max-Forwards: 10
 * <tr>
 * <td><b> Pragma <b>
 * <td>Implementation-specific headers that may have various effects anywhere along the
 * request-response chain. Pragma: no-cache
 * <tr>
 * <td><b> Proxy-Authorization <b>
 * <td>Authorization credentials for connecting to a proxy. Proxy-Authorization: Basic
 * QWxhZGRpbjpvcGVuIHNlc2FtZQ==
 * <tr>
 * <td><b> Range <b>
 * <td>Request only part of an entity. Bytes are numbered from 0. Range: bytes=500-999
 * <tr>
 * <td><b> Referer[sic] <b>
 * <td>This is the address of the previous web page from which a link to the currently requested
 * page was followed. (The word “referrer” is misspelled in the RFC as well as in most
 * implementations.) Referer: http://en.wikipedia.org/wiki/Main_Page
 * <tr>
 * <td><b> TE <b>
 * <td>The transfer encodings the user agent is willing to accept: the same values as for the
 * response header Transfer-Encoding can be used, plus the "trailers" value (related to the
 * "chunked" transfer method) to notify the server it expects to receive additional headers (the
 * trailers) after the last, zero-sized, chunk. TE: trailers, deflate
 * <tr>
 * <td><b> Upgrade <b>
 * <td>Ask the server to upgrade to another protocol. Upgrade: HTTP/2.0, SHTTP/1.3, IRC/6.9, RTA/x11
 * <tr>
 * <td><b> User-Agent <b>
 * <td>The user agent string of the user agent User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:12.0)
 * Gecko/20100101 Firefox/12.0
 * <tr>
 * <td><b> Via <b>
 * <td>Informs the server of proxies through which the request was sent. Via: 1.0 fred, 1.1
 * example.com (Apache/1.1)
 * <tr>
 * <td><b> Warning <b>
 * <td>A general warning about possible problems with the entity body. Warning: 199 Miscellaneous
 * warning
 * </table>
 * 
 * @author Michal Michaluk <michaluk.michal@gmail.com>
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Documented
public @interface HttpParam {
    
    public enum Name {
        Accept("Accept"),
        AcceptCharset("Accept-Charset"),
        AcceptEncoding("Accept-Encoding"),
        AcceptLanguage("Accept-Language"),
        AcceptDatetime("Accept-Datetime"),
        Authorization("Authorization"),
        CacheControl("Cache-Control"),
        Connection("Connection"),
        Cookie("Cookie"),
        ContentLength("Content-Length"),
        ContentMD5("Content-MD5"),
        ContentType("Content-Type"),
        Date("Date"),
        Expect("Expect"),
        From("From"),
        Host("Host"),
        IfMatch("If-Match"),
        IfModifiedSince("If-Modified-Since"),
        IfNoneMatch("If-None-Match"),
        IfRange("If-Range"),
        IfUnmodifiedSince("If-Unmodified-Since"),
        MaxForwards("Max-Forwards"),
        Pragma("Pragma"),
        ProxyAuthorization("Proxy-Authorization"),
        Range("Range"),
        Referer("Referer"),
        TE("TE"),
        Upgrade("Upgrade"),
        UserAgent("User-Agent"),
        Via("Via"),
        Warning("Warning");
        
        private final String name;
        
        private Name(String name) {
            this.name = name;
            
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    @Nonbinding
    public Name value();
    
}
