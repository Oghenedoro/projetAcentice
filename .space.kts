/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, see https://www.jetbrains.com/help/space/automation.html
*/

job("Build and push Docker") {

    container(displayName = "Run mvn install", image = "maven:latest") {
        shellScript {
            content = """
	            mvn clean install
                cp target/*.jar mnt/space/share
            """
        }
    }

    docker {
        beforeBuildScript {
            content = """
                export BRANCH=${'$'}(echo ${'$'}JB_SPACE_GIT_BRANCH | cut -d'/' -f 3)
            	mkdir target
            	cp mnt/space/share/*.jar target/
            """
        }
        build {
            context = "."
            file = "./Dockerfile"
            labels["vendor"] = "Acentice"
        }

        push("acentice.registry.jetbrains.space/ideale/idealeapi") {
            tag = "version-0.\$JB_SPACE_EXECUTION_NUMBER-\$BRANCH"
        }
    }
}
