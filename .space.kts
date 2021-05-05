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
                cp target/*.jar $mountDir/share
            """
        }
    }

    docker {
        beforeBuildScript {
            content = """
                export BRANCH=${'$'}(echo ${'$'}JB_SPACE_GIT_BRANCH | cut -d'/' -f 3)
            	mkdir target
            	cp $mountDir/share/*.jar target/
            """
        }
        build {
            context = "."
            file = "./Dockerfile"
            labels["vendor"] = "Acentice"
        }

        push("acentice.registry.jetbrains.space/p/ideale/acentice/idealeapi") {
            tag = "version-\$BRANCH"
        }
    }
}

job("Deploy to k8s dev cluster") {
    startOn {
        gitPush {
            branchFilter {
                // add 'main'
                +"refs/heads/develop"

                -"refs/heads/IDEALE-*"
            }
        }
    }

    container(displayName = "Deploy dev cluster", image = "acentice.registry.jetbrains.space/p/ideale/acentice/kubectl:latest") {
        env["K8S_CONFIG"] = Secrets("k8s-config")
        shellScript {
            content = """
                ls -al /.kube/
	            echo is  ${'$'}K8S_CONFIG
                 echo "${'$'}K8S_CONFIG" >> /.kube/config
                 kubectl apply -f k8s/
            """
        }
    }
}
