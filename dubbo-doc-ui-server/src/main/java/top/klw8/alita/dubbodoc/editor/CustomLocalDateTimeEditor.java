package top.klw8.alita.dubbodoc.editor;


import top.klw8.alita.dubbodoc.utils.LocalDateTimeUtil;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: CustomLocalDateTimeEditor
 * @Description: 自定义 LocalDateTime 编辑器
 * @author klw
 * @date 2019年1月31日 下午6:41:58
 */
public class CustomLocalDateTimeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDateTimeUtil.formatToLDT(text));
    }
    
    @Override
    public String getAsText() {
	LocalDateTime date = (LocalDateTime)getValue();
	return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    
}
