/*
 * Copyright © 2015 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.tephra.util;

import com.google.inject.Provider;
import org.apache.hadoop.conf.Configuration;

/**
 * Provides {@code org.apache.hadoop.conf.Configuration} instances, constructed by the correct version used
 * for the runtime.
 */
public class ConfigurationFactory implements Provider<Configuration> {
  private static class ConfigurationProviderFactory extends HBaseVersionSpecificFactory<ConfigurationProvider> {
    @Override
    protected String getHBase96Classname() {
      return "co.cask.tephra.hbase96.HBase96ConfigurationProvider";
    }

    @Override
    protected String getHBase98Classname() {
      return "co.cask.tephra.hbase98.HBase98ConfigurationProvider";
    }

    @Override
    protected String getHBase10Classname() {
      return "co.cask.tephra.hbase10.HBase10ConfigurationProvider";
    }

    @Override
    protected String getHBase10CDHClassname() {
      return "co.cask.tephra.hbase10cdh.HBase10ConfigurationProvider";
    }
  }

  private final ConfigurationProvider provider = new ConfigurationProviderFactory().get();

  /**
   * Returns a new {@link org.apache.hadoop.conf.Configuration} instance from the HBase version-specific factory.
   */
  @Override
  public Configuration get() {
    return provider.get();
  }

  /**
   * Returns a new {@link org.apache.hadoop.conf.Configuration} instance from the HBase version-specific factory.
   *
   * @param baseConf additional configuration properties to merge on to the classpath configuration
   * @return the merged configuration
   */
  public Configuration get(Configuration baseConf) {
    return provider.get(baseConf);
  }
}
