package com.struts.adpater.form;

import com.struts.adapter.entity.FormBeanInfo;
import java.util.Map;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Administrator
 */
public interface FormHandler {

    public ActionForm handleForm(Map<String, Object> paramterMap, FormBeanInfo formBeanInfo)  throws Exception;
}
