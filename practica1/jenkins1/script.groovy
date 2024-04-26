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
    }
}
