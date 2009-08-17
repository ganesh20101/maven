package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.Properties;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-4305">MNG-4305</a>.
 * 
 * @author Benjamin Bentmann
 */
public class MavenITmng4305LocalRepoBasedirTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng4305LocalRepoBasedirTest()
    {
        super( ALL_MAVEN_VERSIONS );
    }

    /**
     * Verify that ${localRepository.basedir} delivers a proper filesystem path. In detail, the path should use the
     * platform-specific file separator and have no trailing separator.
     */
    public void testit()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-4305" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        Properties props = verifier.loadProperties( "target/basedir.properties" );

        // NOTE: This deliberately compares the paths on the String level, not via File.equals()
        assertEquals( new File( verifier.localRepo ).getPath(), props.getProperty( "localRepository.basedir" ) );
    }

}
