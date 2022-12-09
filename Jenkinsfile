pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                script{
                    sh"""
                    set -xe
                    whoami
                    pwd
                    ls
                    mvn clean install -DskipTests
                    docker build -t sdms-backend:${BUILD_NUMBER} .
                    docker tag sdms-backend:${BUILD_NUMBER} public.ecr.aws/z9r2r5c8/sdms-backend:${BUILD_NUMBER}
                    docker image ls
                    """
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing done..'
            }
        }
        stage('Push image...') {
            steps {
                echo 'Uploading to S3...'
                script{
                    withAWS(credentials:'AWS', region:'us-west-2') {
                        sh"""
                        aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/z9r2r5c8
                        docker push public.ecr.aws/z9r2r5c8/sdms-backend:${BUILD_NUMBER}
                        docker rmi public.ecr.aws/z9r2r5c8/sdms-backend:${BUILD_NUMBER}
                        docker rmi sdms-backend:${BUILD_NUMBER}
                        """
                    }
                }
            }
        }
    }
}