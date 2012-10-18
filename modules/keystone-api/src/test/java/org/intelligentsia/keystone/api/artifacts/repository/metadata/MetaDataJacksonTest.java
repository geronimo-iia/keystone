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
package org.intelligentsia.keystone.api.artifacts.repository.metadata;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * MetaData and Jackson Test.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MetaDataJacksonTest {
	private static final String JSON_DATA = "{\"groupId\":\"gid\",\"artifactId\":\"aid\",\"version\":\"1.4-SNAPSHOT\",\"versioning\":{\"latest\":\"1.4-SNAPSHOT\",\"release\":null,\"snapshot\":{\"timestamp\":\"20111002.162808\",\"buildNumber\":1,\"localCopy\":false},\"versions\":[],\"lastUpdated\":\"20111008133412\"},\"plugins\":[]}\r\n";
	public String XML_DATA = "<metadata><groupId>org.intelligents-ia</groupId><artifactId>keystone</artifactId><versioning><versions><version>1.1-SNAPSHOT</version><version>1.2-SNAPSHOT</version></versions><lastUpdated>20111008133412</lastUpdated></versioning><version>1.4-SNAPSHOT</version></metadata>";
	private ObjectMapper mapper;

	@Before
	public void setUp() throws Exception {
		mapper = new ObjectMapper();
	}

	@Test
	public void testWrite() throws JsonGenerationException, JsonMappingException, IOException {
		final Metadata metadata = getMetadata();
		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, metadata);
		Assert.assertNotNull(writer.toString());
	}

	@Test
	public void testRead() throws JsonParseException, JsonMappingException, IOException {
		final Metadata metadata = mapper.readValue(JSON_DATA, Metadata.class);
		Assert.assertNotNull(metadata);
		Assert.assertEquals("gid", metadata.getGroupId());
		Assert.assertEquals("aid", metadata.getArtifactId());
		Assert.assertEquals("1.4-SNAPSHOT", metadata.getVersion());
		Assert.assertEquals("20111008133412", metadata.getVersioning().getLastUpdated());
		Assert.assertEquals(0, metadata.getVersioning().getVersions().size());
	}

	@Test
	public void testXmlJacksonWrite() throws JsonGenerationException, JsonMappingException, IOException {
		final ObjectMapper mapper = new XmlMapper();
		final Metadata metadata = getMetadata();
		final StringWriter writer = new StringWriter();
		mapper.writeValue(writer, metadata);
		Assert.assertNotNull(writer.toString());
	}

	@Test
	public void testXmlJacksonRead() throws JsonParseException, JsonMappingException, IOException {
		final ObjectMapper mapper = new XmlMapper();
		final Metadata metadata = mapper.readValue(XML_DATA, Metadata.class);
		Assert.assertNotNull(metadata);
		Assert.assertEquals("org.intelligents-ia", metadata.getGroupId());
		Assert.assertEquals("keystone", metadata.getArtifactId());
		Assert.assertEquals("1.4-SNAPSHOT", metadata.getVersion());
		Assert.assertEquals("20111008133412", metadata.getVersioning().getLastUpdated());
		Assert.assertEquals(2, metadata.getVersioning().getVersions().size());
		Assert.assertEquals("1.1-SNAPSHOT", metadata.getVersioning().getVersions().get(0));
		Assert.assertEquals("1.2-SNAPSHOT", metadata.getVersioning().getVersions().get(1));

		final Metadata metadata2 = getMetadata();
		Assert.assertEquals(metadata2.getGroupId(), metadata.getGroupId());
		Assert.assertEquals(metadata2.getArtifactId(), metadata.getArtifactId());
		Assert.assertEquals(metadata2.getVersion(), metadata.getVersion());
		Assert.assertEquals(metadata2.getVersioning().getLastUpdated(), metadata.getVersioning().getLastUpdated());
		Assert.assertEquals(metadata2.getVersioning().getVersions().size(), metadata.getVersioning().getVersions().size());
		Assert.assertEquals(metadata2.getVersioning().getVersions().get(0), metadata.getVersioning().getVersions().get(0));
		Assert.assertEquals(metadata2.getVersioning().getVersions().get(1), metadata.getVersioning().getVersions().get(1));
	}

	public Metadata getMetadata() {
		final Metadata metadata = new Metadata();
		metadata.setGroupId("org.intelligents-ia");
		metadata.setArtifactId("keystone");
		metadata.setVersion("1.4-SNAPSHOT");
		metadata.getVersioning().setLatest("1.4-SNAPSHOT");
		metadata.getVersioning().setLastUpdated("20111008133412");
		metadata.getVersioning().getVersions().add("1.1-SNAPSHOT");
		metadata.getVersioning().getVersions().add("1.2-SNAPSHOT");
		metadata.getVersioning().setSnapshot(new Snapshot());
		metadata.getVersioning().getSnapshot().setTimestamp("20111002.162808");
		metadata.getVersioning().getSnapshot().setBuildNumber(1);
		return metadata;
	}
}
