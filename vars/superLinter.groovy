def call(Map config = [:]) {
    
    def pipelineParams = [:]

    pipeline {
        agent any

        stages {
            stage("Super-Linter") {
                agent { 
                    kubernetes {
                        cloud config.cloud
                        yamlFile config.yamlFile
                        defaultContainer 'super-linter'
                    }
                }
                steps {
                    sh "env | sort"
                    sh """
                        pwd
                        /action/lib/linter.sh
                        """
                }
            }
        }

        post {
            always {
                echo 'Super-linter. check the results.'
            }
        }
    }
}