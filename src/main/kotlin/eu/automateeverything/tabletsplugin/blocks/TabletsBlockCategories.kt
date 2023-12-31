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

import eu.automateeverything.data.localization.Resource
import eu.automateeverything.domain.automation.blocks.BlockCategory
import eu.automateeverything.tabletsplugin.R

enum class TabletsBlockCategories(override val categoryName: Resource, override val color: Int) :
    BlockCategory {
    UI(R.category_ui, 45),
    Actions(R.category_actions, 90),
    StartHere(R.category_start_here, 135),
    Devices(R.category_other_devices, 180),
    Layouts(R.category_layouts, 215)
}
