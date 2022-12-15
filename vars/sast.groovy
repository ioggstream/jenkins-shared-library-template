def call(Map config = [:], Closure steps) {
  

  stages {
    stage("SAST") {
      parallel {
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
              ls -lR /home/jenkins || true
              /action/lib/linter.sh
              """
          }
        }

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
      } // parallel^    
    }
  }
}
