package net.gaox.sponsor.utils;

import net.gaox.common.api.ApiException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @site gaox.net
 * @date 2019/12/18 20:23
 */
public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {

        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString)
            throws ApiException {

        try {
            return DateUtils.parseDate(
                    dateString, parsePatterns
            );
        } catch (Exception ex) {
            throw new ApiException(ex.getMessage());
        }
    }
}
