<select <#rt/>
<#if parameters??>
    <#if parameters.name??>
        name ="${parameters.name?html}"<#rt/>
    </#if>
    <#if parameters.onmouseover??>
        onmouseover ="${parameters.onmouseover?html}"<#rt/>
    </#if>
    <#if parameters.onmouseout??>
        onmouseout ="${parameters.onmouseout?html}"<#rt/>
    </#if>
    <#if parameters.onchange??>
        onchange ="${parameters.onchange?html}"<#rt/>
    </#if>
</#if>
    >