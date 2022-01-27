#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@master', retriever: modernSCM(
    [$class: 'GitSCMSource',
     remote: 'https://github.com/bkarim27/local-shared-library.git',
     credentialsId: 'GitHubToken'
    ]
)

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
                  echo 'building application jar...'
                  buildJar()
               }
            }
        }
        stage('build image') {
            steps {
                script {
                   echo 'building docker image...'
                   buildImage(env.IMAGE_NAME)
                   dockerLogin()
                   dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                   echo 'deploying docker image to EC2...'

                   def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                   def ec2Instance = "ec2-user@35.178.187.137"

                   sshagent(['ec2-docker-server']) {
                       sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                       sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                       sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                   }
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
                                sh 'git push origin HEAD:version-increment-ec2-deploy-5'
                            }
                        }
                    }
        }

    }
}
