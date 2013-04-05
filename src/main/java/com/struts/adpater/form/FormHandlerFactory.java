package com.struts.adpater.form;

import com.struts.adapter.constant.FormTypeEnum;
import java.util.EnumMap;

/**
 *
 * @author Administrator
 */
public class FormHandlerFactory {

    public static final EnumMap<FormTypeEnum,FormHandler> formHandlerMap = new EnumMap<FormTypeEnum,FormHandler>(FormTypeEnum.class);

    static {
        formHandlerMap.put(FormTypeEnum.DYNAMIC_FORM, new DynamicFormHandler());
        formHandlerMap.put(FormTypeEnum.STATIC_FORM, new StaticFormHandler());
    }
    
    public static FormHandler getFormHandler(FormTypeEnum formType){
    
        return formHandlerMap.get(formType);
    }
}
