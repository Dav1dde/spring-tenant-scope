package de.dav1d.play.ts.property;

public class NullPropertyTransformer implements PropertyTransformer
{
    @Override
    public String[] transform(String name)
    {
        return new String[]{name};
    }

    @Override
    public boolean isTransformed(String name)
    {
        return false;
    }
}
