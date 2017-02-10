package de.dav1d.play.ts.tenant;

public class TenantServletPath
{
    private String tenant;
    private String contextPath;
    private String servletPath;

    public TenantServletPath()
    {
    }

    public TenantServletPath(String tenant, String contextPath, String servletPath)
    {
        this.tenant = tenant;
        this.contextPath = contextPath;
        this.servletPath = servletPath;
    }

    public TenantServletPath setTenant(String tenant)
    {
        this.tenant = tenant;
        return this;
    }

    public TenantServletPath setContextPath(String contextPath)
    {
        this.contextPath = contextPath;
        return this;
    }

    public TenantServletPath setServletPath(String servletPath)
    {
        this.servletPath = servletPath;
        return this;
    }

    public String getTenant()
    {
        return tenant;
    }

    public String getContextPath()
    {
        return contextPath;
    }

    public String getServletPath()
    {
        return servletPath;
    }
}
