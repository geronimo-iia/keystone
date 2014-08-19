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
package org.intelligentsia.keystone;

import java.util.Arrays;

import org.apache.maven.artifact.Artifact;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test on {@link BootStrapMojo}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BootStrapMojoTest {
    
    @Test
    public void checkIncludedScopetWithDefaultPattern() {
        BootStrapMojo bootStrapMojo = new BootStrapMojo();        
        for(String scope: Arrays.asList(Artifact.SCOPE_COMPILE,  Artifact.SCOPE_RUNTIME, Artifact.SCOPE_PROVIDED, Artifact.SCOPE_SYSTEM)) {
            Assert.assertTrue(bootStrapMojo.includedScope(scope));
        }
        Assert.assertFalse(bootStrapMojo.includedScope(Artifact.SCOPE_TEST));
    } 

    @Test
    public void checkIncludedScopeWithNull() {
        BootStrapMojo bootStrapMojo = new BootStrapMojo();       
        Assert.assertTrue(bootStrapMojo.includedScope(null));
    }
}
