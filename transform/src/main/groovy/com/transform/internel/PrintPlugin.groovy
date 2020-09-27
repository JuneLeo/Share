package com.transform.internel

import org.gradle.api.Plugin
import org.gradle.api.Project

class PrintPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        project.extensions.create("abtest", PrintExtension)
        project.configurations.implementation.dependencies.add(
                project.dependencies.create('com.transform.mock:Mock:1.0'))

        project.android.registerTransform(new PrintTransform(project))
    }
}

