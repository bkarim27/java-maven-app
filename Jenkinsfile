#!/usr/bin/env groovy
@Library('jenkins-shared-library')
def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "./script.groovy"
                }
            }
        }
        stage("build jar") {
                    steps {
                        script {
                           buildJar()
                    }
             }
        }
        stage("build docker image") {
            steps {
                script {
                    buildImage 'badshak/demo-app:java-maven-app-3.0'
                }
            }
        }
        stage("deploy - docker hub") {
            steps {
                script {
                    deployApp 'badshak/demo-app:java-maven-app-3.0'
                }
            }
        }
    }   
}
