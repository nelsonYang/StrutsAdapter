<input type="checkbox" <#rt/>
    <#if parameters??>
        <#if parameters.property??>
            name = "${parameters.property?html}"<#rt/>
        </#if>
         <#if parameters.name??>
            id = "${parameters.name?html}"<#rt/>
        </#if>
        <#if parameters.value??>
            name = "${parameters.value?html}"<#rt/>
        </#if>
        <#if parameters.styleClass??>
            class = "${parameters.styleClass?html}"<#rt/>
        </#if>
        <#if parameters.style??>
            style = "${parameters.style?html}"<#rt/>
        </#if>
        <#if parameters.onmouseout??>
            onmouseout = "${parameters.onmouseout?html}"<#rt/>
        </#if>
        <#if parameters.onchange??>
            onchange = "${parameters.onchange?html}"<#rt/>
        </#if>
        <#if parameters.onclick??>
            onclick = "${parameters.onclick?html}"<#rt/>
        </#if>
        <#if parameters.tabindex??>
            tabindex = "${parameters.tabindex?html}"<#rt/>
        </#if>
    </#if>
/>
