/**
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
package org.examples;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

public class PropertiesGeneratorFunction implements Function<String, String> {
    @Override
    public String process(String s, Context context) throws Exception {
        try {
            String generatedString = RandomStringUtils.randomAlphabetic(10);
            context.newOutputMessage(context.getOutputTopic(), Schema.STRING).value(s + "-properties").property("content", s).property("random", generatedString).sendAsync();
        }catch (PulsarClientException e) {
            context.getLogger().error("newOutputMessage failed", e);
        }
        return null;
    }
}
