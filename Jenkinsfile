pipeline {
    agent any

    environment {
        // Define any environment variables needed for the pipeline
        // JAVA_HOME = tool name: 'openjdk-21', type: 'jdk'
        // PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = "spring-boot-app:latest"
    }

    stages {
        // stage('Checkout') {
        //     steps {
        //         // Checkout the source code from the repository
        //         git 'https://github.com/your-username/spring-boot-java21.git'
        //     }
        // }

        stage('Build') {
            steps {
                echo "Build Stage!"
                // Build the application using Maven
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                echo "Test Stage!"
                // Run tests using Maven Wrapper
                sh './mvnw test'
            }
            post {
                always {
                    // Archive the test results to display them in Jenkins
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                // Run SonarQube analysis (assumes SonarQube is installed and configured in Jenkins)
                withSonarQubeEnv('sonarqube') {
                    sh './mvnw sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(env.DOCKER_IMAGE)
                }
            }
        }

        stage('Deploy to Staging') {
            steps {
                script {
                    sh '''
                        docker-compose down
                        docker-compose up -d --build
                    '''
                }
            }
        }

        // stage('Release to Production') {
        //     steps {
        //         // Deploy the application to the production environment
        //         script {
        //             // Stop and remove the existing container if it exists
        //             sh '''
        //                 if [ "$(docker ps -q -f name=spring-boot-java21)" ]; then
        //                     docker stop spring-boot-java21
        //                     docker rm spring-boot-java21
        //                 fi
        //             '''
        //             // Run the new container
        //             sh 'docker run -d -p 8080:8080 --name spring-boot-java21 ${DOCKER_IMAGE}'
        //         }
        //     }
        // }

        // stage('Post-deployment Monitoring') {
        //     steps {
        //         // Configure monitoring (assuming you have monitoring tools configured, e.g., New Relic, Datadog)
        //         echo 'Monitoring and alerting setup steps go here'
        //     }
        // }
    }

    post {
        always {
            // Clean up actions that should always run, e.g., archiving artifacts, cleaning up Docker containers
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            // cleanWs()
        }
        success {
            // Notify on success
            echo 'Pipeline completed successfully!'
        }
        failure {
            // Notify on failure
            echo 'Pipeline failed!'
        }
    }
}
