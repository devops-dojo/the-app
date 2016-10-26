package io.github.zutherb.appstash.shop.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Input
import org.gradle.api.java.archives.Manifest

class VersionTxtTask extends DefaultTask {

    @Input
    Manifest manifest

    @Input
    String filename

    @TaskAction
    def create() {
        manifest.writeTo(filename)
    }
}