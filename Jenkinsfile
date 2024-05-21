pipeline {
    agent any

    environment {
        // Define any environment variables needed for the pipeline
        // JAVA_HOME = tool name: 'openjdk-21', type: 'jdk'
        // PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        DOCKER_IMAGE = "spring-boot-app:latest"
        DATADOG_API_KEY = credentials('70f4230f697ae781329ad8f7de09f384')
        DATADOG_APP_KEY = credentials('8cb22c662e1803696e84b87d7f177a2b1bf36679')
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

        stage('Post-deployment Monitoring') {
            steps {
                script {
                    // Send deployment notification to Datadog
                    sh '''
                        curl -X POST "https://api.datadoghq.com/api/v1/events" \
                        -H "Content-Type: application/json" \
                        -H "DD-API-KEY: ${DATADOG_API_KEY}" \
                        -d '{
                              "title": "Deployment Successful",
                              "text": "The application has been successfully deployed to production.",
                              "alert_type": "success"
                            }'
                    '''
                }
            }
        }
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
