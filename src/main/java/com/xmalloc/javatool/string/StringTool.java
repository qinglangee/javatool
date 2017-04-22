package com.xmalloc.javatool.string;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringTool {
    private static final Logger LOG = LoggerFactory.getLogger(StringTool.class);

    // Empty checks
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Checks if a String is empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * Checks if a String is not empty ("") and not null.
     * </p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 0.0.2
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a String is not empty (""), not null and not whitespace only.
     * </p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null and not
     *         whitespace
     * @since 0.0.2
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * ids转换成列表
     * @param delimiters 分隔符
     * @param ids
     * @return
     */
    public static List<Long> idsToList(String ids) {
        return idsToList(new String[] { "," }, ids);
    }

    /**
     * ids转换成列表
     * @param delimiters 分隔符
     * @param ids
     * @return
     */
    public static List<Long> idsToList(String[] delimiters, String ids) {
        List<Long> result = new ArrayList<Long>();
        if (isEmpty(ids)) {
            return result;
        }

        if (delimiters != null) {
            for (String delimiter : delimiters) {
                if (!",".equals(delimiter))
                    ids = ids.replace(delimiter, ",");
            }
        }

        String[] array = ids.split(",");
        for (String id : array) {
            if (!isEmpty(id))
                try {
                    result.add(Long.parseLong(id.trim()));
                } catch (Exception e) {
                    // just do nothing
                }
        }

        return result;
    }

    /**
     * 逗号分隔的ids转换成数组
     * @param idStr
     * @return
     */
    public static Long[] idsToArray(String idStr) {
        Long[] targetIds = null;
        try {
            if (isNotEmpty(idStr)) {
                String[] ids = idStr.split(",");
                targetIds = new Long[ids.length];
                for (int i = 0; i < ids.length; i++) {
                    if (isEmpty(ids[i])) {
                        continue;
                    }
                    try {
                        targetIds[i] = Long.valueOf(ids[i]);
                    } catch (Exception e) {
                        // just do nothing
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return targetIds;
    }

    /**
     * 多个字符串数组合并成一个
     * @param arrs 多个字符串数组
     * @return
     */
    public static String[] contactArr(String[]... arrs) {
        int size = 0;
        for (String[] arr : arrs) {
            if (arr != null) {
                size += arr.length;
            }
        }
        String[] result = new String[size];
        int index = 0;
        for (String[] arr : arrs) {
            if (arr == null || arr.length == 0) {
                continue;
            }
            for (String str : arr) {
                result[index++] = str;
            }
        }
        return result;
    }

    /**
     * 从文件路径中截取出文件名
     * @param filepath
     * @return file name
     */
    public static String parseFilename(String filepath) {
        if (filepath == null) {
            return null;
        }
        if (filepath.indexOf('/') >= 0) {
            return filepath.substring(filepath.lastIndexOf('/') + 1);
        }
        if (filepath.indexOf('\\') >= 0) {
            return filepath.substring(filepath.lastIndexOf('\\') + 1);
        }
        return filepath;
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }
}
