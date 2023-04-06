#!/usr/bin/env groovy

ArrayList<String> jobs = System.getProperty("DEVICES", "firefoxMac, edgeWin11, chromeMac, safariIos, chromeAndroid").split(", ").collect { it.trim() }

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
    } as Object
}

pipeline {
    triggers { cron "H H/3 * * *" }
    agent { label 'build' }
    parameters {
        choice(name: 'BASEURL', choices: ['stg', 'dev', 'prod'], description: 'Select the target environment for your tests')
        string(name: 'DEVICES', defaultValue: "firefoxMac, edgeWin11, chromeMac, safariIos, chromeAndroid", description: 'Enter a comma-separated list of devices')
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