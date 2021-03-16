package com.enchainte.sdk

import org.junit.jupiter.api.Test
import java.util.ArrayList
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.test.fail

class DocumentationTest {

    @Test
    @Throws(IOException::class)
    fun testReadMeContainsVersionThatMatches() {
        val readmeFile = File("README.md").absoluteFile
        assertTrue(
            readmeFile.exists(), String.format(
                "Expected README.md file to exist, but it doesn't. (path is %s).",
                readmeFile.absolutePath
            )
        )
        assertTrue(
            readmeFile.isFile, String.format(
                "Expected README.md to be a file, but it doesn't. (path is %s).",
                readmeFile.absolutePath
            )
        )
        BufferedReader(
            InputStreamReader(FileInputStream(readmeFile), StandardCharsets.UTF_8)
        ).use { reader ->
            val expectedMentionsOfVersion = 2
            val mentioningLines: MutableList<String> = ArrayList()

            for (line in reader.lines()) {
                if (line.contains(EnchainteClient.VERSION)) {
                    mentioningLines.add(line)
                }
            }
            val message = java.lang.String.format(
                "Expected %d mentions of the version in the Readme, but found %d:%n",
                expectedMentionsOfVersion,
                mentioningLines.size
            )
            assertSame(expectedMentionsOfVersion, mentioningLines.size, message)
        }
    }

    @Test
    @Throws(IOException::class)
    fun testGradlePropertiesContainsVersionThatMatches() {
        // we want to ensure that the pom's version matches the static version.
        val gradleFile = File("build.gradle.kts").absoluteFile
        assertTrue(
            gradleFile.exists(), String.format(
                "Expected build.gradle.kts file to exist, but it doesn't. (path is %s).",
                gradleFile.absolutePath
            )
        )
        BufferedReader(
            InputStreamReader(FileInputStream(gradleFile), StandardCharsets.UTF_8)
        ).use { reader ->
            for (line in reader.lines()) {
                if (line.contains(EnchainteClient.VERSION)) {
                    return
                }
            }
            fail(
                java.lang.String.format(
                    "Expected the EnchainteClient.VERSION (%s) to match up with the one listed in the "
                            + "build.gradle.kts file. It wasn't found.",
                    EnchainteClient.VERSION
                )
            )
        }
    }

}