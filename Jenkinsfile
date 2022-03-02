#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
    }
    stages {
       stage('increment version') {
                steps {
                    script {
                        echo 'incrementing app version...'
                        sh 'mvn build-helper:parse-version versions:set \
                            -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                            versions:commit'
                        def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                        def version = matcher[0][1]
                        env.IMAGE_NAME = "badshak/demo-app:java-maven-app-$version"
                        //env.IMAGE_NAME = "badshak/demo-app:java-maven-app-$version-$BUILD_NUMBER"
                    }
                }
            }
        stage('build app') {
            steps {
                script {
                    echo "building the application..."
                    sh 'mvn clean package'
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    echo "building the docker image..."
                    withCredentials([usernamePassword(credentialsId: 'DockerHub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh "docker build -t $imageName ."
                        sh "echo $PASS | docker login -u $USER --password-stdin"
                        sh "docker push $imageName"
                    }
                }
            }
        }
        stage('deploy') {
             environment {
               AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
               AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
            }
            steps {
                script {
                    echo 'deploying docker image...'
                    sh 'kubectl create deployment nginx-deployment --image=nginx'
                }
            }
        }
        stage('commit version update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'GitHubToken', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        // git config here for the first time run
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh "git remote set-url origin https://${USER}:${PASS}@github.com/bkarim27/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:eks-deploy
                    }
                }
            }
        }
    }
}
