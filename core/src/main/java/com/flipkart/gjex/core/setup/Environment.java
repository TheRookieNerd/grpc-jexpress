/*
 * Copyright 2012-2016, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flipkart.gjex.core.setup;

import com.codahale.metrics.MetricRegistry;

/**
 * Represents a GJEX application environment 
 * 
 * @author regu.b
 *
 */
public class Environment {

	private final String name;
    private final MetricRegistry metricRegistry;
    
    public Environment(String name, MetricRegistry metricRegistry) {
    		this.name = name;
    		this.metricRegistry = metricRegistry;
    }

	public String getName() {
		return name;
	}

	public MetricRegistry getMetricRegistry() {
		return metricRegistry;
	}
        
}
