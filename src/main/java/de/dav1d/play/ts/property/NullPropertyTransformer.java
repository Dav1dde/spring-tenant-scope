package de.dav1d.play.ts.property;

public class NullPropertyTransformer implements PropertyTransformer
{
    @Override
    public String[] transform(String key)
    {
        return new String[]{key};
    }
}
