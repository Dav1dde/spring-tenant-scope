package de.dav1d.play.ts.property;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.web.context.support.StandardServletEnvironment;


public class MultiTryPropertyServletEnvironment extends StandardServletEnvironment implements MultiTryPropertyResolver
{
    private final MultiTryPropertySourcesPropertyResolver propertyResolver =
        new MultiTryPropertySourcesPropertyResolver(getPropertySources());

    @Override
    public void setPropertyTransformer(PropertyTransformer propertyTransformer)
    {
        propertyResolver.setPropertyTransformer(propertyTransformer);
    }

    @Override
    public ConfigurableConversionService getConversionService() {
        return this.propertyResolver.getConversionService();
    }

    @Override
    public void setConversionService(ConfigurableConversionService conversionService) {
        propertyResolver.setConversionService(conversionService);
    }

    @Override
    public void setPlaceholderPrefix(String placeholderPrefix) {
        propertyResolver.setPlaceholderPrefix(placeholderPrefix);
    }

    @Override
    public void setPlaceholderSuffix(String placeholderSuffix) {
        propertyResolver.setPlaceholderSuffix(placeholderSuffix);
    }

    @Override
    public void setValueSeparator(String valueSeparator) {
        propertyResolver.setValueSeparator(valueSeparator);
    }

    @Override
    public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
        propertyResolver.setIgnoreUnresolvableNestedPlaceholders(ignoreUnresolvableNestedPlaceholders);
    }

    @Override
    public void setRequiredProperties(String... requiredProperties) {
        propertyResolver.setRequiredProperties(requiredProperties);
    }

    @Override
    public void validateRequiredProperties() throws MissingRequiredPropertiesException
    {
        propertyResolver.validateRequiredProperties();
    }

    @Override
    public boolean containsProperty(String key)
    {
        return propertyResolver.containsProperty(key);
    }

    @Override
    public String getProperty(String key)
    {
        return propertyResolver.getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue)
    {
        return propertyResolver.getProperty(key, defaultValue);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType)
    {
        return propertyResolver.getProperty(key, targetType);
    }

    @Override
    public <T> T getProperty(String key, Class<T> targetType, T defaultValue)
    {
        return propertyResolver.getProperty(key, targetType, defaultValue);
    }

    @Override
    public <T> Class<T> getPropertyAsClass(String key, Class<T> targetType)
    {
        return propertyResolver.getPropertyAsClass(key, targetType);
    }

    @Override
    public String getRequiredProperty(String key) throws IllegalStateException
    {
        return propertyResolver.getRequiredProperty(key);
    }

    @Override
    public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException
    {
        return propertyResolver.getRequiredProperty(key, targetType);
    }

    @Override
    public String resolvePlaceholders(String text)
    {
        return propertyResolver.resolvePlaceholders(text);
    }

    @Override
    public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException
    {
        return propertyResolver.resolveRequiredPlaceholders(text);
    }
}
