pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "spring-boot-app:latest"
        DATADOG_API_KEY = credentials('datadog-api-key')
    }

    stages {
        stage('Build') {
            steps {
                echo "Build Stage!"
                // Build the application using Maven
                sh './mvnw clean package'
            }
            post {
                always {
                    echo 'Build stage completed'
                }
                success {
                    echo 'Build stage succeeded'
                }
                failure {
                    echo 'Build stage failed'
                }
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
                    // Archive test results
                    junit 'target/surefire-reports/*.xml'
                    echo 'Test stage completed'
                }
                success {
                    echo 'Test stage succeeded'
                }
                failure {
                    echo 'Test stage failed'
                }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                echo "Code Quality Analysis Stage!"
                // Run SonarQube analysis
                withSonarQubeEnv('sonarqube') {
                    sh './mvnw sonar:sonar'
                }
            }
            post {
                always {
                    echo 'Code Quality Analysis stage completed'
                }
                success {
                    echo 'Code Quality Analysis stage succeeded'
                }
                failure {
                    echo 'Code Quality Analysis stage failed'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Build Docker Image Stage!"
                // Build the Docker image
                script {
                    docker.build(env.DOCKER_IMAGE)
                }
            }
            post {
                always {
                    echo 'Build Docker Image stage completed'
                }
                success {
                    echo 'Build Docker Image stage succeeded'
                }
                failure {
                    echo 'Build Docker Image stage failed'
                }
            }
        }

        stage('Deploy to Staging server') {
            steps {
                echo "Deploy to Staging server Stage!"
                script {
                    // Deploy to staging server
                    sh '''
                        docker-compose down
                        docker-compose up -d --build
                    '''
                }
            }
            post {
                always {
                    echo 'Deploy to Staging server stage completed'
                }
                success {
                    echo 'Deploy to Staging server stage succeeded'
                }
                failure {
                    echo 'Deploy to Staging server stage failed'
                }
            }
        }

        stage('Monitoring') {
            steps {
                script {
                    echo "Monitoring Stage!"
                    // Send deployment notification to Datadog
                    sh '''
                        curl -X POST "https://api.datadoghq.com/api/v1/events" \
                        -H "Content-Type: application/json" \
                        -H "DD-API-KEY: ${DATADOG_API_KEY}" \
                        -d '{
                              "title": "Deployment Successful",
                              "text": "The application has been successfully deployed.",
                              "alert_type": "success"
                            }'
                    '''
                }
            }
            post {
                always {
                    echo 'Monitoring stage completed'
                }
                success {
                    echo 'Monitoring stage succeeded'
                }
                failure {
                    echo 'Monitoring stage failed'
                }
            }
        }
    }

    post {
        always {
            // Archive the build artifacts
            archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
        }
        success {
            // Notify on success
            echo 'Pipeline completed successfully!'
        }
        failure {
            // Notify on failure
            echo 'Pipeline failed!'
            script {
                // Send failure notification to Datadog
                sh '''
                    curl -X POST "https://api.datadoghq.com/api/v1/events" \
                    -H "Content-Type: application/json" \
                    -H "DD-API-KEY: ${DATADOG_API_KEY}" \
                    -d '{
                          "title": "Deployment Failed",
                          "text": "The application deployment to production has failed.",
                          "alert_type": "error"
                        }'
                '''
        }
    }
}
