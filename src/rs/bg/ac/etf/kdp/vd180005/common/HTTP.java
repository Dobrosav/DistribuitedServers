package rs.bg.ac.etf.kdp.vd180005.common;

/**
 *
 * @author Aleksandar Abu-Samra
 */
public class HTTP {

	/**
	 * Useful constants
	 */
	public static final String CRLF = "\r\n";

	/**
	 * HTTP status codes https://en.wikipedia.org/wiki/Http_status_codes
	 */
	public static class STATUS {
		// 2xx success

		public static final String OK = "HTTP/1.1 200 OK" + CRLF;
		// 4xx client error
		public static final String FORBIDDEN = "HTTP/1.1 403 Forbidden" + CRLF;
		public static final String NOT_FOUND = "HTTP/1.1 404 Not Found" + CRLF;
	}

	/**
	 * MIME types https://en.wikipedia.org/wiki/Mime_types
	 */
	public static class MIME_TYPE {

		public static final String TEXT_PLAIN = "Content-Type: text/plain" + CRLF;
		public static final String TEXT_HTML = "Content-Type: text/html" + CRLF;
	}

	/**
	 * HTTP 1.1 Request messages
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html
	 */
	public static String getRequestString(String method, String requestUri) {
		return method
				+ " " + requestUri
				+ " " + "HTTP/1.1"
				+ CRLF;
	}
	
	public static String getHeaderString(String type, String value) {
		return type
				+ " " + value
				+ CRLF;
	}
}
