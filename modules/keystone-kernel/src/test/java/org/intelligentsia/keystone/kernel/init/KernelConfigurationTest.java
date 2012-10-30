/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.keystone.kernel.init;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.intelligentsia.keystone.api.artifacts.repository.Repository;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 
 * KernelConfigurationTest test on {@link KernelConfiguration}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KernelConfigurationTest {

	@Test
	public void checkSerialization() throws JsonGenerationException, JsonMappingException, IOException {
		final ObjectMapper mapper = new XmlMapper();

		final KernelConfiguration configuration = getConfiguration();
		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, configuration);

		final KernelConfiguration configuration2 = mapper.readValue(new StringReader(writer.toString()), KernelConfiguration.class);
		assertNotNull(configuration2);
		assertNotNull(configuration2.getArtifactIdentifier());
		assertEquals("org.intelligents-ia.keystone:keystone-kernel:1.0.0-SNAPSHOT", configuration2.getArtifactIdentifier());
		assertNotNull(configuration2.getRepositories());
		assertTrue(!configuration2.getRepositories().isEmpty());
		assertEquals(configuration.getRepositories().size(), configuration2.getRepositories().size());
		assertEquals(configuration.getRepositories().get(0), configuration2.getRepositories().get(0));
		assertNotNull(configuration2.getLocalRepository());
		assertEquals(new URL("file://home"), configuration2.getLocalRepository());
	}

	public KernelConfiguration getConfiguration() throws MalformedURLException {
		final KernelConfiguration configuration = new KernelConfiguration();
		configuration.setArtifactIdentifier("org.intelligents-ia.keystone:keystone-kernel:1.0.0-SNAPSHOT");
		configuration.add(new Repository("repository-maven-uk", "http://uk.maven.org/maven2"));
		configuration.setLocalRepository(new URL("file://home"));
		return configuration;
	}
}