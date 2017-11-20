// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.model.container.xml;

import com.yahoo.config.application.api.ApplicationPackage;
import com.yahoo.config.model.builder.xml.test.DomBuilderTest;
import com.yahoo.config.model.deploy.DeployState;
import com.yahoo.config.model.test.MockApplicationPackage;
import com.yahoo.config.model.test.MockRoot;
import com.yahoo.container.core.identity.IdentityConfig;
import com.yahoo.vespa.model.container.Identity;
import org.junit.Test;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author mortent
 */
public class IdentityBuilderTest extends ContainerModelBuilderTestBase {
    @Test
    public void identity_config_produced() throws IOException, SAXException {
        Element clusterElem = DomBuilderTest.parse(
                "<jdisc id='default' version='1.0'/>");
        String deploymentXml = "<deployment version='1.0' athenz-domain='domain' athenz-service='service'>\n" +
                               "    <test/>\n" +
                               "    <prod>\n" +
                               "        <region active='true'>default</region>\n" +
                               "    </prod>\n" +
                               "</deployment>\n";

        ApplicationPackage applicationPackage = new MockApplicationPackage.Builder()
                .withDeploymentSpec(deploymentXml)
                .build();


        // Override root
        root = new MockRoot("root", applicationPackage);
        createModel(root, DeployState.createTestState(applicationPackage), clusterElem);

        IdentityConfig identityConfig = root.getConfig(IdentityConfig.class, "default/component/" + Identity.CLASS);
        assertEquals("domain", identityConfig.domain());
        assertEquals("service", identityConfig.service());
    }

    @Test
    public void athenz_service_override() throws IOException, SAXException {
        Element clusterElem = DomBuilderTest.parse(
                "<jdisc id='default' version='1.0'/>");
        String deploymentXml = "<deployment version='1.0' athenz-domain='domain' athenz-service='service'>\n" +
                               "    <test/>\n" +
                               "    <prod athenz-service='prod-service'>\n" +
                               "        <region active='true'>default</region>\n" +
                               "    </prod>\n" +
                               "</deployment>\n";

        ApplicationPackage applicationPackage = new MockApplicationPackage.Builder()
                .withDeploymentSpec(deploymentXml)
                .build();


        // Override root
        root = new MockRoot("root", applicationPackage);
        createModel(root, DeployState.createTestState(applicationPackage), clusterElem);

        IdentityConfig identityConfig = root.getConfig(IdentityConfig.class, "default/component/" + Identity.CLASS);
        assertEquals("domain", identityConfig.domain());
        assertEquals("prod-service", identityConfig.service());
    }
}
