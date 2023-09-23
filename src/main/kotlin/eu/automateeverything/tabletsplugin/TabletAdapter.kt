/*
 * Copyright (c) 2019-2022 Tomasz Babiuk
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.automateeverything.tabletsplugin


import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.DiscoveryMode
import eu.automateeverything.domain.hardware.HardwareAdapterBase
import eu.automateeverything.domain.hardware.Port
import eu.automateeverything.domain.hardware.PortIdBuilder
import eu.automateeverything.domain.langateway.LanGatewayResolver
import kotlinx.coroutines.*
import kotlinx.serialization.cbor.Cbor
import java.net.InetAddress

class TabletAdapter(
    owningPluginId: String,
    private val lanGatewayResolver: LanGatewayResolver,
    eventBus: EventBus) : HardwareAdapterBase<Port<*>>(owningPluginId, "0", eventBus) {
    private val coapDiscovery = CoAPDiscovery(Cbor, lanGatewayResolver) {
        eventBus.broadcastDiscoveryEvent(owningPluginId, it)
    }

    private val idBuilder = PortIdBuilder(owningPluginId)

    override suspend fun internalDiscovery(mode: DiscoveryMode) = coroutineScope {
        val forced = coapDiscovery.discoverByForceScanning()
        println(forced)
    }



    override fun executePendingChanges() {
    }

    override fun stop() {
    }

    override fun start() {
    }
}
