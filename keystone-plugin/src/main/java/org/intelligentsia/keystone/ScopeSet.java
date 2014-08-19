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

import org.apache.maven.artifact.Artifact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ScopeSet is a set of scope that dependencies must match in order to be included in final archive.
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ScopeSet implements Serializable {

    private static final long serialVersionUID = -7785361226952170104L;
    
    private List<String> scopes;

    /**
     * Build a new ScopeSet.
     */
    public ScopeSet() {
        super();
    }

    /**
     * @param artifact
     * @return true if artifact has a scope included in this scope set.
     */
    public boolean contains(Artifact artifact) {
        return getScopes().contains(artifact.getScope());
    }

    /**
     * Add specified scope.
     *
     * @param scope
     */
    public void addScope(String scope) {
        getScopes().add(scope);
    }

    /**
     * Remove specified scope.
     *
     * @param scope
     */
    public void removeScope(String scope) {
        getScopes().remove(scope);
    }

    /**
     * @return a list of scope to include.
     */
    public List<String> getScopes() {
        if (scopes == null) {
            scopes = new ArrayList<String>();
        }
        return scopes;
    }

    /**
     * Set a list of scope to include.
     *
     * @param scopes
     */
    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
