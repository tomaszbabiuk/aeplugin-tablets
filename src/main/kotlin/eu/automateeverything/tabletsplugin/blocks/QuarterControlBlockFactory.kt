/*
 * Copyright (c) 2019-2023 Tomasz Babiuk
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

package eu.automateeverything.tabletsplugin.blocks

import eu.automateeverything.data.blocks.RawJson
import eu.automateeverything.domain.automation.*
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import eu.automateeverything.tabletsplugin.interop.QuarterControl
import eu.automateeverything.tabletsplugin.interop.UINode

class QuarterControlBlockFactory : UIBlockFactory {

    override val category = TabletsBlockCategories.Layouts

    override val type: String = "tablet_quarter_control"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
            {
              "type": "$type",
              "message0": "4-devices grid %1 %2 1 %3 2 %4 %5 3 %6 4 %7",
              "args0": [
                {
                  "type": "input_dummy"
                },
                {
                  "type": "input_end_row"
                },
                {
                  "type": "input_value",
                  "name": "DEVICE1",
                  "check": "tablet_device"
                },
                {
                  "type": "input_value",
                  "name": "DEVICE2",
                  "check": "tablet_device"
                },
                {
                  "type": "input_end_row"
                },
                {
                  "type": "input_value",
                  "name": "DEVICE3",
                  "check": "tablet_device"
                },
                {
                  "type": "input_value",
                  "name": "DEVICE4",
                  "check": "tablet_device"
                }
              ],
              "inputsInline": true,
              "previousStatement": null,
              "colour": ${category.color},
              "tooltip": "",
              "helpUrl": ""
            }
                """
                .trimIndent()
        }
    }

    override fun transform(
        block: Block,
        next: StatementNode?,
        context: UIContext,
        transformer: TabletsTransformer,
    ): UINode {
        val device1 = readDeviceId(block, "DEVICE1")
        val device2 = readDeviceId(block, "DEVICE2")
        val device3 = readDeviceId(block, "DEVICE3")
        val device4 = readDeviceId(block, "DEVICE4")
        val controlGrid = QuarterControl(device1, device2, device3, device4)
        val dashboardItem = DashboardItem(quarterControl = controlGrid)
        return UINode(dashboardItem)
    }

    private fun readDeviceId(block: Block, valueName: String): Long? {
        val value = block.values!!.find { it.name == valueName }

        if (value != null) {
            val deviceBlockType = value.block!!.type

            val deviceInstanceIdRaw = deviceBlockType.replace(DeviceBlockFactory.TYPE_PREFIX, "")

            return deviceInstanceIdRaw.toLong()
        }

        return null
    }
}
