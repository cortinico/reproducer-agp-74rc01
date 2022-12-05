package com.ncorti.kotlin.gradle.template.plugin

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

const val EXTENSION_NAME = "templateExampleConfig"
const val TASK_NAME = "templateExample"

abstract class TemplatePlugin : Plugin<Project> {
    @Suppress("UnstableApiUsage")
    override fun apply(project: Project) {
        // Add the 'template' extension object
        val extension = project.extensions.create(EXTENSION_NAME, TemplateExtension::class.java, project)

        // Add a task that uses configuration from the extension object
        project.tasks.register(TASK_NAME, TemplateExampleTask::class.java) {
            it.tag.set(extension.tag)
            it.message.set(extension.message)
            it.outputFile.set(extension.outputFile)
        }

        project.pluginManager.withPlugin("com.android.application") {
            project.extensions.getByType(AndroidComponentsExtension::class.java).apply {
                onVariants(selector().all()) { variant ->
                    val bundleTask = project.tasks.register("reproducer${variant.name}Task", ReproducerTask::class.java)
                    variant.sources.res?.addGeneratedSourceDirectory(bundleTask, ReproducerTask::outputDir)
                }
            }
        }
    }
}
