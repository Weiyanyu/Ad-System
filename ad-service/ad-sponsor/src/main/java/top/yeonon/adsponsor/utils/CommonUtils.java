package top.yeonon.adsponsor.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import top.yeonon.adcommon.exception.AdException;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/23 0023 12:08
 **/
public class CommonUtils {

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    /**
     * 将String值转换成MD5值
     * @param value String值
     * @return MD5值
     */
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * 将String类型的时间转换成Java Date对象
     * @param dateStr String类型表示的时间
     * @return Java Date对象
     */
    public static Date stringToDate(String dateStr) throws AdException {
        try {
            return DateUtils.parseDate(dateStr, PARSE_PATTERNS);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }
}
