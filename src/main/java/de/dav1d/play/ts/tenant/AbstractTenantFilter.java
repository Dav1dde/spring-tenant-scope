package de.dav1d.play.ts.tenant;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class AbstractTenantFilter extends GenericFilterBean
{
    protected final TenantHolder tenantHolder;

    public AbstractTenantFilter(TenantHolder tenantHolder)
    {
        this.tenantHolder = tenantHolder;
    }

    protected abstract TenantServletPath getTenantServletPath(ServletRequest request, ServletResponse response)
        throws ServletException, IOException;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        TenantServletPath tenantServletPath = getTenantServletPath(request, response);
        if (tenantServletPath == null)
        {
            return;
        }

        try
        {
            tenantHolder.setTenant(tenantServletPath.getTenant());
        }
        catch (InvalidTenantException e)
        {
            ((HttpServletResponse) response).sendError(400, e.getMessage());
            return;
        }
        chain.doFilter(new TenantRequest((HttpServletRequest) request, tenantServletPath), response);
    }

    protected static class TenantRequest extends HttpServletRequestWrapper
    {
        private final String servletPath;
        private final String contextPath;

        public TenantRequest(HttpServletRequest request, TenantServletPath tenantServletPath)
        {
            super(request);

            this.servletPath = tenantServletPath.getServletPath();
            this.contextPath = tenantServletPath.getContextPath();
        }

        @Override
        public String getServletPath()
        {
            return servletPath;
        }

        @Override
        public String getContextPath()
        {
            return super.getContextPath() + contextPath;
        }
    }
}
