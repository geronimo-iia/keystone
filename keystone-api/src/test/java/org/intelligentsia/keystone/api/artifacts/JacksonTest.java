package org.intelligentsia.keystone.api.artifacts;

import java.io.IOException;

import junit.framework.TestCase;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Jackson basic Test.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class JacksonTest extends TestCase {

	private ObjectMapper mapper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mapper = new ObjectMapper();
	}

	/**
	 * Basic test to verify Jackson serialization
	 */
	public void testHelloString() throws IOException {
		final String jsonResult = mapper.writeValueAsString("hello");
		assertEquals(jsonResult, "\"hello\"");
	}

}
