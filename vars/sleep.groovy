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
                        sleep 1000
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