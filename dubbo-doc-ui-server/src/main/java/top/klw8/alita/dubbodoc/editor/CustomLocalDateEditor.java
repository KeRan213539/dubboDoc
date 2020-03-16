package top.klw8.alita.dubbodoc.editor;


import top.klw8.alita.dubbodoc.utils.LocalDateTimeUtil;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: CustomLocalDateEditor
 * @Description: 自定义 LocalDate 编辑器
 * @author klw
 * @date 2019年1月31日 下午6:41:58
 */
public class CustomLocalDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDateTimeUtil.formatToLD(text));
    }
    
    @Override
    public String getAsText() {
	LocalDate date = (LocalDate)getValue();
	return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    
}
