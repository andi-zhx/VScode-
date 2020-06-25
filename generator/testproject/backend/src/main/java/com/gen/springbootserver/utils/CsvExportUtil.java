package com.gen.springbootserver.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvExportUtil {
    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = ",";
    /** CSV文件列分隔符 */
    private static final String CSV_RN = "\r\n";
    public static ResponseEntity<byte[]> exportcsv(String header ,List<?> list){
        HttpHeaders headers = CsvExportUtil.setCsvHeader(header);
        byte[] value = null;
        if (list.size() > 0) {
            value = doListExport(list).toByteArray();
        }
        return new ResponseEntity<byte[]>(value, headers, HttpStatus.OK);
    }

    public static ByteArrayOutputStream doListExport(List<?> list) {
        try {
            Class<?> cls = list.get(0).getClass();
            Field[] fields = cls.getDeclaredFields();
            StringBuffer buf = new StringBuffer();

            // 完成数据csv文件的封装
            // 输出列头
            for (int i = 0; i < fields.length; i++) {
                buf.append(fields[i].getName()).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);
    
            if (null != list) { // 输出数据
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < fields.length; j++) {
                        fields[j].setAccessible(true);  
                        Object a=fields[j].get(list.get(i));
                        buf.append(a).append(CSV_COLUMN_SEPARATOR);
                    }
                    buf.append(CSV_RN);
                }
            }
            // 写出响应
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            os.write(buf.toString().getBytes("GBK"));
            os.flush();
            os.close();
            return os;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpHeaders setCsvHeader(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        try {
            // 设置文件后缀
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = new String(fileName.getBytes("gbk"), "iso8859-1") + sdf.format(new Date()) + ".csv";
            // String filename = new String(fileName) + sdf.format(new Date()) + ".csv";
            headers.add("Pragma", "public");
            headers.add("Cache-Control", "max-age=30");
            headers.add("Content-Disposition", "attachment;filename=" + filename);
            headers.setContentType(MediaType.valueOf("application/vnd.ms-excel;charset=UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }

}