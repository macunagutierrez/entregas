pipeline {
    agent any
    
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            } 
        }
        stage('GIT') {
            steps {
                echo 'Download git repository'
                git 'https://github.com/macunagutierrez/helloworld.git'
            } 
        }
         stage('Comprobaciones') {
            steps {
                echo 'Comprobaciones de descarga de repositorio'
                echo WORKSPACE
                bat 'dir'
            }
        }
        stage('Build') {
            steps {
                echo 'Stage Build no hace nada'
            }
        }
        stage('TEST unitarios') {
            steps {
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE'){
                    bat '''
                        set PYTHONPATH=%WORKSPACE%
                        python --version
                        python -m pytest --junitxml=result-unit.xml test/unit
                    '''
                }
            }
        }
        stage('TEST integracion') {
            steps {
                    bat '''
                        set PYTHONPATH=%WORKSPACE%
                        set FLASK_APP=app/api.py
                        start flask run
                        start java -jar C:\\unir\\programas\\wiremock-standalone-3.5.4.jar --port 9090 --root-dir test\\wiremock
                        
                        
                        python --version
                        python -m pytest --junitxml=result-unit.xml test/rest
                    '''
            }
        }
         stage('Result') {
            steps {
                junit 'result*.xml'
                echo 'FINISH!!!'
            }
        }
    }
}
