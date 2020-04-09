/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @ClassName: CustomDateEditor
 * @Description: Date editor for controller
 * @date 2020/2/27 18:33
 */
public class CustomDateEditor extends PropertyEditorSupport {

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
     * @author klw(213539@qq.com)
     * @Description: String to date adaptation method
     * @Date 2020/4/8 17:31
     * @param: dateStr
     * @return java.util.Date
     */
    public static Date dateAdapter(String dateStr) {
        Date date = null;

        if (!(null == dateStr || "".equals(dateStr))) {
            try{
                long timeLong = Long.parseLong(dateStr);
                date = new Date(timeLong);

                return date;
            }catch(Exception e){

            }

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
                        throw new IllegalArgumentException(dateStr + " cannot be converted to date format！");
                    }
                }
            }
        }

        return date;
    }
}
