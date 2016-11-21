package com.haze.springboot.freemarker;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * {@link TemplateAvailabilityProvider} that provides availability information for
 * FreeMarker view templates.
 *
 * @author Andy Wilkinson
 * @since 1.1.0
 */
public class FreeMarkerTemplateAvailabilityProvider
        implements TemplateAvailabilityProvider {

    @Override
    public boolean isTemplateAvailable(String view, Environment environment,
                                       ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (ClassUtils.isPresent("freemarker.template.Configuration", classLoader)) {
            RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment,
                    "spring.freemarker.");
            String loaderPath = resolver.getProperty("template-loader-path",
                    FreeMarkerProperties.DEFAULT_TEMPLATE_LOADER_PATH);
            String prefix = resolver.getProperty("prefix",
                    FreeMarkerProperties.DEFAULT_PREFIX);
            String suffix = resolver.getProperty("suffix",
                    FreeMarkerProperties.DEFAULT_SUFFIX);
            return resourceLoader.getResource(loaderPath + prefix + view + suffix)
                    .exists();
        }
        return false;
    }

}