package com.blccorestudio.rootpreflight.scanner

object ConflictDetector {
    fun detect(report: ModuleReport, installedModules: List<InstalledModule>): List<Conflict> =
        installedModules.mapNotNull { installed ->
            val fileConflicts = report.overlayTargets.intersect(installed.overlayTargets)
            val propertyConflicts = report.propertyKeys.intersect(installed.propertyKeys)
            if (fileConflicts.isEmpty() && propertyConflicts.isEmpty()) {
                null
            } else {
                Conflict(
                    installedModuleId = installed.id,
                    installedModuleName = installed.name,
                    overlappingFiles = fileConflicts,
                    overlappingProperties = propertyConflicts,
                )
            }
        }
}
