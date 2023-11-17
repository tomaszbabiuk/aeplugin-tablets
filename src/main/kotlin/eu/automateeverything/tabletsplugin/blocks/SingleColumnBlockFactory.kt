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
import eu.automateeverything.tabletsplugin.R
import eu.automateeverything.tabletsplugin.composition.UIBlock
import eu.automateeverything.tabletsplugin.composition.UIBlockFactory
import eu.automateeverything.tabletsplugin.composition.UIContext

class SingleColumnBlockFactory : UIBlockFactory {

    override val category = TabletsBlockCategories.Layouts

    override val type: String = "tablet_single_column"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
            {
              "type": "$type",
              "message0": "${R.block_single_column.getValue(language)}",
              "args0": [
                {
                  "type": "input_dummy"
                },
                {
                  "type": "input_statement",
                  "name": "CONTENT",
                  "check": [
                    "tablet_text",
                    "tablet_headline"
                  ]
                }
              ],   
              "previousStatement": "single",
              "colour": 230,
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
    ): UIBlock {
        val content =
            block.statements?.find { it.name == "CONTENT" }
                ?: throw MalformedBlockException(type, "CONTENT statements missing")

        var iBlock = content.block
        while (iBlock != null) {
            transformer.transformStatement(iBlock, context)
            iBlock = iBlock.next?.block
        }

        return UIBlock()
    }
}
