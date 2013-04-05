 <textarea <#rt/>
    <#if parameters??>
        <#if parameters.property??>
            name = "${parameters.property?html}"<#rt/>
        </#if>
        <#if parameters.name??>
            id = "${parameters.name?html}"<#rt/>
        </#if>
         <#if parameters.rows??>
            rows = "${parameters.rows?html}"<#rt/>
        </#if>
        <#if parameters.styleClass??>
            class = "${parameters.styleClass?html}"<#rt/>
        </#if>
        <#if parameters.style??>
            style = "${parameters.style?html}"<#rt/>
        </#if>
        <#if parameters.tabindex??>
            tabindex = "${parameters.tabindex?html}"<#rt/>
        </#if>
    </#if>
>
      <#if parameters??>
        <#if parameters.value??>
             ${parameters.value?html} <#rt/>
        </#if>
    </#if>
</textarea>

