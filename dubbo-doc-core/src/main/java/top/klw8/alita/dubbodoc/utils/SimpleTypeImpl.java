package top.klw8.alita.dubbodoc.utils;

import java.lang.reflect.Type;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: SimpleTypeImpl
 * @Description: Simple implementation of {@link java.lang.reflect.Type}
 * @date 2020/7/31 15:53
 */
public class SimpleTypeImpl implements Type {

    private String typeName;

    public SimpleTypeImpl(String typeName){
        this.typeName = typeName;
    }


    public String getTypeName(){
        return typeName;
    }

}
