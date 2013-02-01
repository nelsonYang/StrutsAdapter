<form <#rt/>
<#if parameters??>
    <#if parameters.name??>
        name ="${parameters.name?html}"<#rt/>
    </#if>
    <#if parameters.method??>
        method ="${parameters.method?html}"<#rt/>
    </#if>
    <#if parameters.action??>
        action ="${parameters.action?html}"<#rt/>
    </#if>
    <#if parameters.enctype??>
        enctype ="${parameters.enctype?html}"<#rt/>
    </#if>
</#if>
    >