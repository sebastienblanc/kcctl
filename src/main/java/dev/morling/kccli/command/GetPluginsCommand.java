/*
 *  Copyright 2021 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.morling.kccli.command;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

import dev.morling.kccli.service.ConnectorPlugin;
import dev.morling.kccli.service.KafkaConnectApi;
import picocli.CommandLine.Command;

@Command(name = "plugins")
public class GetPluginsCommand implements Runnable {

    @Override
    public void run() {
        KafkaConnectApi simpleGetApi = RestClientBuilder.newBuilder()
                .baseUri(URI.create("http://localhost:8083"))
                .build(KafkaConnectApi.class);

        List<ConnectorPlugin> connectorPlugins = simpleGetApi.getConnectorPlugins();

        System.out.println();
        System.out.println(AsciiTable.getTable(AsciiTable.NO_BORDERS, connectorPlugins, Arrays.asList(
                new Column().header("TYPE").dataAlign(HorizontalAlign.LEFT).with(plugin -> plugin.type),
                new Column().header(" CLASS").dataAlign(HorizontalAlign.LEFT).with(plugin -> " " + plugin.clazz),
                new Column().header(" VERSION").dataAlign(HorizontalAlign.LEFT).with(plugin -> " " + plugin.version))));
        System.out.println();
    }
}
