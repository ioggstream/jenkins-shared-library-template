def call(Map config = [:]) {
    
    def pipelineParams = [:]

    pipeline {
        agent any
        options {
            ansiColor('xterm')
        }
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
                    echo "${agentLabel}"
                    echo "${pipelineParams.osConfiguration}"
                    echo "${pipelineParams.osConfiguration.OS_VERSION}"
                    echo "${pipelineParams.osConfiguration.DIR_TYPE}" 
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