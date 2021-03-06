// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.model.content.cluster;

import com.yahoo.vespa.model.builder.xml.dom.ModelElement;
import com.yahoo.vespa.model.content.ResourceLimits;

/**
 * Builder for resource limits for a content cluster with engine proton.
 *
 * @author geirst
 */
public class DomResourceLimitsBuilder {

    public static ResourceLimits build(ModelElement contentXml) {
        ResourceLimits.Builder builder = new ResourceLimits.Builder();
        ModelElement resourceLimits = contentXml.child("resource-limits");
        if (resourceLimits == null) {
            return builder.build();
        }
        if (resourceLimits.child("disk") != null) {
            builder.setDiskLimit(resourceLimits.childAsDouble("disk"));
        }
        if (resourceLimits.child("memory") != null) {
            builder.setMemoryLimit(resourceLimits.childAsDouble("memory"));
        }
        return builder.build();
    }
}
