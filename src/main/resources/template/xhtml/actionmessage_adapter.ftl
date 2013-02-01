<link rel="stylesheet" type="text/css" href="${base}/struts/xhtml/style.css" />
<#if (actionMessageItem?? &&  parameters?size > 0)>
	<ul<#rt/>
<#if parameters.id?if_exists != "">
 id="${parameters.id?html}"<#rt/>
</#if>
<#if parameters.cssClass??>
 class="${parameters.cssClass?html}"<#rt/>
<#else>
 class="actionMessage"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle?html}"<#rt/>
</#if>
>		
            <#if actionMessageItem?if_exists != "">
                <li><span>${actionMessageItem}</span></li>
            </#if>
    </ul>
</#if>