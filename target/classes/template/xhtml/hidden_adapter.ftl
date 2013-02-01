<input type="hidden" <#rt/>
    <#if parameters??>
        <#if parameters.property??>
            name="${parameters.property?html}"<#rt/>
        </#if>
        <#if parameters.name?? >
            id="${parameters.name?html}"<#rt/>
       </#if>
        <#if parameters.value??>
             value = "${parameters.value?html}"<#rt/>
          <#else>
             value=""<#rt/>
        </#if>
    </#if>
/>