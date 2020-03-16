package top.klw8.alita.dubbodoc.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: MybatisDemoController
 * @Description: mybatis demo
 * @date 2019/8/8 15:47
 */
public class MyCustomDateEditor extends PropertyEditorSupport {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(this.dateAdapter(text));
        }
    }

    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (value != null ? dateFormat.format(value) : "");
    }

    /**
     * 字符串转日期适配方法
     *
     * @param dateStr 日期字符串
     * @throws
     */
    public static Date dateAdapter(String dateStr) {
        Date date = null;
        String temp = dateStr;//缓存原始数据

        if (!(null == dateStr || "".equals(dateStr))) {
            //判断是不是时间戳
            try{
                long timeLong = Long.parseLong(dateStr);
                date = new Date(timeLong);

                return date;
            }catch(Exception e){

            }

            //判断是不是日期字符串，如Wed May 28 08:00:00 CST 2014
            if (dateStr.contains("CST")) {
                date = new Date(dateStr);
            }else if(dateStr.contains("Z")){
                dateStr = dateStr.replace("\"", "");
                dateStr = dateStr.replace("Z", " UTC");
                SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                try {
                    date = utcFormat.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                dateStr = dateStr.replace("年", "-").replace("月", "-").replace("日", "").replaceAll("/", "-").replaceAll("\\.", "-").trim();
                String fm = "";

                //确定日期格式
                if (Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()) {
                    fm = "yyyy-MM-dd";
                } else if (Pattern.compile("^[0-9]{4}-[0-9]{1}-[0-9]+.*||^[0-9]{4}-[0-9]+-[0-9]{1}.*").matcher(dateStr).matches()) {
                    fm = "yyyy-M-d";
                } else if (Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()) {
                    fm = "yy-MM-dd";
                } else if (Pattern.compile("^[0-9]{2}-[0-9]{1}-[0-9]+.*||^[0-9]{2}-[0-9]+-[0-9]{1}.*").matcher(dateStr).matches()) {
                    fm = "yy-M-d";
                }

                //确定时间格式
                if (Pattern.compile(".*[ ][0-9]{2}").matcher(dateStr).matches()) {
                    fm += " HH";
                } else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}").matcher(dateStr).matches()) {
                    fm += " HH:mm";
                } else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}").matcher(dateStr).matches()) {
                    fm += " HH:mm:ss";
                } else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{0,3}").matcher(dateStr).matches()) {
                    fm += " HH:mm:ss:sss";
                }

                if (!"".equals(fm)) {
                    try {
                        date = new SimpleDateFormat(fm).parse(dateStr);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("参数字符串"+dateStr+"无法被转换为日期格式！");
                    }
                }
            }
        }

        return date;
    }
}
