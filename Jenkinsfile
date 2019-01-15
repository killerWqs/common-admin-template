pipeline {
    agent {
        docker {
            image 'jhouzard/docker-jdk8-mvn3'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Package') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'java -jar demo-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}