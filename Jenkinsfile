#!/usr/bin/env groovy
def parallelStagesMap

def generateStage(driver) {
    return {
        stage("stage: ${driver}") {
            steps {
                sauce("qacare") {
                    sh "./gradlew clean ${driver}Test -DCARE_TEST_ENVIRONMENT=${params.CARE_TEST_ENVIRONMENT}"
                }
            }
        }
    }
}

pipeline {
    agent {
        label 'build'
    }
    parameters {
        choice(name: 'CARE_TEST_ENVIRONMENT', choices: ['stg', 'dev', 'prod'], description: 'Test Environment')
    }
    options {
        timeout(time: 1, unit: 'HOURS')
        timestamps()
        skipDefaultCheckout()
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
        stage('VCS checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: scm.branches,
                          extensions: scm
                                          .extensions + [[$class: 'CloneOption', depth: 30, noTags: false, reference: '', shallow: true, timeout: 5]],
                          userRemoteConfigs: scm.userRemoteConfigs])
            }
        }
        stage('Create List of Drivers to run in Parallel') {
            steps {
                script {
                    def drivers = ['firefoxMac', 'ieWin10', 'chromeWin11' , 'safariIphone', 'chromeAndroid']
                    parallelStagesMap = drivers.collectEntries {
                        ["${it}" : generateStage(it)]
                    }
                }
            }
        }
        stage('Run Stages in Parallel') {
            steps {
                script {
                    parallel parallelStagesMap
                }
            }
        }
        stage('Allure Report') {
            steps {
                allure jdk: '',
                       properties: [[key: 'allure.link.issue.pattern', value: 'https://carecom.atlassian.net/browse/{}'],
                                    [key: 'allure.link.tms.pattern', value: 'https://care.testrail.io/index.php?/cases/view/{}']],
                       reportBuildPolicy: 'ALWAYS',
                       results: [[path: 'build/reports/allure-results']]
            }
        }
    }
    post {
        always('Post-build actions') {
            script {
                summary = junit allowEmptyResults: true, testDataPublishers: [[$class: 'SauceOnDemandReportPublisher', jobVisibility: 'public']], testResults: 'build/test-results/*/*.xml'
                saucePublisher testDataPublishers: [[$class: 'SauceOnDemandReportPublisher', jobVisibility: 'public']]
                def totalTest = summary.totalCount - summary.skipCount
                def testStatus = "\nTotal: ${totalTest}, Passed: ${summary.passCount}, Failures: ${summary.failCount}, Skipped/Retried: ${summary.skipCount}"
                slackSend color: slackMessageColor(),
                          botUser: true,
                          channel: 'hit_usc',
                          message: "SEO Visitor Test Result : ${params.CARE_TEST_ENVIRONMENT} #${currentBuild.result} <${BUILD_URL}allure|report>, <${BUILD_URL}|job>" +
                                   testStatus,
                          tokenCredentialId: 'slack-api-bot-user-oauth-token'
                deleteDir()
            }
        }
        success {
            echo 'I succeeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }
}

String slackMessageColor() {
    return currentBuild.result == 'SUCCESS' ? '#00ff26' :
           currentBuild.result == 'UNSTABLE' ? '#f6ff00' :
           currentBuild.result == 'FAILURE' ? '#ff0000' :
           '#0044ff'
}
