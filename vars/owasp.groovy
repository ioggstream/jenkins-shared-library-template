def call(Map config = [:]) {
    
    def pipelineParams = [:]

    pipeline {
        agent any

        stages {
            stage("Owasp") {
                agent { 
                    kubernetes {
                        cloud config.cloud
                        yamlFile config.yamlFile
                        defaultContainer 'owasp'
                    }
                }
                steps {
                    sh "env | sort"
                    sh """
                        pwd
                        ls -l /home/jenkins -R || true
                        /usr/share/dependency-check/bin/dependency-check.sh -s ${config.workdir}
                        """
                }
            }
        }

        post {
            always {
                echo 'OWASP. check the results.'
            }
        }
    }
}