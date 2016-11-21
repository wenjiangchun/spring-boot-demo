package com.haze.springboot.freemarker;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ConfigurationProperties} for configuring FreeMarker.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = "spring.freemarker")
public class FreeMarkerProperties extends AbstractTemplateViewResolverProperties {

    public static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";

    public static final String DEFAULT_PREFIX = "";

    public static final String DEFAULT_SUFFIX = ".ftl";

    /**
     * Well-known FreeMarker keys which will be passed to FreeMarker's Configuration.
     */
    private Map<String, String> settings = new HashMap<String, String>();

    /**
     * Comma-separated list of template paths.
     */
    private String[] templateLoaderPath = new String[] { DEFAULT_TEMPLATE_LOADER_PATH };

    /**
     * Prefer file system access for template loading. File system access enables hot
     * detection of template changes.
     */
    private boolean preferFileSystemAccess = true;

    public FreeMarkerProperties() {
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public Map<String, String> getSettings() {
        return this.settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public String[] getTemplateLoaderPath() {
        return this.templateLoaderPath;
    }

    public boolean isPreferFileSystemAccess() {
        return this.preferFileSystemAccess;
    }

    public void setPreferFileSystemAccess(boolean preferFileSystemAccess) {
        this.preferFileSystemAccess = preferFileSystemAccess;
    }

    public void setTemplateLoaderPath(String... templateLoaderPaths) {
        this.templateLoaderPath = templateLoaderPaths;
    }

}
