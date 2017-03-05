package de.dav1d.play.ts.property;

public interface PropertyTransformer
{
    String[] transform(String name);

    boolean isTransformed(String name);
}
