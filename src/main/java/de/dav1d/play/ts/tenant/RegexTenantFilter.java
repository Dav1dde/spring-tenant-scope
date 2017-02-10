package de.dav1d.play.ts.tenant;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTenantFilter extends AbstractTenantFilter
{
    protected final Pattern pattern;
    protected final String contextPathFormatString;

    public RegexTenantFilter(
        TenantSetter tenantSetter, Pattern pattern, String contextPathFormatString
    )
    {
        super(tenantSetter);

        this.pattern = pattern;
        this.contextPathFormatString = contextPathFormatString;
    }

    @Override
    protected TenantServletPath getTenantServletPath(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Matcher matcher = pattern.matcher(httpServletRequest.getServletPath());

        if (!matcher.matches())
        {
            ((HttpServletResponse)response).sendError(
                HttpServletResponse.SC_BAD_REQUEST, "Could not extract tenant"
            );
            return null;
        }

        return new TenantServletPath()
            .setTenant(matcher.group("tenant"))
            .setContextPath(String.format(contextPathFormatString, matcher.group("tenant")))
            .setServletPath(matcher.group("path")
            );
    }
}