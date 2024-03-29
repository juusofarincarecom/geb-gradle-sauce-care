#!/usr/bin/env groovy

ArrayList<String> jobs = System.getProperty("DEVICES", 'chrome_mac\nedge_win\nfirefox_mac\nsafari_ios\nchrome_android').split("\n").collect { it.trim() }
def parallelStagesMap = jobs.collectEntries {
    ["${it}" : generateStage(it)]
}

def generateStage(job) {
    return {
        stage("stage: ${job}") {
            sauce("qacare") {
                sh "./gradlew ${job}Test -DTEST_ENVIRONMENT=${System.getProperty("TEST_ENVIRONMENT", "stg")} --continue || true"
                def testStatus = ""
                def testResultsPath = "build/test-results/${job}Test/*.xml" as Object
                def summary = junit allowEmptyResults: true, skipPublishingChecks: true, testResults: testResultsPath, testDataPublishers: [[$class: 'SauceOnDemandReportPublisher', jobVisibility: 'public']]
                testStatus = "\nTotal: ${summary.totalCount}, Passed: ${summary.passCount}, Failures: ${summary.failCount}, Skipped: ${summary.skipCount}"
                slackSend color: slackMessageColor(),
                          botUser: true,
                          channel: '@juuso.farin',
                          message: "SEO ${job} Test Result: #${currentBuild.result ?: 'PASSED'} <${BUILD_URL}allure|report>, <${BUILD_URL}|job>" +
                                   testStatus,
                          tokenCredentialId: 'slack-api-bot-user-oauth-token'
                }
        }
    } as Object
}

pipeline {
    triggers {
        cron(env.BRANCH_NAME == 'main' ? "H H/3 * * *" : '')
    }
    agent { label 'build' }
    parameters {
        choice(name: 'TEST_ENVIRONMENT', choices: ['stg', 'dev', 'prod'], description: 'Select the target environment for your tests')
        text(name: 'DEVICES', defaultValue: 'chrome_mac\nedge_win\nfirefox_mac\nsafari_ios\nchrome_android', description: 'Enter a newline-separated list of devices')
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
                sh "./gradlew clean"
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

String slackMessageColor() {
    switch(currentBuild.result) {
        case 'SUCCESS':
            return '#00ff26' //Green
            break
        case 'UNSTABLE':
            return '#f6ff00' //Yellow
            break
        case 'FAILURE':
            return '#ff0000' //Red
            break
        default:
            return '#0044ff' //Blue
            break
    }
}