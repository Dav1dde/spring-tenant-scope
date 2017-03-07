package de.dav1d.play.ts.tenant;

public class InvalidTenantException extends Exception
{
    private final Object tenant;

    public InvalidTenantException(String message, Object tenant)
    {
        super(message);
        this.tenant = tenant;
    }

    public Object getTenant()
    {
        return tenant;
    }
}
