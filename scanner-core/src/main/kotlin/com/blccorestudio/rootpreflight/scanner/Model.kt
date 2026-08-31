package com.blccorestudio.rootpreflight.scanner

enum class Severity { INFO, LOW, MEDIUM, HIGH }

enum class RiskLevel { LOW, MEDIUM, HIGH }

data class ModuleMetadata(
    val id: String? = null,
    val name: String? = null,
    val version: String? = null,
    val versionCode: String? = null,
    val author: String? = null,
    val description: String? = null,
)

data class Finding(
    val code: String,
    val severity: Severity,
    val title: String,
    val detail: String,
    val source: String? = null,
    val weight: Int = 0,
)

data class ModuleReport(
    val sourceName: String,
    val metadata: ModuleMetadata,
    val riskScore: Int,
    val riskLevel: RiskLevel,
    val findings: List<Finding>,
    val entriesScanned: Int,
    val scripts: List<String>,
    val overlayTargets: Set<String>,
    val propertyKeys: Set<String>,
    val nativeFiles: List<String>,
) {
    val moduleDisplayName: String
        get() = metadata.name ?: metadata.id ?: sourceName
}

data class InstalledModule(
    val id: String,
    val name: String? = null,
    val overlayTargets: Set<String> = emptySet(),
    val propertyKeys: Set<String> = emptySet(),
)

data class Conflict(
    val installedModuleId: String,
    val installedModuleName: String?,
    val overlappingFiles: Set<String>,
    val overlappingProperties: Set<String>,
)
