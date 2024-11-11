package gmky.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;
import java.util.List;

@UtilityClass
public class ResponseUtil {
    private static final String X_TOTAL_COUNT_HEADER = "X-TOTAL-COUNT";

    public static <T> ResponseEntity<T> data(T data) {
        return ResponseEntity.ok(data);
    }

    public static <T> ResponseEntity<List<T>> data(Page<T> page) {
        var headers = generatePaginationHeader(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<Void> created() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public static <T> HttpHeaders generatePaginationHeader(ServletUriComponentsBuilder uriBuilder, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        if (page == null) return headers;
        headers.add(X_TOTAL_COUNT_HEADER, Long.toString(page.getTotalElements()));
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();
        StringBuilder link = new StringBuilder();
        if (pageNumber < page.getTotalPages() - 1) {
            link.append(prepareLink(uriBuilder, pageNumber + 1, pageSize, "next")).append(",");
        }

        if (pageNumber > 0) {
            link.append(prepareLink(uriBuilder, pageNumber - 1, pageSize, "prev")).append(",");
        }

        link.append(prepareLink(uriBuilder, page.getTotalPages() - 1, pageSize, "last")).append(",").append(prepareLink(uriBuilder, 0, pageSize, "first"));
        headers.add("Link", link.toString());
        return headers;
    }

    private static String prepareLink(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize, String relType) {
        return MessageFormat.format("<{0}>; rel=\"{1}\"", preparePageUri(uriBuilder, pageNumber, pageSize), relType);
    }

    private static String preparePageUri(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize) {
        return uriBuilder.replaceQueryParam("page", Integer.toString(pageNumber)).replaceQueryParam("size", Integer.toString(pageSize)).toUriString().replace(",", "%2C").replace(";", "%3B");
    }
}
