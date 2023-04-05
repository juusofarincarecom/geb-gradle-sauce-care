#!/usr/bin/env groovy

def jobs = ['firefoxMac', 'ieWin10', 'chromeWin11', 'safariIphone', 'chromeAndroid']

def parallelStagesMap = jobs.collectEntries {
    ["${it}" : generateStage(it)]
}

def generateStage(job) {
    return {
        stage("stage: ${job}") {
            sauce("qacare") {
                sh "./gradlew clean ${job}Test -DCARE_TEST_ENVIRONMENT=${params.CARE_TEST_ENVIRONMENT}"
            }
        }
    }
}

pipeline {
    triggers { cron { "H H/3 * * *" }  }
    agent { label 'build' }
    parameters {
        choice(name: 'CARE_TEST_ENVIRONMENT', choices: ['stg', 'dev', 'prod'], description: 'Test Environment')
    }
    options {
        timeout(time: 1, unit: 'HOURS')
        timestamps()
        buildDiscarder(logRotator(// number of build logs to keep
                                  numToKeepStr: '5',
                                  // history to keep in days
                                  daysToKeepStr: '5',
                                  // artifacts are kept for days
                                  artifactDaysToKeepStr: '5',
                                  // number of builds have their artifacts kept
                                  artifactNumToKeepStr: '5'))
    }
    stages {
        stage('non-parallel stage') {
            steps {
                echo 'This stage will be executed first.'
            }
        }
        stage('Run Stages in Parallel') {
            steps {
                script {
                    parallel parallelStagesMap
                }
            }
        }
    }
}